package org.agilej.sparkle.core.argument;

import java.util.List;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;

public interface ArgumentResolverManager {
    
    Object resolve(ActionMethodParameter parameter, WebRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
