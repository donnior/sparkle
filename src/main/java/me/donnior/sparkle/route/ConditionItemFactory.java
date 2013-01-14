package me.donnior.sparkle.route;

public class ConditionItemFactory {

    private static final String NOT_EQUAL_CHARS = "!=";
    private static final String EQUAL_CHARS = "=";
    
    public static ConditionItem createItem(String param) {
        if(isNotEqualCondition(param)){
            return new NotEqualConditionItem(param.split(NOT_EQUAL_CHARS));
        }
        if(isEqualCondition(param)){
            return new EqualConditionItem(param.split(EQUAL_CHARS));
        } else {
            return new NotNullConditionItem(param);
        }
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
    protected String key;
    
    public AbstractConditionItem(String[] split) {
        this.key = split[0];
        if(split.length > 1){
            this.value = split[1];            
        }
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
    public abstract boolean match(String realValue);
}

class NotEqualConditionItem extends AbstractConditionItem{
    
    public NotEqualConditionItem(String[] split) {
        super(split);
    }

    public boolean match(String realValue) {
        return !value.equals(realValue);
    }
    
}

class EqualConditionItem extends AbstractConditionItem{
    
    public EqualConditionItem(String[] split) {
        super(split);
    }

    @Override
    public boolean match(String realValue) {
        return this.value.equals(realValue);
    }
    
}

 class NotNullConditionItem implements ConditionItem{
    
    private String key;
    
    public NotNullConditionItem(String key) {
        this.key = key;
    }
    
    @Override
    public boolean match(String realValue) {
        return realValue != null;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }
    
}
