package models;

import database.HibernateManager;
import mbeans.HitPercentage;
import mbeans.PointsCounter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@ManagedBean(name = "attempts", eager = true)
@ApplicationScoped
public class CollectionAttemptsBean implements Serializable {
    @Inject
    private PointsCounter pointsCounter;

    @Inject
    private HitPercentage hitPercentage;

    private CopyOnWriteArrayList<Attempt> attempts;
    private final HibernateManager hibernateManager;

    private Attempt currentAttempt = new Attempt("0", "0", "3");

    public CollectionAttemptsBean() {
        hibernateManager = new HibernateManager();
        attempts = new CopyOnWriteArrayList<>();
    }

    @PostConstruct
    public void init() {
        pointsCounter.prepare(getAttempts());
        hitPercentage.setPointsCounter(pointsCounter);

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName mBean;

        try {
            mBean = new ObjectName("52Agent:name=PointsCounter");
            if (!mbs.isRegistered(mBean)) {
                mbs.registerMBean(pointsCounter, mBean);
            }
            mBean = new ObjectName("52Agent:name=HitPercentage");
            if (!mbs.isRegistered(mBean)) {
                mbs.registerMBean(hitPercentage, mBean);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Attempt attempt) {
        attempts.add(attempt);
        hibernateManager.addAttempt(attempt);
        pointsCounter.incrementTotalPoints();
        if (attempt.getIsHit()){
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