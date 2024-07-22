package com.victor.alexa.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import java.util.Properties;

@Component
public final class PropertiesUtils {

    private static Environment environment;

    @Autowired
    public PropertiesUtils(Environment env) {
        environment = env;
    }

    public static String getPropertyValue(String key) {
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(new ClassPathResource("application.yaml"));
            Properties yamlProperties = yamlFactory.getObject();


            String yamlValue = yamlProperties.getProperty(key);
            if (yamlValue != null) {
                return yamlValue;
            }

            return environment.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
