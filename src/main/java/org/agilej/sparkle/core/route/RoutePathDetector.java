package org.agilej.sparkle.core.route;

import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoutePathDetector {

    private static final Pattern PATH_TEMPLATE_PATTERN = Pattern.compile("\\{(.*?)\\}");

    private final Pattern matcherPatten;
    private String matcherPattenDescrtiption = null;

    private List<String> pathVariables = new ArrayList<String>();


    public RoutePathDetector(String src) {
        if(Strings.count(src, "{") != Strings.count(src, "}")){
            throw new SparkleException("{ and } not match in " + src);
        }
        Matcher m = PATH_TEMPLATE_PATTERN.matcher(src);
        while(m.find()) {
            String matched = m.group(1);
            if(Strings.isCharacterOrDigit(matched)){
                this.pathVariables.add(matched);
            } else {
                throw new SparkleException(matched + " is invalid in " + src);
            }
        }
        constructMatcherRegexPattern(src);
        this.matcherPatten = Pattern.compile(this.matcherPattenDescrtiption);
    }
    
    private void constructMatcherRegexPattern(String source) {
        //match to route like "/projects/{id}/members/{name}";
        String result = source;
        for(String v : this.pathVariables){
            result = result.replaceAll("\\{"+v+"\\}", "([^/]+)");
        }
        this.matcherPattenDescrtiption = result;
    }

    public String matcherPattenDescription() {
        return matcherPattenDescrtiption;
    }


    public List<String> pathVariables() {
        return pathVariables;
    }

    //TODO 重构此方法，放至合适的地方
    public List<String> extractPathVariableValues(String path){
        List<String> variables = new ArrayList<String>();

        Matcher matcher = this.matcherPatten.matcher(path);

        if (matcher.matches()) {
            int count = matcher.groupCount();
            for (int index = 1; index <= count; index++) {
                String group = matcher.group(index);
                variables.add(group);
            }
        }

        return variables;
    }


    public Map<String, String > pathVariables(String path) {
        List<String> values = this.extractPathVariableValues(path);
        List<String> names = this.pathVariables();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < names.size(); i++) {
            map.put(names.get(i), values.get(i));
        }
        return map;
    }

    public Pattern matcherPattern() {
        return this.matcherPatten;
    }

    public boolean matches(String path) {
        return this.matcherPatten.matcher(path).matches();
    }
}
