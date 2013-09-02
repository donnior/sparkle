package me.donnior.sparkle.servlet;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkleWebAppInitializer implements ServletContainerInitializer {
    
    private static final String SPARKLE_SERVLET_NAME = "sparkleServlet";
    private static final String STATIC_SERVLET_NAME = "staticServlet";
    
    private final static Logger logger = LoggerFactory.getLogger(SparkleWebAppInitializer.class);
    
    public SparkleWebAppInitializer() {
        
    }

    @Override
    public void onStartup(Set<Class<?>> arg0, ServletContext servletContext) throws ServletException {
        
        
        ServletRegistration.Dynamic appServlet = 
                servletContext.addServlet(SPARKLE_SERVLET_NAME, new SparkleDispatcherServlet());
        appServlet.setLoadOnStartup(1);
        Set<String> mappingConflicts = appServlet.addMapping("/");
        appServlet.setAsyncSupported(true);
        
        ensureContainerSpecifiedEnv(mappingConflicts);
        
        ServletRegistration.Dynamic staticServlet = 
                servletContext.addServlet(STATIC_SERVLET_NAME, new StaticWrapperServlet());
        staticServlet.setLoadOnStartup(1);
        staticServlet.addMapping("/static/*");
    }

    //deal with container specified env issue, like tomcat's version support
    private void ensureContainerSpecifiedEnv(Set<String> mappingConflicts) {
        if (!mappingConflicts.isEmpty()) {
            throw new IllegalStateException(
                    "'appServlet' could not be mapped to '/' due "
                            + "to an existing mapping. This is a known issue under Tomcat versions "
                            + "<= 7.0.14;");
        }
    }

}