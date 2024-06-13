package mbeans;

public interface PointsCounterMBean {
    int getTotalPoints();
    int getHitPoints();
    void incrementTotalPoints();
    void incrementHitPoints();
    void resetAndInitializeCounts();
}
