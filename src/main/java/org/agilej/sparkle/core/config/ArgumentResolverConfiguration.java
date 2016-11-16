package org.agilej.sparkle.core.config;

import org.agilej.sparkle.mvc.ArgumentResolver;

import java.util.List;

public interface ArgumentResolverConfiguration {

    List<ArgumentResolver> getCustomizedArgumentResolvers();

}
