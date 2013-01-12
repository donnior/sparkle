package me.donnior.sparkle.route;


public interface Router {

    HttpScoppedRoutingBuilder match(String path);
    
}
