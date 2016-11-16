package org.agilej.sparkle.core.config;

import org.agilej.sparkle.config.Config;

/**
 * configuration result for {@link Config}
 */
public interface ConfigResult extends ControllerFactoryConfiguration, ArgumentResolverConfiguration,
        ViewRenderConfiguration, SessionStoreConfiguration, LocaleResolverConfiguration,
        InterceptorConfiguration, JSONSerializerFactoryConfiguration {

    String[] getControllerPackages();

    String getBasePackage();


}
