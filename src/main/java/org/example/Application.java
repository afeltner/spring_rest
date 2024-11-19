package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * Application is the entry point for a service that listens on a port for incoming REST API calls.
 * The listen port is specified in the application.yaml file.
 */
@SpringBootApplication
public class Application {
    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        LOGGER.debug("Application server starting......");
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Environment environment = context.getEnvironment();
        String port = environment.getProperty("server.port");
        LOGGER.debug("Application listening on port {}", port);
    }
}