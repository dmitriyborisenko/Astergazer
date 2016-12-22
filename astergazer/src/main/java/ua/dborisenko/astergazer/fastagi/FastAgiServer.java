package ua.dborisenko.astergazer.fastagi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.fastagi.MappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FastAgiServer extends AgiServerThread {
    
    @Autowired
    private MappingStrategy agiMapping;
    
    @PostConstruct
    public void init() {
        setAgiServer(new DefaultAgiServer(agiMapping));
        setDaemon(false);
        startup();
    }
    
    @PreDestroy
    public void destroy() {
        shutdown();
    }
}
