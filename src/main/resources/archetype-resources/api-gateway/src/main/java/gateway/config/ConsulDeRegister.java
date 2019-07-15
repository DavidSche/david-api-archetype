package ${groupId}.gateway.config;

import ${groupId}.common.gracefullshutdown.IProbeController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.stereotype.Component;

/**
 * DeRegister the consul service of the Gateway Server
 * 
 * @Author:David.che
 * 
 * @version: ${version}
 * 
 * @Date 2019-03-01
 * 
 * @category ${groupId}:${artifactId}
 *
 */

@Slf4j
@Component
public class ConsulDeRegister implements IProbeController {

    @Autowired(required = false)
    private ConsulServiceRegistry serviceRegistration;

    @Autowired
    private ConsulRegistration registration;

    @Override
    public void setReady(boolean ready) {
        log.info("Begin deregister consul !");
        this.serviceRegistration.deregister(registration);
        log.info("deregister consul over !");
    }
}
