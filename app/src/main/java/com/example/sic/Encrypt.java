package com.example.sic;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.security.keystore.KeyProperties;

import com.google.android.gms.common.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encrypt {
    private static final String TSE_KEY_ALIAS = "TSE_DATA";

    @TargetApi(Build.VERSION_CODES.M)
    public static String encrypt(Context context, String data) throws Exception {
        byte[] srcBytes = data.getBytes(StandardCharsets.UTF_8);
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Cipher cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        SecretKey secretKey = (SecretKey) keyStore.getKey(TSE_KEY_ALIAS, null);
        IvParameterSpec params = new IvParameterSpec("1234567890123456".getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, params);
        byte[] cipherText = cipher.doFinal(srcBytes);
        return Base64Utils.encode(cipherText).replace("\n", "");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static String decrypt(Context context, String data) throws Exception {
        byte[] src = Base64Utils.decode(data);
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        Cipher cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

        SecretKey secretKey = (SecretKey) keyStore.getKey(TSE_KEY_ALIAS, null);
        IvParameterSpec params = new IvParameterSpec("1234567890123456".getBytes());

        cipher.init(Cipher.DECRYPT_MODE, secretKey, params);
        byte[] cipherText = cipher.doFinal(src);
        return new String(cipherText);
    }

}