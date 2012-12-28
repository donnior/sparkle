package me.donnior.sparkle.route;

public interface HttpScoppedRoutingBuilder extends LinkedRoutingBuilder, ConditionalRoutingBuilder {

    LinkedRoutingBuilder withGet();
    
    LinkedRoutingBuilder withPost();
    
}
