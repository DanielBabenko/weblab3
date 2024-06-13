package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.faces.bean.ManagedBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

@ManagedBean
@ApplicationScoped
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
