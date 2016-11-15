package org.agilej.sparkle.core.config;

import org.agilej.sparkle.core.argument.ArgumentResolver;

import java.util.List;

public interface ArgumentResolverConfiguration {

    List<ArgumentResolver> getCustomizedArgumentResolvers();

}
