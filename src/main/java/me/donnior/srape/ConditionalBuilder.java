package me.donnior.srape;

public class ConditionalBuilder {

    public void unless(boolean condition){
        
    }
    
    public void when(boolean condition){
        this.unless(!condition);
    }
}
