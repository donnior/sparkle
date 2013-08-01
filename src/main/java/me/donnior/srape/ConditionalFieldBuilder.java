package me.donnior.srape;

public interface ConditionalFieldBuilder {

    public void unless(boolean condition);
    
    public void when(boolean condition);
    
}
