package org.agilej.sparkle.core.config;

import org.agilej.sparkle.core.request.LocaleResolver;

public interface LocaleResolverConfiguration {

    Class<? extends LocaleResolver> getLocaleResolverClass();

}
