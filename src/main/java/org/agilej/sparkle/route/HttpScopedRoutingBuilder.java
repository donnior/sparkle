package org.agilej.sparkle.route;

public interface HttpScopedRoutingBuilder extends LinkedRoutingBuilder, ConditionalRoutingBuilder {

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
