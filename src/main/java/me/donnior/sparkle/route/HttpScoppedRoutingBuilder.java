package me.donnior.sparkle.route;

public interface HttpScoppedRoutingBuilder extends LinkedRoutingBuilder, ConditionalRoutingBuilder {

    /**
     * make the route rule match http method 'GET'
     */
    LinkedRoutingBuilder withGet();

    /**
     * make the route rule match http method 'POST'
     */
    LinkedRoutingBuilder withPost();

    /**
     * make the route rule match http method 'PUT'
     */
    LinkedRoutingBuilder withPut();

    /**
     * make the route rule match http method 'DELETE'
     */
    LinkedRoutingBuilder withDelete();



}
