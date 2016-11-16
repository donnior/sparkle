package org.agilej.sparkle.core.argument;

import java.util.List;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;

/**
 * resolver for argument resolver manager hold lots of argument resolver, and use the match
 * one to resolve argument
 */
public interface ArgumentResolverResolver {

    ArgumentResolver resolve(ActionMethodParameter parameter);

    /*
    List<ArgumentResolver> registeredResolvers();
    */
}
