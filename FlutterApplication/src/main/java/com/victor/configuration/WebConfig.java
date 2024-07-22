package com.victor.configuration;

import com.amazon.ask.servlet.ServletConstants;
import com.victor.alexa.properties.PropertiesUtils;
import com.victor.alexa.servlet.AlexaServlet;
import com.victor.alexa.servlet.PrivacyPolicyServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Objects;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> alexaServlet() {
        loadProperties();

        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new AlexaServlet());
        servRegBean.addUrlMappings("/alexa/*");
        servRegBean.setLoadOnStartup(1);
        return servRegBean;
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> privacyPolicyServlet() {
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new PrivacyPolicyServlet());
        servRegBean.addUrlMappings("/policy/*");
        servRegBean.setLoadOnStartup(2);
        return servRegBean;
    }

    private void loadProperties() {
        try {
            InputStream keyStoreStream = getClass().getClassLoader().getResourceAsStream(Constants.SSL_KEYSTORE_FILE_NAME);
            if (keyStoreStream == null) {
                throw new RuntimeException("Could not find " + Constants.SSL_KEYSTORE_FILE_NAME + " in classpath");
            }
            String keystorePath = getClass().getClassLoader().getResource(Constants.SSL_KEYSTORE_FILE_NAME).toExternalForm();
            System.setProperty(Constants.SSL_KEYSTORE_FILE_PATH_KEY, keystorePath);
            System.setProperty(Constants.SSL_KEYSTORE_PASSWORD_KEY, Constants.SSL_KEYSTORE_PASSWORD);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load keystore", e);
        }

        System.setProperty(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY, Objects.requireNonNull(PropertiesUtils.getPropertyValue(ServletConstants.TIMESTAMP_TOLERANCE_SYSTEM_PROPERTY)));
        System.setProperty(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY, Objects.requireNonNull(PropertiesUtils.getPropertyValue(ServletConstants.DISABLE_REQUEST_SIGNATURE_CHECK_SYSTEM_PROPERTY)));
    }

}