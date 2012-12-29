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
import me.donnior.fava.Function;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.util.AntPathMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteMachter {

    private final static Logger logger = LoggerFactory.getLogger(RouteMachter.class);
    private RouteBuilderHolder routeBuilderHolder;
    
    public RouteMachter(RouteBuilderHolder router) {
        this.routeBuilderHolder = router;
    }

    public  RoutingBuilder match(final HttpServletRequest request) {
        //TODO match route defenition with request's servlet path, request headers, etc.

        final String path = extractPath(request);
        
        List<RoutingBuilder> rbs = this.routeBuilderHolder.getRegisteredRouteBuilders();
        
        FList<RouteBuilderMatcher> rbms = FLists.create(rbs).collect(new Function<RoutingBuilder, RouteBuilderMatcher>(){
			@Override
			public RouteBuilderMatcher apply(RoutingBuilder e) {
				return new RouteBuilderMatcher(e, request);
			}
        	
        });
        
        FList<RouteBuilderMatcher> matched = rbms.select(new Predict<RouteBuilderMatcher>() {
            @Override
            public boolean apply(RouteBuilderMatcher rbm) {
                return rbm.match();
            }
        });
        
//        for(RouteBuilderMatcher m : matched){
//        	    System.out.println("founded rb with : " + m.getBuilder().getPathPattern() + " with method " + m.getBuilder().getHttpMethod());
//        }
        
        if(matched.size() > 1){
            System.out.println("found more than one matched route builder, now trying to get the closest one");
            Collections.sort(matched, new Comparator<RouteBuilderMatcher>(){
                @Override
                public int compare(RouteBuilderMatcher one, RouteBuilderMatcher two) {
                    MatchedCondition[] mc1 = one.matchedExplicitConditions();
                    MatchedCondition[] mc2 = two.matchedExplicitConditions();
                    
                    Set<MatchedCondition> s1 = new HashSet<MatchedCondition>(Arrays.asList(mc1));
                    Set<MatchedCondition> s2 = new HashSet<MatchedCondition>(Arrays.asList(mc2));
                    
                    return s2.size() - s1.size();
                }
            });
        }
        
        RouteBuilderMatcher rbm = matched.first();
        RoutingBuilder rb = null;
        if(rbm != null){
        	    rb = rbm.getBuilder();
        	    logger.debug("founded route builder matched closest {}", rb);
            Map<String, String> uriVariables = new AntPathMatcher().extractUriTemplateVariables(rb.getPathPattern(), path);
            logger.debug("extracted path variables {}", uriVariables);            
        } else {
            logger.debug("can't find RoutingBuilder for {}", path);
        }
        return rb;
              
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
