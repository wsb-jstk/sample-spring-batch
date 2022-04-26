package com.capgemini.sample.batch.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
public class H2ConsoleStarter {

    private static final String H2_CONSOLE_PORT = "8082";
    private Server webServer;

    @PostConstruct
    public void postConstruct() {
        log.info("Starting h2-console under: http://localhost:{}", H2_CONSOLE_PORT);
        if (this.webServer == null) {
            try {
                this.webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", H2_CONSOLE_PORT);
                this.webServer.start();
                log.info("Started h2-console. You can access console on path: http://localhost:{}", H2_CONSOLE_PORT);
            } catch (final java.sql.SQLException e) {
                log.warn("Couldn't start H2Console (maybe port {} is already taken): {{}", H2_CONSOLE_PORT, e.getMessage());
            }
        } else {
            log.info("WebServer is already running on port {}", H2_CONSOLE_PORT);
        }
    }

    @PreDestroy
    public void shutdownConsole() {
        if (this.webServer != null) {
            this.webServer.shutdown();
        }
    }

}

