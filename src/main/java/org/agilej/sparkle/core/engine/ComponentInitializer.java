package org.agilej.sparkle.core.engine;

import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.ext.EnvSpecific;

public interface ComponentInitializer {

    <T> T initializeComponent(ConfigResult config, EnvSpecific specific);

}
