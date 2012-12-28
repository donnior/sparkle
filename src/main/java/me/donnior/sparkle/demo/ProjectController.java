package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;

@Controller("projects")
public class ProjectController {
    
    public String index(){
        return "projects/index";
    }
    
    public String index2(){
        return "projects/index2";
    }
    
    public String save(){
        System.out.println("projects save called");
        return "projects/index";
    }

}
