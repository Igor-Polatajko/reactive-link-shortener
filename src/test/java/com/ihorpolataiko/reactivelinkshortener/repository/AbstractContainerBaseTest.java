package com.ihorpolataiko.reactivelinkshortener.repository;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;

@ContextConfiguration(initializers = AbstractContainerBaseTest.Initializer.class)
public abstract class AbstractContainerBaseTest {

    public static final int REDIS_PORT = 6379;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static GenericContainer<?> redis = new GenericContainer<>("redis:6.0.8")
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            redis.start();

            String redisContainerIP = "spring.redis.host=" + redis.getContainerIpAddress();
            String redisContainerPort = "spring.redis.port=" + redis.getMappedPort(REDIS_PORT);
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(context,  redisContainerIP, redisContainerPort);
        }
    }

}
