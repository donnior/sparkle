package me.donnior.sparkle.route;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouteChecker {

    private Pattern p = Pattern.compile("\\{(.*?)\\}");
    
    public boolean isCorrectRoute(String src){
        if(this.count(src, "{") != this.count(src, "}")){
            System.out.println("{ and } not match in " + src);
            throw new RuntimeException("{ and } not match in " + src);
        }
        Matcher m = p.matcher(src);
        while(m.find()) {
            String matched = m.group(1);
            if(!isValid(matched)){
                System.out.println(matched + " is invalid");
                throw new RuntimeException(matched + " is invalid in " + src);
            }
        }
        return true;
    }

    private boolean isValid(String matched) {
        return matched.matches("\\w*");
    }
    
    public int count(String input, String countString){
        return input.split("\\Q"+countString+"\\E", -1).length - 1;
    }
    
}
