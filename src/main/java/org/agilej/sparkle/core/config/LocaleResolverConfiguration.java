package org.agilej.sparkle.core.config;

import org.agilej.sparkle.mvc.LocaleResolver;

public interface LocaleResolverConfiguration {

    Class<? extends LocaleResolver> getLocaleResolverClass();

}
