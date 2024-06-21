package OPI4.weblab3.mbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
@ManagedBean(eager = true)
public class HitPercentage implements HitPercentageMBean, Serializable {

    private PointsCounterMBean pointsCounter;

    public HitPercentage() {

    }

    public void setPointsCounter(PointsCounter pointsCounter) {
        this.pointsCounter = pointsCounter;
    }

    @Override
    public double getHitPercentage() {
        int totalPoints = pointsCounter.getTotalPoints();
        int missedPoints = pointsCounter.getHitPoints();
        if (totalPoints == 0) {
            return 0.0;
        }
        return (double) missedPoints / totalPoints * 100;
    }
}