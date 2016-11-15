package org.agilej.sparkle.core.config;

import org.agilej.fava.FList;
import org.agilej.sparkle.interceptor.Interceptor;

public interface InterceptorConfiguration {

    FList<Interceptor> getInterceptors();

}
