package org.agilej.sparkle.core.ext;

import org.agilej.sparkle.core.argument.ArgumentResolver;

import java.util.List;

public interface VendorArgumentResolverProvider {

    List<ArgumentResolver> vendorArgumentResolvers();

}
