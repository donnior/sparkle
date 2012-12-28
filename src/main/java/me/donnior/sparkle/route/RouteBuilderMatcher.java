package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;


public class RouteBuilderMatcher {
	
	private HttpServletRequest request;
	private RoutingBuilder builder;

	public RouteBuilderMatcher(RoutingBuilder builder, HttpServletRequest request) {
		this.builder = builder;
		this.request = request;
	}
	
	public boolean match(){

        final String path = extractPath(request);
        final HTTPMethod method = extractMethod(request);
        
        boolean pathAndMethodMatched = builder.matchPath(path) && (builder.getHttpMethod() == method); 
        if(!pathAndMethodMatched){
        	return false;
        } else {
        	ConditionMatchResult paramMatchResult = builder.matchParam(request);
        	if(!paramMatchResult.succeed()){
        		return false;
        	}
        	if(paramMatchResult.isExplicitMatch()){
        		//add this result to matched condition arrays
        	}

        	ConditionMatchResult headerMatchResult = builder.matchHeader(request);
        	if(!headerMatchResult.succeed()){
        		return false;
        	}
        	if(headerMatchResult.isExplicitMatch()){
        		//add this result to matched condition arrays
        	}
        	
        	ConditionMatchResult consumeMatchResult = builder.matchConsume(request);
        	if(!consumeMatchResult.succeed()){
        		return false;
        	}
        	if(consumeMatchResult.isExplicitMatch()){
        		//add this result to matched condition arrays
        	}
        	return true;
        }
	}
	
	public MatchedCondition[] matchedExplicitConditions(){
		return new MatchedCondition[]{};
	}
	
	public RoutingBuilder getBuilder() {
		return builder;
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
        
//        System.out.println("c&a string : " + cAndActionString);
        return cAndActionString;
    }

}
