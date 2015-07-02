package me.donnior.sparkle.core.argument;

import java.util.List;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParameter;
import me.donnior.sparkle.core.argument.ArgumentResolver;

public interface ArgumentResolverManager {
    
    Object resolve(ActionMethodParameter parameter, WebRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
