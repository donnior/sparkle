package me.donnior.sparkle.util;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * key generator use aes algorithm
 */
public class AESKeyGenerator {

    private static final String KEY_ALGORITHM = "AES";

    /**
     * 转换密钥
     *
     * @param key   二进制密钥
     * @return 密钥
     */
    public static  Key toKey(byte[] key){
        //生成密钥
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * use seed to generate a key
     * @param seed
     * @return
     */
    public static  Key generateKey(byte[] seed){
        return new SecretKeySpec(initSecretKey(seed), KEY_ALGORITHM);
    }

    /**
     * use seed to generate a key
     * @param seed
     * @return key's byte
     */
    public  static byte[] initSecretKey(byte[] seed) {
        //返回生成指定算法的秘密密钥的 KeyGenerator 对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
        //初始化此密钥生成器，使其具有确定的密钥大小
        //AES 要求密钥长度为 128
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(seed);
            kg.init(128, random);
            //生成一个密钥
            SecretKey  secretKey = kg.generateKey();
            return secretKey.getEncoded();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

}
