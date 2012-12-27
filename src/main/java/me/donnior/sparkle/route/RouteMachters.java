package me.donnior.sparkle.route;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import me.donnior.fava.FList;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.util.AntPathMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class RouteMachters {

    private final static Logger logger = LoggerFactory.getLogger(RouteMachters.class);
    
    public  RouteDefintion match(final HttpServletRequest request, Router router) {
        //TODO match route defenition with request's servlet path, request headers, etc.

        final String path = extractPath(request);
        final HTTPMethod method = extractMethod(request);
        
        List<RoutingBuilder> rbs = router.getAllRouteBuilders();
        
        FList<RoutingBuilder> pathAndMethodMatched = FLists.create(rbs).select(new Predict<RoutingBuilder>() {
            @Override
            public boolean apply(RoutingBuilder rb) {
                return rb.matchPath(path);
            }
        }).select(new Predict<RoutingBuilder>() {
            @Override
            public boolean apply(RoutingBuilder rb) {
                return rb.getHttpMethod() == method;
            }
        });
        
        //after path and method matched, it may get more than one RoutingBuilders which has different conditions matched.
        if(pathAndMethodMatched.size() > 1){
            Collections.sort(pathAndMethodMatched, new Comparator<RoutingBuilder>(){
                @Override
                public int compare(RoutingBuilder one, RoutingBuilder two) {
                    MatchedCondition[] mc1 = one.matchCondition(request);
                    MatchedCondition[] mc2 = two.matchCondition(request);

                    Set<MatchedCondition> s1 = new HashSet<MatchedCondition>(Arrays.asList(mc1));
                    Set<MatchedCondition> s2 = new HashSet<MatchedCondition>(Arrays.asList(mc2));
                    
                    Set<MatchedCondition> unioned = Sets.union(s1, s2);
                    
                    //if two RoutingBuilder have unioned matched conditions, choose the one has more condition matched
                    return (s1.size()-unioned.size()) - (s2.size()-unioned.size());
                }
            });
        }
        
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
