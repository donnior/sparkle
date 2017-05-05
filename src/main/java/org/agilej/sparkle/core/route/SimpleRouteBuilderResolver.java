package org.agilej.sparkle.core.route;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.agilej.fava.FList;
import org.agilej.fava.Function;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.WebRequest;

import org.agilej.sparkle.core.annotation.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SimpleRouteBuilderResolver implements RouteBuilderResolver{

    private final static Logger logger = LoggerFactory.getLogger(SimpleRouteBuilderResolver.class);
    
    private RouteBuilderHolder routeBuilderHolder;
    
    public SimpleRouteBuilderResolver(RouteBuilderHolder router) {
        this.routeBuilderHolder = router;
    }

    public  RouteBuilder match(final WebRequest webRequest) {
        
        List<RouteBuilder> rbs = this.routeBuilderHolder.getRegisteredRouteBuilders();
        FList<RouteBuilderMatcher> matched = findSucceedRouteBuilderMatcher(webRequest, rbs);
        RouteBuilderMatcher rbm = getClosestMatchedRouteBuilder(matched);
        if(rbm != null){
            RouteBuilder rb = rbm.getBuilder();
            logger.debug("Found matched route builder: {}", rb);
//          Map<String, String> uriVariables = new AntPathMatcher().extractUriTemplateVariables(rb.getPathTemplate(), path);
//            logger.debug("extracted path variables {}", uriVariables);
            return rb;
        } else {
            logger.debug("Could not find RoutingBuilder for request {} {}", webRequest.getMethod(), webRequest.getPath());
            return null;
        }
    }

    private FList<RouteBuilderMatcher> findSucceedRouteBuilderMatcher(final WebRequest webRequest, List<RouteBuilder> rbs) {
        FList<RouteBuilderMatcher> rbms = FLists.create(rbs).collect(new Function<RouteBuilder, RouteBuilderMatcher>(){
            @Override
            public RouteBuilderMatcher apply(RouteBuilder e) {
                return new RouteBuilderMatcher(e, webRequest);
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
            logger.debug("Found more than one matched route builder, now trying to get the closest one");
            Collections.sort(matched, new Comparator<RouteBuilderMatcher>(){
                @Override
                public int compare(RouteBuilderMatcher one, RouteBuilderMatcher two) {
                    MatchedCondition[] mc1 = one.matchedExplicitConditions();
                    MatchedCondition[] mc2 = two.matchedExplicitConditions();


//                    Set<MatchedCondition> s1 = new HashSet<MatchedCondition>(Arrays.asList(mc1));
//                    Set<MatchedCondition> s2 = new HashSet<MatchedCondition>(Arrays.asList(mc2));
//
//                    return s2.size() - s1.size();

                    //TODO need ensure this refactor
                    return mc2.length - mc1.length;
                }
            });
        }
        
        return matched.first();
    }

    @Override
    public RouteBuilderHolder routeBuilderHolder() {
        return this.routeBuilderHolder;
    }
}
