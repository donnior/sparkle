package me.donnior.sparkle.condition;


public interface ConditionItem {
    
    boolean match(String realValue);

    String getName();
    
    String getValue();

}
