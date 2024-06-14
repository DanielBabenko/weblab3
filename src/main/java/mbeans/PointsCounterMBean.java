package mbeans;

import database.HibernateManager;
import models.Attempt;

import java.util.concurrent.CopyOnWriteArrayList;

public interface PointsCounterMBean {
    int getTotalPoints();
    int getHitPoints();
    void incrementTotalPoints();
    void incrementHitPoints();
    void resetAndInitializeCounts();
}
