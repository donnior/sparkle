package me.donnior.sparkle.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MessageCipher {
      
    /** 
     * 密钥算法 
    */  
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

//    private byte[] key;
    private Key sKey;
    private byte[] iv;

    public MessageCipher(){}

    public  byte[] initSecretKey(byte[] secret) {
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
            random.setSeed(secret);
            kg.init(128, random);
            //生成一个密钥
            SecretKey  secretKey = kg.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }

    }


    /** 
     * 转换密钥 
     *  
     * @param key   二进制密钥 
     * @return 密钥 
     */  
    public  Key toKey(byte[] key){
        //生成密钥  
        return new SecretKeySpec(key, KEY_ALGORITHM);  
    }  
      
    /** 
     * 加密 
     *  
     * @param data  待加密数据 
     * @param key   密钥 
     * @return byte[]   加密数据 
     * @throws Exception 
     */  
    public  byte[] encrypt(byte[] data,Key key) throws Exception{
        return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }

    public  byte[] encrypt(byte[] data) throws Exception{
        Key k = this.getKey(); //Key k = this.toKey(this.key);
        return encrypt(data, k, DEFAULT_CIPHER_ALGORITHM);
    }

    public Key getKey() {
        return this.sKey;
    }

    public void setKey(Key sKey){
        this.sKey = sKey;
    }

    /** 
     * 加密 
     *  
     * @param data  待加密数据 
     * @param key   二进制密钥 
     * @return byte[]   加密数据 
     * @throws Exception 
     */  
    public  byte[] encrypt(byte[] data,byte[] key) throws Exception{
        return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);  
    }  
      
      
    /** 
     * 加密 
     *  
     * @param data  待加密数据 
     * @param key   二进制密钥 
     * @param cipherAlgorithm   加密算法/工作模式/填充方式 
     * @return byte[]   加密数据 
     * @throws Exception 
     */  
    public  byte[] encrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥  
        Key k = toKey(key);  
        return encrypt(data, k, cipherAlgorithm);  
    }  
      
    /** 
     * 加密 
     *  
     * @param data  待加密数据 
     * @param key   密钥 
     * @param cipherAlgorithm   加密算法/工作模式/填充方式 
     * @return byte[]   加密数据 
     * @throws Exception 
     */  
    public  byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        return this.doFinal(data,Cipher.ENCRYPT_MODE, key, cipherAlgorithm);
    }

    public byte[] doFinal(byte[] data, int model, Key key,String cipherAlgorithm)throws Exception{
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(model, key, iv());
//        byte[] updated = cipher.update(data);
//        return cipher.doFinal(updated);
        return cipher.doFinal(data);

    }

      
    /** 
     * 解密 
     *  
     * @param data  待解密数据 
     * @param key   二进制密钥 
     * @return byte[]   解密数据 
     * @throws Exception 
     */  
    public  byte[] decrypt(byte[] data,byte[] key) throws Exception{
        return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
    }  
      
    /** 
     * 解密 
     *  
     * @param data  待解密数据 
     * @param key   密钥 
     * @return byte[]   解密数据 
     * @throws Exception 
     */  
    public  byte[] decrypt(byte[] data,Key key) throws Exception{
        return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
    }

    public  byte[] decrypt(byte[] data) throws Exception{
        Key k = this.getKey();
        //Key k = this.toKey(this.key);
        return decrypt(data, k,DEFAULT_CIPHER_ALGORITHM);
    }

    /** 
     * 解密 
     *  
     * @param data  待解密数据 
     * @param key   二进制密钥 
     * @param cipherAlgorithm   加密算法/工作模式/填充方式 
     * @return byte[]   解密数据 
     * @throws Exception 
     */  
    public  byte[] decrypt(byte[] data,byte[] key,String cipherAlgorithm) throws Exception{
        //还原密钥  
        Key k = toKey(key);  
        return decrypt(data, k, cipherAlgorithm);  
    }  
  
    /** 
     * 解密 
     *  
     * @param data  待解密数据 
     * @param key   密钥 
     * @param cipherAlgorithm   加密算法/工作模式/填充方式 
     * @return byte[]   解密数据 
     * @throws Exception 
     */  
    public  byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
        return this.doFinal(data, Cipher.DECRYPT_MODE, key, cipherAlgorithm);
    }

    private IvParameterSpec iv(){
//        byte[] iv = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//        return new IvParameterSpec(iv);
        return new IvParameterSpec(this.iv);
    }
      
    private static String  showByteArray(byte[] data){  
        if(null == data){  
            return null;  
        }  
        StringBuilder sb = new StringBuilder("{");  
        for(byte b:data){  
            sb.append(b).append(",");  
        }  
        sb.deleteCharAt(sb.length()-1);  
        sb.append("}");  
        return sb.toString();  
    }

    public byte[] random_iv(){
        SecureRandom random = new SecureRandom();
        byte[] ivs = new byte[16];
        random.nextBytes(ivs);
        this.iv = ivs;
        return this.iv;
    }

    public void setIV(byte[] iv) {
        this.iv = iv;
    }

    public static void main(String[] args) throws Exception {

//        byte[] bKey = MyKeyGenerator.initSecretKey("this is key".getBytes());
//        Key k = MyKeyGenerator.toKey(bKey); v   b

        //String password = "this is key";
        String password = "b14e9b5b720f84fe02307ed16bc1a32ce6f089e10f7948422ccf3349d8ab586869c11958c70f46ab4cfd51f0d41043b7b249a74df7d53c7375d50f187750a0f5";


        Key k = AESKeyGenerator.generateKey(password.getBytes());
        System.out.println("key for encrypt："+showByteArray(k.getEncoded()));


        MessageCipher cipher = new MessageCipher();
        cipher.setKey(k);
        byte[] iv = cipher.random_iv();

//        String data ="AES数据";
        String data = "{\"session_id\"=>\"e2c4ca694aa02905ab9d4bcb051fe68c\", \"github_username\"=>\"neerajdotname\"}";
        System.out.println("加密前数据: string:"+data);  
        System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));  
        System.out.println();  
        byte[] encryptData = cipher.encrypt(data.getBytes());
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));  
        System.out.println("加密后数据: hexStr:"+Hex.encodeHexStr(encryptData));  
        System.out.println();


        k = AESKeyGenerator.generateKey(password.getBytes());
        System.out.println("key for decrypt："+showByteArray(k.getEncoded()));

        cipher = new MessageCipher();
        cipher.setKey(k);
        cipher.setIV(iv);

        byte[] decryptData = cipher.decrypt(encryptData);
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));  
        System.out.println("解密后数据: string:"+new String(decryptData));  
          
    }

}