package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;

@Controller("projects")
public class ProjectController {
    
    public String index(){
        return "projects/index";
    }

}