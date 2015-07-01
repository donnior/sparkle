package me.donnior.sparkle.core;

public interface ControllerFactoryResolver {

    ControllerFactory get(ConfigResult config);

}
