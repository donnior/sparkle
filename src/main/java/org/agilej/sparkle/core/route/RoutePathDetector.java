package org.agilej.sparkle.core.route;

import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.util.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * utility class for detect route path template with constructed pattern, used to match
 * request path and extract path variables
 */
public class RoutePathDetector {

    private static final Pattern PATH_TEMPLATE_PATTERN = Pattern.compile("\\{(.*?)\\}");

    private final Pattern matcherPatten;
    private String matcherPattenDescription = null;

    private List<String> pathVariables = new ArrayList<String>();

    /**
     *
     * constructor with given path template, the template contains path variables like '/user/{id}',
     * path variable should only character and embraced by '{}'
     *
     * @param pathTemplate
     */
    public RoutePathDetector(String pathTemplate) {
        if(Strings.count(pathTemplate, "{") != Strings.count(pathTemplate, "}")){   //quick fail
            throw new SparkleException("{ and } not match in " + pathTemplate);
        }
        Matcher m = PATH_TEMPLATE_PATTERN.matcher(pathTemplate);
        while(m.find()) {
            String matched = m.group(1);
            if(Strings.isCharacterOrDigit(matched)){
                this.pathVariables.add(matched);
            } else {
                throw new SparkleException(matched + " is invalid in " + pathTemplate);
            }
        }
        this.matcherPattenDescription = constructMatcherRegexPattern(pathTemplate, this.pathVariables);
        this.matcherPatten = Pattern.compile(this.matcherPattenDescription);
    }


    public boolean matches(String path) {
        return this.matcherPatten.matcher(path).matches();
    }


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


    public Map<String, String > pathVariableNames(String path) {
        List<String> values = this.extractPathVariableValues(path);
        List<String> names  = this.pathVariableNames();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < names.size(); i++) {
            map.put(names.get(i), values.get(i));
        }
        return map;
    }

    public Pattern matcherPattern() {
        return this.matcherPatten;
    }

    public String matcherPattenDescription() {
        return matcherPattenDescription;
    }

    public List<String> pathVariableNames() {
        return pathVariables;
    }

    private String constructMatcherRegexPattern(String source, List<String> pathVariableNames) {
        String result = source;
        for(String v : pathVariableNames){
            result = result.replaceAll("\\{"+v+"\\}", "([^/]+)");
        }
        return result;
    }

}
