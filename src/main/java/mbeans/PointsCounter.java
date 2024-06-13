package mbeans;

import database.HibernateManager;
import models.Attempt;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@ManagedBean
@ApplicationScoped
public class PointsCounter extends NotificationBroadcasterSupport implements PointsCounterMBean, Serializable {
    private AtomicInteger totalPoints = new AtomicInteger(0);
    private AtomicInteger hitPoints = new AtomicInteger(0);
    private long hitNumber = 0;
    private long sequenceNumber = 1;
    private HibernateManager hibernateManager;

    public PointsCounter(HibernateManager hibernateManager) {
        this.hibernateManager = hibernateManager;
        initializeCounts();
    }

    private void initializeCounts() {
        totalPoints.set(0);
        hitPoints.set(0);
        Collection<Attempt> attempts = hibernateManager.getAttempts();
        for (Attempt attempt : attempts) {
            totalPoints.incrementAndGet();
            if (attempt.getIsHit()) {
                hitPoints.incrementAndGet();
                hitNumber++;
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
    }

    @Override
    public void incrementHitPoints() {
        hitPoints.incrementAndGet();
        hitNumber++;
        if (hitNumber % 10 == 0) {
            Notification n = new Notification("HitNumber", this, sequenceNumber++,
                    System.currentTimeMillis(), "Число точек на графике кратно 10!");
            sendNotification(n);
        }
    }
}