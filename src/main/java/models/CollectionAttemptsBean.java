package models;

import com.sun.istack.logging.Logger;
import database.HibernateManager;
import jakarta.annotation.PostConstruct;
import mbeans.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.management.*;
import java.lang.management.ManagementFactory;

@ManagedBean(name = "attempts", eager = true)
@SessionScoped
public class CollectionAttemptsBean implements Serializable {
    //нужно использовать потокобезопасные коллекции,
    // потому что взаимодействия с коллекциями могут происходить в разных потоках.
    private CopyOnWriteArrayList<Attempt> attempts;
    private final HibernateManager hibernateManager;
    private PointsCounterMBean pointsCounter;
    private HitPercentageMBean hitPercentage;

    private Attempt currentAttempt = new Attempt("0", "0", "3");

    public CollectionAttemptsBean() {
        hibernateManager = new HibernateManager();
        attempts = new CopyOnWriteArrayList<>();
    }

    @PostConstruct
    public void init() {
        var resultEntity = hibernateManager.getAttempts();
        attempts = new CopyOnWriteArrayList<>(resultEntity);

        // Регистрация MBeans
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName pointsCounterName = new ObjectName("mbeans:type=PointsCounter");
            pointsCounter = new PointsCounter(hibernateManager);
            mbs.registerMBean(pointsCounter, pointsCounterName);

            // Регистрация слушателя уведомлений
            NotificationListener listener = (notification, handback) -> System.out.println("Received notification: " + notification.getMessage());
            mbs.addNotificationListener(pointsCounterName, listener, null, null);

            hitPercentage = new HitPercentage(pointsCounter);
            ObjectName hitPercentageName = new ObjectName("mbeans:type=HitPercentage");
            StandardMBean hitPercentageMBean = new StandardMBean(hitPercentage, HitPercentageMBean.class);
            mbs.registerMBean(hitPercentageMBean, hitPercentageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Attempt attempt) {
        attempts.add(attempt);
        hibernateManager.addAttempt(attempt);

        pointsCounter.incrementTotalPoints();
        if (attempt.getIsHit()) {
            pointsCounter.incrementHitPoints();
        }
    }

    public void clear() {
        attempts.clear();

        hibernateManager.clearAttempts();
        pointsCounter.resetAndInitializeCounts();
    }

    public List<Attempt> getAttempts() {
        return hibernateManager.getAttempts();
    }

    public void addFromForm() {
            currentAttempt.updateIsHIt();
            add(currentAttempt);
            currentAttempt = new Attempt(currentAttempt.getX(),
                    currentAttempt.getY(), currentAttempt.getR());
    }

    public void addFromCanvas() {
        String strX = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("x");
        String strY = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("y");
        String strR = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("r");

        Attempt attempt = new Attempt(strX, strY, strR);

        add(attempt);
    }

    public Attempt getCurrentAttempt() {
        return currentAttempt;
    }

    public void setCurrentAttempt(Attempt currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionAttemptsBean that = (CollectionAttemptsBean) o;
        return Objects.equals(attempts, that.attempts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempts);
    }

    @Override
    public String toString() {
        return "CollectionAttempts{" +
                "attempts=" + attempts +
                '}';
    }
}