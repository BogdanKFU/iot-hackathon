package ru.kpfu.group11501.amirbogdan.iothackathon.config;

import java.util.Properties;

public class PropertiesHolder {

    private static Properties properties;

    static{
        properties = new Properties();
        try {
            properties.load(PropertiesHolder.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            System.out.println("Can't load properties");
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static Properties getProperties(){
        return properties;
    }
}