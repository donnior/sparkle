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
    
    public  RoutingBuilder match(final HttpServletRequest request, Router router) {
        //TODO match route defenition with request's servlet path, request headers, etc.

        final String path = extractPath(request);
        
        List<RoutingBuilder> rbs = router.getAllRouteBuilders();
        
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
        
        for(RouteBuilderMatcher m : matched){
        	System.out.println("founded rb with : " + m.getBuilder().getPathPattern() + " with method " + m.getBuilder().getHttpMethod());
        }
        
        if(matched.size() > 1){
            Collections.sort(matched, new Comparator<RouteBuilderMatcher>(){
                @Override
                public int compare(RouteBuilderMatcher one, RouteBuilderMatcher two) {
                    MatchedCondition[] mc1 = one.matchedExplicitConditions();
                    MatchedCondition[] mc2 = two.matchedExplicitConditions();

                    Set<MatchedCondition> s1 = new HashSet<MatchedCondition>(Arrays.asList(mc1));
                    Set<MatchedCondition> s2 = new HashSet<MatchedCondition>(Arrays.asList(mc2));
                    
                    return s1.size() - s2.size();
                    
//                    Set<MatchedCondition> unioned = Sets.union(s1, s2);
                    
                    //if two RoutingBuilder have unioned matched conditions, choose the one has more condition matched
//                    return (s1.size()-unioned.size()) - (s2.size()-unioned.size());
                }
            });
        }
        
/*        FList<RoutingBuilder> pathAndMethodMatched = FLists.create(rbs).select(new Predict<RoutingBuilder>() {
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
*/
        /*  this solution is wrong 
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
        */
        
//        FList<RoutingBuilder> matched = FLists.create(rbs).select(new Predict<RoutingBuilder>() {
//            @Override
//            public boolean apply(RoutingBuilder rb) {
//                return rb.matchPath(path) && 
//                       (rb.getHttpMethod() == method) && 
//                       rb.matchHeader(request) && 
//                       rb.matchParam(request) &&
//                       rb.matchConsume(request);
//            }
//        });
//        
        RouteBuilderMatcher rbm = matched.first();
        RoutingBuilder rb = null;
        if(rbm != null){
        	rb = rbm.getBuilder();
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
