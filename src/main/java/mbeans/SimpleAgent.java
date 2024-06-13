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
    private Hello HelloMBean;

    @PostConstruct
    public void initAgent() {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName mBean;

        try {
            mBean = new ObjectName("SimpleAgent:name=MBeanAgent");
            if (!mbs.isRegistered(mBean)) {
                mbs.registerMBean(HelloMBean, mBean);
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
