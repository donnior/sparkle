package org.agilej.sparkle.core.request;


import org.agilej.sparkle.mvc.SessionStore;
import org.agilej.sparkle.util.Hex;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class AbstractSessionStore implements SessionStore {

    protected String generateSessionId(){
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];
            random.nextBytes(bytes);
            return Hex.encodeHexStr(bytes, true);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("can't generate session id");
        }
    }

}
