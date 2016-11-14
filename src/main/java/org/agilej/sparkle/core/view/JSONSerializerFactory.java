package org.agilej.sparkle.core.view;

import org.agilej.sparkle.core.annotation.UserConfigurable;

@UserConfigurable
public interface JSONSerializerFactory {

    JSONSerializer create();

}
