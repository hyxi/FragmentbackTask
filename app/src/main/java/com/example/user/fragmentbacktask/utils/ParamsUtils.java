package com.example.user.fragmentbacktask.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hyxi
 */
public class ParamsUtils {

    public static Map<String, String> createRequestParams(Map<String, String> jsonParams) {
        Map<String, String> params = new HashMap<>();
       // String jsonParameters = map2json(jsonParams);
        //params.put("jsonParameters", jsonParameters);
        String key = getRandom(5);
        //String md5Str = MD5.md5(jsonParameters + key);
        try {
            key = encryptDES(key, Constants.CONFIG.ENCRYPT_KEY);//URLEncoder.encode(DES.encryptDES(key, Constants.CONFIG.ENCRYPT_KEY), "utf-8");//加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("sKey", key);
       // params.put("sMd5Str", md5Str);
        //L.d("param = " + params.toString());
        return params;
    }

    public static String getRandom(final int length) {
        char[] base = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        if (length > base.length) {
            throw new IndexOutOfBoundsException("length must <= 36");
        }
        char[] c = new char[length];
        Random random = new Random();
        for (int index = 0; index < length; index++) {
            char temp;
            while (true) {
                temp = base[random.nextInt(26 + 10)];
                if (index == 0) {
                    break;
                }
                boolean equal = false;
                for (int i = 0; i < index; i++) {
                    if (temp == c[i]) {
                        equal = true;
                        break;
                    }
                }
                if (!equal) {
                    break;
                }
            }
            c[index] = temp;
        }
        return new String(c);
    }

    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return new Base64().encode(encryptedData);//BASE64Encoder()
    }

    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        byte[] byteMi = new Base64().decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }
}
