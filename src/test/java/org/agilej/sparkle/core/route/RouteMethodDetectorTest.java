package org.agilej.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.web.adapter.WebRequestAdapter;

import org.junit.Test;

public class RouteMethodDetectorTest extends RouteMethodDetector{
    
    @Test
    public void test_direct_method_detect(){
        WebRequest request = getRequest();
        
        assertEquals(HTTPMethod.GET, RouteMethodDetector.detectMethod(request));
        
        request = postRequest();
        assertEquals(HTTPMethod.POST, RouteMethodDetector.detectMethod(request));
        
        request = unknownRequest();
        assertEquals(HTTPMethod.GET, RouteMethodDetector.detectMethod(request));
        
    }

    private WebRequest unknownRequest() {
        return new WebRequestAdapter(){
            @Override
            public String getMethod() {
                return "unknown";
            }
        };
    }

    private WebRequest postRequest() {
        return new WebRequestAdapter(){
            @Override
            public String getMethod() {
                return "post";
            }
        };
    }

    private WebRequest getRequest() {
        return new WebRequestAdapter(){
            @Override
            public String getMethod() {
                return "Get";
            }
        };
    }

}
