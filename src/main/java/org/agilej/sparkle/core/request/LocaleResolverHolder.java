package org.agilej.sparkle.core.request;


import org.agilej.sparkle.exception.SparkleException;

/**
 * global locale resolver holder
 */
public class LocaleResolverHolder {

    private static LocaleResolver LOCALE_RESOLVER;

    /**
     * set global LocaleResolver instance
     * @param localeResolver to set
     */
    public static void set(LocaleResolver localeResolver){
        LOCALE_RESOLVER = localeResolver;
    }

    /**
     *
     * get the global LocaleResolver instance
     *
     * @return the global LocaleResolver instance
     * @throws SparkleException if the global LocaleResolver instance is not set
     */
    public static LocaleResolver get(){

        if (LOCALE_RESOLVER == null) {
            throw new SparkleException("LocaleResolver did not set.");
        }
        return LOCALE_RESOLVER;
    }

    /**
     * reset the global LocaleResolver instance to null
     */
    public static void reset(){
        LOCALE_RESOLVER = null;
    }

}
