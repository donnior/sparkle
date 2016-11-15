package org.agilej.sparkle.core.config;

import org.agilej.sparkle.config.Config;
import org.agilej.sparkle.core.request.LocaleResolver;
import org.agilej.sparkle.core.view.JSONSerializerFactory;

/**
 * configuration result for {@link Config}
 */
public interface ConfigResult extends ControllerFactoryConfiguration, ArgumentResolverConfiguration,
        ViewRenderConfiguration, SessionStoreConfiguration, LocaleResolverConfiguration,
        InterceptorConfiguration, JSONSerializerFactoryConfiguration {

    String[] getControllerPackages();

    String getBasePackage();


}
