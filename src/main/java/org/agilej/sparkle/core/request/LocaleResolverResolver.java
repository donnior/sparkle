package org.agilej.sparkle.core.request;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.core.config.ConfigResult;

/**
 * resolver for {@link LocaleResolver}, will fallback to a default locale resolver if user not config it.
 */
public class LocaleResolverResolver {

    private ConfigResult config;

    public LocaleResolverResolver(ConfigResult config){
        this.config = config;
    }

    /**
     * resolve locale resolver from config, if user not config it must return a default locale resolver.
     * @return
     */
    public LocaleResolver resolve(){
        Class<? extends LocaleResolver> localeResolverClass = config.getLocaleResolverClass();
        if (localeResolverClass == null || AcceptHeaderLocaleResolver.class.equals(localeResolverClass)) {
            return defaultLocaleResolver();
        } else {
            return (LocaleResolver) ReflectionUtil.initialize(localeResolverClass);
        }
    }


    /**
     * return a default locale resolver instance, which is {@link AcceptHeaderLocaleResolver}
     * @return
     */
    public LocaleResolver defaultLocaleResolver(){
        return new AcceptHeaderLocaleResolver();
    }

}
