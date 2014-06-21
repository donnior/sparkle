package me.donnior.sparkle;

import java.io.InputStream;

public interface Multipart {
    
    String getName();
    
    InputStream getInputStream();
}
