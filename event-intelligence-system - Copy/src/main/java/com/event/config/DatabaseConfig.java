package com.event.config;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private static Properties props = new Properties();

    static {
        try {
            InputStream input = DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            if (input == null) {
                throw new RuntimeException("application.properties not found");
            }

            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return props.getProperty("db.url");
    }

    public static String getUser() {
        return props.getProperty("db.user");
    }

    public static String getPassword() {
        return props.getProperty("db.password");
    }
}