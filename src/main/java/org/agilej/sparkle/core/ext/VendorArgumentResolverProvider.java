package org.agilej.sparkle.core.ext;

import org.agilej.sparkle.mvc.ArgumentResolver;

import java.util.List;

public interface VendorArgumentResolverProvider {

    List<ArgumentResolver> vendorArgumentResolvers();

}
