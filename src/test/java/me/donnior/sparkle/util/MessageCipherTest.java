package me.donnior.sparkle.util;


import me.donnior.sparkle.core.request.Serializer;
import org.junit.Test;

import java.security.Key;

import static org.junit.Assert.assertEquals;

public class MessageCipherTest {


    public void testEncrypt(){

    }

    @Test
    public void testDecrypt(){
        Serializer serializer = new Serializer() {
            @Override
            public byte[] dump(Object obj) {
                return ((String)obj).getBytes();
            }

            @Override
            public Object load(byte[] bytes) {
                return new String(bytes);
            }
        };
        String encrypted = "V2pWRmliT0VXcDBnTzZoQ1E1NDFUaTI3Y05LcElCbWJCS21leXFLR1o1ek1TZEdtaEkvbXFnVklkeURGbUl4eE9CdzBreGFUT1BaZ3MxaEY3Q3p1dnErSzd0RWc4RFlQQ2VaQllQYnN2ZHV5bnZHYzJ2SEZCT3ZGdTcxUEJPNnItLTJ0TGlrQ3VkdFJ6KzB6MlJiNHNxbnc9PQ==--4cd8ae9f790f9416f81c582ae10029d9cb76ae42";
        String secretBase = "b14e9b5b720f84fe02307ed16bc1a32ce6f089e10f7948422ccf3349d8ab586869c11958c70f46ab4cfd51f0d41043b7b249a74df7d53c7375d50f187750a0f5";

        Key k = AESKeyGenerator.generateKey(secretBase.getBytes());
        MessageEncryptor decryptor = new MessageEncryptor(k, serializer);
        String decoded = (String) decryptor.decryptAndVerify(encrypted);

        String expected = "{\"session_id\"=>\"e2c4ca694aa02905ab9d4bcb051fe68c\", \"github_username\"=>\"neerajdotname\"}";
        assertEquals(expected, decoded);

    }


}
