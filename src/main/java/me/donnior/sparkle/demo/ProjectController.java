package me.donnior.sparkle.demo;

import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.annotation.Param;

@Controller("projects")
public class ProjectController {
    
    public String index(@Param("a") String a, @Param("b") Integer b, @Param("c") String[] c){
        System.out.println("a is " + a);
        System.out.println("b is " + b);
        System.out.println("c is " + c);
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
