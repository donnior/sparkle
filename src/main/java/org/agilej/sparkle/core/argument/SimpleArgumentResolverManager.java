package org.agilej.sparkle.core.argument;

import java.util.List;

public class SimpleArgumentResolverManager extends AbstractArgumentResolverManager {

    public void registerArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        for (ArgumentResolver argumentResolver : argumentResolvers) {
            this.registerArgumentResolver(argumentResolver);
        }
    }

}
