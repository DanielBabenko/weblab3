package mbeans;

import database.HibernateManager;
import models.Attempt;
import models.CollectionAttemptsBean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
@ManagedBean(eager = true)
public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean, Serializable {
    private AtomicInteger totalPoints = new AtomicInteger(0);
    private AtomicInteger hitPoints = new AtomicInteger(0);
    private long hitNumber = 0;
    private long sequenceNumber = 1;
    private CopyOnWriteArrayList<Attempt> attempts;

    public PointsCounter() {
        //пустой конструктор, чтобы всё запускалось
    }

    public void prepare(CopyOnWriteArrayList<Attempt> attempts) {
        this.attempts = attempts;
        initializeCounts();
    }

    private void initializeCounts() {
        totalPoints.set(0);
        hitPoints.set(0);
        for (Attempt attempt : attempts) {
            totalPoints.incrementAndGet();
            hitNumber++;
            if (attempt.getIsHit()) {
                hitPoints.incrementAndGet();
            }
        }
    }

    public void resetAndInitializeCounts() {
        totalPoints.set(0);
        hitPoints.set(0);
        hitNumber = 0;
        initializeCounts();
    }

    @Override
    public int getTotalPoints() {
        return totalPoints.get();
    }

    @Override
    public int getHitPoints() {
        return hitPoints.get();
    }

    @Override
    public void incrementTotalPoints() {
        totalPoints.incrementAndGet();
        hitNumber++;
        if (hitNumber % 10 == 0) {
            Notification n = new Notification("HitNumber", this, sequenceNumber++,
                    System.currentTimeMillis(), "Число точек на графике кратно 10!");
            sendNotification(n);
        }
    }

    @Override
    public void incrementHitPoints() {
        hitPoints.incrementAndGet();
    }
}