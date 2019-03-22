package com.example.za_zhujiangtao.zhupro.utils;

import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.zip.CRC32;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by fanjianfeng on 2017/7/11.
 */

public class EncryptUtils {

    private final static String TAG = "EncryptUtils";
    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * @param text 要加密的字符串
     * @return 加密的字符串
     * SHA1加密
     */
    public static String SHA1(String text) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(text.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long CRC32(String text) {
        CRC32 crc32 = new CRC32();
        crc32.update(text.getBytes());
        return crc32.getValue();
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     */
    @Nullable
    public static RSAPublicKey loadPublicKey(String publicKeyStr) {
        RSAPublicKey key = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr, Base64.DEFAULT));
            key = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return key;
    }

    /**
     * 从字符串中加载公钥
     *
     * @param privateKeyStr 密钥数据字符串
     */
    @Nullable
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) {
        RSAPrivateKey key = null;
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyStr, Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return key;
    }

    /**
     * 公钥加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     */
    @Nullable
    public static byte[] encryptRSAPublic(RSAPublicKey publicKey, byte[] plainTextData) {
        byte[] output = null;
        if (publicKey == null) {
            Log.e(TAG, "public key is empty");
            return output;
        }
        Cipher cipher = null;
        try {
            // 使用默认RSA
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            output = cipher.doFinal(plainTextData);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return output;
    }

    @Nullable
    public static String signSHA256withRSAPrivate(RSAPrivateKey privateKey, String data) {
        String ret = null;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            ret = Base64.encodeToString(signature.sign(), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static boolean verifySignSHA256withRSAPublic(RSAPublicKey publicKey, String data, String sign) {
        boolean ret = false;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(data.getBytes());
            ret = signature.verify(Base64.decode(sign, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
