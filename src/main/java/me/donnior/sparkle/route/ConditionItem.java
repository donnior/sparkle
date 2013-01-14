package me.donnior.sparkle.route;


public interface ConditionItem {
    
    boolean match(String realValue);

    String getKey();

}
