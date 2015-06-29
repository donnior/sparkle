package me.donnior.sparkle.core.resolver;

import java.util.List;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParameter;

public interface ArgumentResolverManager {
    
    Object resolve(ActionMethodParameter paramDefinition, WebRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
