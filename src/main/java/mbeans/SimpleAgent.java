package mbeans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


@ApplicationScoped
@ManagedBean(eager = true)
public class SimpleAgent {

    @Inject
    private PointsCounter pointsCounterMBean;

    @Inject
    private HitPercentage hitPercentageMBean;

    @PostConstruct
    public void initAgent() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName mBean;

        try {
            mBean = new ObjectName("SimpleAgent:name=PointsCounter");
            if (!mbs.isRegistered(mBean)) {
                mbs.registerMBean(pointsCounterMBean, mBean);
            }
            mBean = new ObjectName("SimpleAgent:name=HitPercentage");
            if (!mbs.isRegistered(mBean)) {
                mbs.registerMBean(hitPercentageMBean, mBean);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logSimpleAgentStarted() {
        System.out.println("SimpleAgent.logSimpleAgentStarted");
    }

    public void startup(@Observes @Initialized(ApplicationScoped.class) Object context) {
        SimpleAgent a = new SimpleAgent();
        a.logSimpleAgentStarted();
    }
}
