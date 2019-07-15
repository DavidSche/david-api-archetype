package ${groupId}.restserver.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * CustomHealthIndicator: implement our own custom health indicator – which can
 * collect any type of custom health data specific to the application and
 * automatically expose it through the /health endpoint:
 * 
 * @Author:David.che :
 * 
 * @version ${version}
 * 
 *          ${groupId}:${artifactId}
 *
 * @see https://www.baeldung.com/spring-boot-actuators#boot-2x-actuator
 */
@Slf4j
@Component
public class CustomHealthIndicator implements HealthIndicator {

    // @Override
    // public Health health() {
    // int errorCode = check(); // perform some specific health check
    // if (errorCode != 0) {
    // return Health.down()
    // .withDetail("Error Code", errorCode).build();
    // }
    // return Health.up().build();
    // }

    // public int check() {
    // // Our logic to check health
    // return 0;
    // }
    private boolean isHealthy = true;

    public CustomHealthIndicator() {
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        scheduled.schedule(() -> {
            isHealthy = true;
        }, 30, TimeUnit.SECONDS);
    }

    @Override
    public Health health() {
        return isHealthy ? Health.up().build() : Health.down().build();
    }
}
