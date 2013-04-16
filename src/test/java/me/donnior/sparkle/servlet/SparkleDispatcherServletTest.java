package me.donnior.sparkle.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.HTTPMethod;

import org.junit.Before;
import org.junit.Test;

public class SparkleDispatcherServletTest {

    private SparkleDispatcherServlet servlet;
    
    @Before
    public void setup(){
        servlet = new SparkleDispatcherServlet(new MockSparkleEngine(null));
    }
    
    @Test
    public void test() throws ServletException, IOException{
        servlet.doGet(null, null);
        
        servlet.doHead(null, null);
        
        
        servlet.doPost(null, null);
        
        servlet.doDelete(null, null);
        
        servlet.doPut(null, null);
        
        servlet.doOptions(null, null);
        
        servlet.doTrace(null, null);
    }
    
}

class MockSparkleEngine extends SparkleEngine{

    
    
    public MockSparkleEngine(SparkleConfiguration config) {
        super(config);
    }
    
    @Override
    protected void doService(HttpServletRequest request,
            HttpServletResponse response, HTTPMethod method) {
        
    }
    
    
    
    
}