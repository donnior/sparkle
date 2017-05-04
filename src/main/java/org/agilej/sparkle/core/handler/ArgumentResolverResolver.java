package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.mvc.ArgumentResolver;

/**
 * resolver for handler resolver manager hold lots of handler resolver, and use the match
 * one to resolve handler
 */
public interface ArgumentResolverResolver {

    ArgumentResolver resolve(ActionMethodArgument parameter);

    /*
    List<ArgumentResolver> registeredResolvers();
    */
}
