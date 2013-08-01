package me.donnior.sparkle.route;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import me.donnior.fava.FList;
import me.donnior.fava.Function;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteMachter {

    private final static Logger logger = LoggerFactory.getLogger(RouteMachter.class);
    
    private RouteBuilderHolder routeBuilderHolder;
    
    public RouteMachter(RouteBuilderHolder router) {
        this.routeBuilderHolder = router;
    }

    public  RouteBuilder match(final HttpServletRequest request) {
        
        List<RouteBuilder> rbs = this.routeBuilderHolder.getRegisteredRouteBuilders();
        FList<RouteBuilderMatcher> matched = findSucceedRouteBuilderMatcher(request, rbs);
        RouteBuilderMatcher rbm = getClosestMatchedRouteBuilder(matched);
        if(rbm != null){
            RouteBuilder rb = rbm.getBuilder();
    	    logger.debug("Found route builder matched closest {}", rb);
//    	    Map<String, String> uriVariables = new AntPathMatcher().extractUriTemplateVariables(rb.getPathPattern(), path);
//            logger.debug("extracted path variables {}", uriVariables);
            return rb;
        } else {
            logger.debug("can't find RoutingBuilder for {}", request);
            return null;
        }
    }

    private FList<RouteBuilderMatcher> findSucceedRouteBuilderMatcher(final HttpServletRequest request, List<RouteBuilder> rbs) {
        FList<RouteBuilderMatcher> rbms = FLists.create(rbs).collect(new Function<RouteBuilder, RouteBuilderMatcher>(){
			@Override
			public RouteBuilderMatcher apply(RouteBuilder e) {
				return new RouteBuilderMatcher(e, request);
			}
        	
        });
        
        FList<RouteBuilderMatcher> matched = rbms.select(new Predicate<RouteBuilderMatcher>() {
            @Override
            public boolean apply(RouteBuilderMatcher rbm) {
                return rbm.match();
            }
        });
        return matched;
    }

    private RouteBuilderMatcher getClosestMatchedRouteBuilder(FList<RouteBuilderMatcher> matched) {
        if(matched.size() > 1){
            logger.debug("found more than one matched route builder, now trying to get the closest one");
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
        
        return matched.first();
    }

}
