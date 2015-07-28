package me.donnior.sparkle.util;

import java.util.Base64;

public class MessageVerifier {

    private final byte[] secret;

    public MessageVerifier(byte[] secret) {
        this.secret = secret;
    }

    public String generate(String value) {
        String data = Base64.getEncoder().encodeToString(value.getBytes());
        return data + "--" + this.generateDigest(data.getBytes());
    }

    /**
     * verify message is not been tampered.
     * @param signed_message
     * @return
     */
    public String verify(String signed_message) {
        String[] msgs = signed_message.split("--");
        String data = msgs[0];
        String digest = msgs[1];
        if(this.generateDigest(data.getBytes()).equals(digest)){
            return new String(Base64.getDecoder().decode(data.getBytes()));
        }
        throw new RuntimeException("verify failed");
    }

    private String generateDigest(byte[] data){
        byte[] result = Hmac.encodeHmacSHA(data, this.secret);
        return Hex.encodeHexStr(result);
    }

}
