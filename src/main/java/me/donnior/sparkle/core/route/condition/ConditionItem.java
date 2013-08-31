package me.donnior.sparkle.core.route.condition;


public interface ConditionItem {
    
    boolean match(String realValue);

    String getName();
    
    String getValue();

}
