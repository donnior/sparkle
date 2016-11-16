package org.agilej.sparkle.core.handler;


import org.agilej.fava.FList;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.mvc.ArgumentResolver;

import java.util.List;

public class ArgumentResolverRegistration {

    private FList<ArgumentResolver> builtInArgumentResolvers    = FLists.newEmptyList();
    private FList<ArgumentResolver> vendorArgumentResolvers     = FLists.newEmptyList();
    private FList<ArgumentResolver> appScopedArgumentResolvers  = FLists.newEmptyList();

    public ArgumentResolverRegistration() {
        this.registerBuiltInArgumentResolvers();
    }

    public void registerAppScopedArgumentResolver(List<ArgumentResolver> argumentResolvers) {
        this.appScopedArgumentResolvers.addAll(argumentResolvers);
    }

    public void registerVendorArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.vendorArgumentResolvers.addAll(argumentResolvers);
    }

    private void registerBuiltInArgumentResolvers(){
        this.builtInArgumentResolvers.add(new ParamAnnotationArgumentResolver());
        this.builtInArgumentResolvers.add(new WebRequestArgumentResolver());
        this.builtInArgumentResolvers.add(new PathVariableArgumentResolver());
        this.builtInArgumentResolvers.add(new ParamsArgumentResolver());
        this.builtInArgumentResolvers.add(new SessionAttributeArgumentResolver());
        this.builtInArgumentResolvers.add(new CookiesArgumentResolver());
    }

    public List<ArgumentResolver> getAllOrderedArgumentResolvers(){
        return FLists.$(this.appScopedArgumentResolvers).plus(this.builtInArgumentResolvers).plus(this.vendorArgumentResolvers);
    }

}
