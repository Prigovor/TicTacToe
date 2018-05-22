package com.prigovor.TicTacToe.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

class CleanupDriver {

    private final Logger log = LoggerFactory.getLogger(CleanupDriver.class);
    // Now deregister JDBC drivers in this context's ClassLoader:
    // Get the webapp's ClassLoader
    private final ClassLoader cl = Thread.currentThread().getContextClassLoader();
    // Loop through all drivers
    private final Enumeration<Driver> drivers = DriverManager.getDrivers();

    private void destroy() {
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == cl) {
                // This driver was registered by the webapp's ClassLoader, so deregister it:
                try {
                    log.info("Deregister JDBC driver {}", driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    log.error("Error deregister JDBC driver {}", driver, ex);
                }
            } else {
                // driver was not registered by the webapp's ClassLoader and may be in use elsewhere
                log.warn("Not deregister JDBC driver: ", driver);
            }
        }
    }
}