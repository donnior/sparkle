package me.donnior.sparkle.demo;

import java.util.List;

import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.annotation.ResponseBody;
import me.donnior.sparkle.view.HttpStatus;

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
    
    public String normalView(){
        return "";
    }
    
    public String redirectView(){
        return "redirect:users";
    }
    
    @Json
    public List<Object> jsonView(){
        
        return null;
    }
    
    @ResponseBody
    public String responseBody(){
        return "";
    }
    
    
    public HttpStatus httpStatus(){
        return new HttpStatus(201, "Post created");
    }
    
    
    public String save(){
        System.out.println("projects save called");
        return "projects/index";
    }

}
