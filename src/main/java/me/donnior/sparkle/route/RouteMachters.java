package me.donnior.sparkle.route;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.donnior.fava.FList;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.util.AntPathMatcher;

public class RouteMachters {

    private final static Logger logger = LoggerFactory.getLogger(RouteMachters.class);
    
    public  RouteDefintion match(HttpServletRequest request, Router router) {
        //TODO match route defenition with request's servlet path, request headers, etc.

        final String path = extractPath(request);
        final HTTPMethod method = extractMethod(request);
        
        List<RoutingBuilder> rbs = router.getAllRouteBuilders();
        
        FList<RoutingBuilder> pathAndMethodMatched = FLists.create(rbs).select(new Predict<RoutingBuilder>() {
            @Override
            public boolean apply(RoutingBuilder rb) {
                return rb.match(path);
            }
        }).select(new Predict<RoutingBuilder>() {
            @Override
            public boolean apply(RoutingBuilder rb) {
                return rb.getHttpMethod() == method;
            }
        });
        
        RoutingBuilder rb = pathAndMethodMatched.first();
        if(rb != null){
            Map<String, String> uriVariables = new AntPathMatcher().extractUriTemplateVariables(rb.getRoutePattern(), path);
            logger.debug("extracted path variables {}", uriVariables);            
        }
        return rb;
              
    }

    private HTTPMethod extractMethod(HttpServletRequest request) {
        if("get".equals(request.getMethod().toLowerCase())){
            return HTTPMethod.GET;
        }
        if("post".equals(request.getMethod().toLowerCase())){
            return HTTPMethod.POST;
        }
        return HTTPMethod.GET;
    }

    private String extractPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String cAndActionString = pathInfo;
        
        if(pathInfo == null){
//            System.out.println("wild servlet mapping like / or *.do");
            cAndActionString = request.getServletPath();
        } else {
//            System.out.println("normal mapping like /cms/*");
        }
        
        System.out.println("c&a string : " + cAndActionString);
        return cAndActionString;
    }
    
    

}
