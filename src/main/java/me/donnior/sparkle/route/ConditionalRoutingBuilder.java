package me.donnior.sparkle.route;

public interface ConditionalRoutingBuilder extends LinkedRoutingBuilder{
	
	ConditionalRoutingBuilder matchParams(String... params);
	
	ConditionalRoutingBuilder matchHeaders(String... params);
	
	ConditionalRoutingBuilder matchConsumes(String... params);

}
