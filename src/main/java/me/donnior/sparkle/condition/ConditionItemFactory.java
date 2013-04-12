package me.donnior.sparkle.condition;

import me.donnior.sparkle.exception.SparkleException;


public class ConditionItemFactory {

    private static final String NOT_EQUAL_CHARS = "!=";
    private static final String EQUAL_CHARS = "=";
    
    public static ConditionItem createItem(String param) {
        if(!isValidConditionExpression(param)){
            throw new SparkleException(param + " is not an valid condition expression");
        }
        if(isNotEqualCondition(param)){
            return new NotEqualConditionItem(param.split(NOT_EQUAL_CHARS));
        }
        if(isEqualCondition(param)){
            return new EqualConditionItem(param.split(EQUAL_CHARS));
        } else {
            return new NotNullConditionItem(param);
        }
    }

    private static boolean isValidConditionExpression(String param) {
        // TODO check condition expression is valid
        return param!=null && !"".equals(param.trim());
    }

    private static boolean isEqualCondition(String param) {
        return param.contains(EQUAL_CHARS);
    }

    private static boolean isNotEqualCondition(String param) {
        return param.contains(NOT_EQUAL_CHARS);
    }
}

abstract class AbstractConditionItem implements ConditionItem{
    protected String value;
    protected String name;
    
    public AbstractConditionItem(String[] split) {
        this.name = split[0];
        this.value = split[1];            
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public abstract boolean match(String realValue);
}

class NotEqualConditionItem extends AbstractConditionItem{
    
    public NotEqualConditionItem(String[] kv) {
        super(kv);
    }

    public boolean match(String realValue) {
        return !value.equals(realValue);
    }
    
}

class EqualConditionItem extends AbstractConditionItem{
    
    public EqualConditionItem(String[] kv) {
        super(kv);
    }

    @Override
    public boolean match(String realValue) {
        return this.value.equals(realValue);
    }
    
}

 class NotNullConditionItem implements ConditionItem{
    
    private String name;
    
    public NotNullConditionItem(String key) {
        this.name = key;
    }
    
    @Override
    public boolean match(String realValue) {
        return realValue != null;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getValue() {
        return null;
    }
    
}
