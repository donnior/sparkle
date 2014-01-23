package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.web.adapter.HttpServletRequestAdapter;
import me.donnior.web.adapter.ServletWebRequest;

import org.junit.Test;

public class RouteMethodDetectorTest extends RouteMethodDetector{
    
    @Test
    public void test_direct_method_detect(){
        WebRequest request = new ServletWebRequest(getRequest(), null);
        
        assertEquals(HTTPMethod.GET, RouteMethodDetector.detectMethod(request));
        
        request = new ServletWebRequest(postRequest(), null);
        assertEquals(HTTPMethod.POST, RouteMethodDetector.detectMethod(request));
        
        request = new ServletWebRequest(unknownRequest(), null);
        assertEquals(HTTPMethod.GET, RouteMethodDetector.detectMethod(request));
        
    }

    private HttpServletRequest unknownRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getMethod() {
                return "unknown";
            }
        };
    }

    private HttpServletRequest postRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getMethod() {
                return "post";
            }
        };
    }

    private HttpServletRequest getRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getMethod() {
                return "Get";
            }
        };
    }

}
