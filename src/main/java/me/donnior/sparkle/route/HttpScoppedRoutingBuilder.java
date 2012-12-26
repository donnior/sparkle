package me.donnior.sparkle.route;

public interface HttpScoppedRoutingBuilder extends LinkedRoutingBuilder {

    LinkedRoutingBuilder withGet();
    
    LinkedRoutingBuilder withPost();
    
}
