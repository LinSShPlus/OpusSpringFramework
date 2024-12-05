package ru.otus.spring.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * CustomHealthIndicator
 **/
@Component
public class CustomHealthIndicator implements HealthIndicator {

    private boolean isHealthy = true;

    public CustomHealthIndicator() {
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        Runnable command = () -> isHealthy = false;
        scheduled.schedule(command, 30, TimeUnit.SECONDS);
    }

    @Override
    public Health health() {
        return isHealthy
                ? Health.up().withDetail("message", "Good!").build()
                : Health.down().withDetail("message", "Bad!").build();
    }
}
