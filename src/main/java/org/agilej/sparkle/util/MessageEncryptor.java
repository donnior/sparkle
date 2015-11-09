package org.agilej.sparkle.util;

import org.agilej.sparkle.core.request.ObjectSerializer;
import org.agilej.sparkle.core.request.Serializer;

import java.security.Key;
import java.util.Base64;

public class MessageEncryptor {

    private final Key secretKey;
    private MessageVerifier verifier;
    private Serializer serializer;

    public MessageEncryptor(Key key){
        /*
        this(key, new Serializer() {
            @Override
            public byte[] dump(Object obj) {
                return ((String)obj).getBytes();
            }

            @Override
            public Object load(byte[] bytes) {
                return new String(bytes);
            }
        });
        */
        this(key, new ObjectSerializer());
    }

    public MessageEncryptor(Key key, Serializer serializer){
        this.secretKey = key;
        this.serializer = serializer;
        this.verifier = new MessageVerifier(this.secretKey.getEncoded());
    }

    //value can be any type, and dump to byte[]
    public String encryptAndSign(Object value){
        byte[] bValue = this.serializer.dump(value);
        return verifier.generate((String)_encrypt(bValue));
    }

    public Object decryptAndVerify(String value){
        byte[] bytes =  _decrypt(verifier.verify((String)value));
        Object obj = this.serializer.load(bytes);
        return obj;
    }

    //value should be byte[]
    private Object _encrypt(String value) {
        return this._encrypt(value.getBytes());
    }

    //value should be byte[]
    private String _encrypt(byte[] value) {
        MessageCipher cipher = new MessageCipher();
        cipher.setKey(this.secretKey);
        byte[] iv = cipher.random_iv();
        try {
            byte[] encryptData = cipher.encrypt(value);
            String firstData = Base64.getEncoder().encodeToString(encryptData);
            String secondData = Base64.getEncoder().encodeToString(iv);
            return firstData + "--" + secondData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //should return byte[]
    private byte[] _decrypt(String encrypted_message) {
        String[] messages = encrypted_message.split("--");
        byte[] encryptedData = Base64.getDecoder().decode(messages[0]);
        byte[] iv = Base64.getDecoder().decode(messages[1]);

        MessageCipher cipher = new MessageCipher();
        cipher.setKey(this.secretKey);
        cipher.setIV(iv);

        try {
            byte[] decryptData = cipher.decrypt(encryptedData);
            return decryptData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void main1(String[] args){
        String password = "b14e9b5b720f84fe02307ed16bc1a32ce6f089e10f7948422ccf3349d8ab586869c11958c70f46ab4cfd51f0d41043b7b249a74df7d53c7375d50f187750a0f5";

        Key k = AESKeyGenerator.generateKey(password.getBytes());

        MessageEncryptor encryptor = new MessageEncryptor(k);
        String data = "{\"session_id\"=>\"e2c4ca694aa02905ab9d4bcb051fe68c\", \"github_username\"=>\"neerajdotname\"}";
        String result = (String) encryptor._encrypt(data.getBytes());
        System.out.println(result);

        MessageEncryptor decryptor = new MessageEncryptor(k);
        String decoded = new String(decryptor._decrypt(result));
        System.out.println(decoded);
    }

    public static void main(String[] args){
        String data = "{\"session_id\"=>\"e2c4ca694aa02905ab9d4bcb051fe68c\", \"github_username\"=>\"neerajdotname\"}";

        String password = "b14e9b5b720f84fe02307ed16bc1a32ce6f089e10f7948422ccf3349d8ab586869c11958c70f46ab4cfd51f0d41043b7b249a74df7d53c7375d50f187750a0f5";
        Key k = AESKeyGenerator.generateKey(password.getBytes());

        MessageEncryptor encryptor = new MessageEncryptor(k);
        String result = encryptor.encryptAndSign(data);
        System.out.println(result);

        k = AESKeyGenerator.generateKey(password.getBytes());
        MessageEncryptor decryptor = new MessageEncryptor(k);
        String decoded = (String) decryptor.decryptAndVerify(result);
        System.out.println(decoded);
    }


}
