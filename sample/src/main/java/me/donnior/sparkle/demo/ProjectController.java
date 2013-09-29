package me.donnior.sparkle.demo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.util.FLists;
import me.donnior.sparkle.Params;
import me.donnior.sparkle.annotation.Async;
import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Out;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.annotation.ResponseBody;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.view.result.HttpStatus;
import me.donnior.srape.AbstractFieldExposerModule;
import me.donnior.srape.FieldExposerModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@Controller("projects")
public class ProjectController {
    
    private final static Logger logger = LoggerFactory.getLogger(ProjectController.class);
    
    @Inject private Service service;
    
    @Out private String a;
    @Out private Service account;
    
    public String index(@Param("a") String a, @Param("b") Integer b, @Param("c") String[] c){
        System.out.println("a from param is " + a);
        this.a = a;
        return "projects/indexlaal";
    }
    
    public String show(){
        return "projects/show";
    }
    
    public String index2(Params params){
        System.out.println("param account : " + params.get("account"));
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
    
//    @Json
//    public Map<String, String> jsons(){
//        Map<String, String> maps = new HashMap<String, String>();
//        maps.put("name", "donny");
//        maps.put("age", "10");
//        return maps;
//    }
//  
    
    public FieldExposerModule jsons(){
        this.service.hello();
        return new AbstractFieldExposerModule() {
            public void config() {
                expose(new int[]{1,2,3}).withName("ints");        //ints
                expose(new String[]{"one","two","three2"}).withName("strings");        //strings
            }
        };
    }
    
    @Json
    public List<String> json(HttpServletRequest request){
        return FLists.create("one", "two", "three", "four");
    }
    
    @ResponseBody
    public String responseBody(){
        return "";
    }
    
    public HttpStatus httpStatus(){
        return new HttpStatus(HTTPStatusCode.CREATED, "Post created");
    }
    
    public String save(){
        return "redirect: projects";
    }
    
    @Async
    public Callable<String> async0(){
        return new Callable<String>() {
            
            @Override
            public String call() throws Exception {
                return "callable";
            }
        };
    }
    
    @Async
    public String async1(){
        return "async";
    }
    
    public Callable<String> async2(){
        return new Callable<String>() {
            
            @Override
            public String call() throws Exception {
                return "callable";
            }
        };
    }
    
    public Object async3(){
        return new Callable<String>() {
            
            @Override
            public String call() throws Exception {
                return "callable";
            }
        }; 
    }
    
    public Object async4(){
        return "normal";
    }
    
    public void text(HttpServletResponse response){
        try {
//            logger.info("get text");
            response.getWriter().write("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        logger.info("should be printed");
        System.out.println("ha");
    }
}
