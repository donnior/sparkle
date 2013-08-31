package me.donnior.sparkle.core.route;

import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.core.route.RouteMethodDetector;
import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Test;

public class RouteMethodDetectorTest extends RouteMethodDetector{
    
    @Test
    public void test_direct_method_detect(){
        HttpServletRequest request = getRequest();
        
        assertEquals(HTTPMethod.GET, RouteMethodDetector.detectMethod(request));
        
        request = postRequest();
        assertEquals(HTTPMethod.POST, RouteMethodDetector.detectMethod(request));
        
        request = unknownRequest();
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
