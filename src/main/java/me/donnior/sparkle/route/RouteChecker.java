package me.donnior.sparkle.route;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.donnior.sparkle.util.Strings;

public class RouteChecker {

    private static final Pattern p = Pattern.compile("\\{(.*?)\\}");
    
    private List<String> pathVariables = new ArrayList<String>();
    private String matcherRegexPatten = null;
    
    public RouteChecker(String src) {
        if(Strings.count(src, "{") != Strings.count(src, "}")){
            throw new RuntimeException("{ and } not match in " + src);
        }
        Matcher m = p.matcher(src);
        while(m.find()) {
            String matched = m.group(1);
            if(!isCharacterOrDigit(matched)){
                throw new RuntimeException(matched + " is invalid in " + src);
            } else {
                this.pathVariables .add(matched);
            }
        }
        
        constructMatcherRegexPattern(src);
    }
    
    private void constructMatcherRegexPattern(String source) {
        //match to route like "/projects/{id}/members/{name}";
        String result = source;
        for(String v : this.pathVariables){
            result = result.replaceAll("\\{"+v+"\\}", "([^/]+)");
        }
        this.matcherRegexPatten = result;
    }

    public String matcherRegexPatten() {
        return matcherRegexPatten;
    }


    public List<String> pathVariables() {
        return pathVariables;
    }
    
    public boolean isCorrectRoute(String src){
        if(Strings.count(src, "{") != Strings.count(src, "}")){
            throw new RuntimeException("{ and } not match in " + src);
        }
        Matcher m = p.matcher(src);
        while(m.find()) {
            String matched = m.group(1);
            if(!isCharacterOrDigit(matched)){
                throw new RuntimeException(matched + " is invalid in " + src);
            }
        }
        return true;
    }

    private boolean isCharacterOrDigit(String matched) {
        return matched.matches("\\w*");
    }
       
}
