package me.donnior.sparkle;

import me.donnior.sparkle.annotation.Param;

public class ControllerForSparkleActionExecutor{

    public String index(@Param("name") String name, @Param("page") int page){
        return name + String.valueOf(page);
    }
    
    @SuppressWarnings("unused")
    private String show(){
        return null;
    }
    
    
}
