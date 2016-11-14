package org.agilej.sparkle.core.argument;

import java.util.List;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;

/**
 * one argument resolver manager hold lots of argument resolver, and use the match
 * one to resolve argument
 */
public interface ArgumentResolverManager {
    
    Object resolve(ActionMethodParameter parameter, WebRequest request);

    /*
    List<ArgumentResolver> registeredResolvers();
    */
}
