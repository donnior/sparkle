package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;

@Controller("projects")
public class ProjectController {
    
    public String index(String a){
        System.out.println("a is " + a);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
