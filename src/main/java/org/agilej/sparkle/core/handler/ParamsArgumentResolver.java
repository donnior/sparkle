package org.agilej.sparkle.core.handler;


import org.agilej.sparkle.Params;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.mvc.ArgumentResolver;

/**
 * Argument resolver for param type with {@link Params}
 */
public class ParamsArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodArgument actionMethodParameter) {
        return Params.class.equals(actionMethodParameter.paramType());
    }

    @Override
    public Object resolve(ActionMethodArgument actionMethodParameter, WebRequest request) {
        return new SimpleParams(request);
    }

    static class SimpleParams implements Params{

        private final WebRequest request;

        SimpleParams(WebRequest request){
            this.request = request;
        }

        @Override
        public String get(String name) {
            return this.request.params(name);
        }

        @Override
        public String get(String name, String defaultValue) {
            String value = get(name);
            return value != null ? value : defaultValue;
        }

        @Override
        public String[] gets(String name) {
            return this.request.getParameterValues(name);
        }

        @Override
        public <T> T get(String name, Class<T> clz) {
            throw new RuntimeException("not implemented yet");
        }

        @Override
        public Integer getInt(String name) {
            String value = get(name);
            try{
                return Integer.valueOf(value);
            } catch (NumberFormatException nfe){
                return null;
            }
        }

        @Override
        public Integer getInt(String name, Integer defaultValue) {
            String value = get(name);
            if (value == null){
                return defaultValue;
            }
            try{
                return Integer.valueOf(value);
            } catch (NumberFormatException nfe){
                return defaultValue;
            }
        }

        @Override
        public Float getFloat(String name) {
            String value = get(name);
            try{
                return Float.valueOf(value);
            } catch (NumberFormatException nfe){
                return null;
            }
        }

        @Override
        public Float getFloat(String name, Float defaultValue) {
            String value = get(name);
            if (value == null){
                return defaultValue;
            }
            try{
                return Float.valueOf(value);
            } catch (NumberFormatException nfe){
                return defaultValue;
            }
        }
    }

}
