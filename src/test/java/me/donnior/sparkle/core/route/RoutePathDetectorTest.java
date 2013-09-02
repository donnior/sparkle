package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Test;

public class RoutePathDetectorTest extends RoutePathDetector{
    
    @Test
    public void test_extract_normal_path(){
        HttpServletRequest request = normalPathRequest();
        assertEquals("/pathinfo", RoutePathDetector.extractPath(request));
    }
    
    @Test
    public void test_extract_wild_path(){
        HttpServletRequest request = wildPathRequest();
        assertEquals("/servletPath", RoutePathDetector.extractPath(request));
    }

    private HttpServletRequest normalPathRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getPathInfo() {
                return "/pathinfo";
            }
            
            @Override
            public String getServletPath() {
                return "/servletPath";
            }
            
        };
    }

    private HttpServletRequest wildPathRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getPathInfo() {
                return null;
            }
            
            @Override
            public String getServletPath() {
                return "/servletPath";
            }
            
        };
    }
    
    
}

