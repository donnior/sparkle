package org.agilej.sparkle;

import java.io.InputStream;

public interface Multipart {
    
    String getName();
    
    InputStream getInputStream();
}
