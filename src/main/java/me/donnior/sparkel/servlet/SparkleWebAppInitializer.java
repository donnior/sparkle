package me.donnior.sparkel.servlet;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SparkleWebAppInitializer implements ServletContainerInitializer {
    
    public SparkleWebAppInitializer() {
        System.out.println("init SparkleWebAppInitializer");
    }

    @Override
    public void onStartup(Set<Class<?>> arg0, ServletContext servletContext)
            throws ServletException {
        ServletRegistration.Dynamic appServlet = servletContext.addServlet(
                "appServlet", new SparkleDispatcherServlet());
        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping("/");
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException(
                    "'appServlet' could not be mapped to '/' due "
                            + "to an existing mapping. This is a known issue under Tomcat versions "
                            + "<= 7.0.14;");
        }
    }

}