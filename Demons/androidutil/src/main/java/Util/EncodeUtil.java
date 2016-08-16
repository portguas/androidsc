/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package Util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class EncodeUtil {

    private static String RSA_TYPE = "RSA";
    private static int RSA_KEY_LENGTH = 1024;

    private static String ENCRYPT_TYPE_RSA_URLSAFE = "3";

    private static String PUBLICKEY_COMMON =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCuLglFmSzBu7SwESKy4RUCD2lYXdPZFKXyI0PQBHyG8VwtQsTMz0M9TMgffil"
                    + "M3pIE3sk+OtEMoJlPD52FEj+QG+mftCYWSvcuUliCK8RUhtyruSZoWveNes5NN80m3xBNoBMKKRiJwT2W"
                    + "/fv3dt2o7erFenb7EHYivNyDH85VrwIDAQAB";

    public static String encode(byte[] data) {
        byte[] base64Data = encodeByRSA(data, PUBLICKEY_COMMON, Base64Util.NO_WRAP, ENCRYPT_TYPE_RSA_URLSAFE, false);

        try {
            String urlEncodeStr = URLEncoder.encode(new String(base64Data), "utf-8");
            return urlEncodeStr;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new String(data);
    }

    private static byte[] encodeByRSA(byte[] data, String key, int base64Flag, String encryptMode, boolean isNeedZip) {
        try {
            byte[] zipUserData = data;
            if (isNeedZip) {
                zipUserData = ZipUtil.compress(data);
            }

            byte[] dataEncrypt = encryptByRSAWithPiece(zipUserData, Base64Util.decode(key));
            byte[] dataBase64 = Base64Util.encode(dataEncrypt, base64Flag);

            StringBuffer sb = new StringBuffer();
            sb.append(encryptMode);
            sb.append(new String(dataBase64));

            return sb.toString().getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] encryptByRSAWithPiece(byte[] userData, byte[] publicKey) throws Exception {
        int pageLen = RSA_KEY_LENGTH / 8 - 11;
        int userDataLen = userData.length;
        int model = userDataLen % pageLen;
        int devider = userDataLen / pageLen;
        int pageCount = (model == 0) ? devider : (devider + 1);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_TYPE);
        Key key = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] result = null;
        for (int i = 0; i < pageCount; i++) {
            byte[] pageUserData;
            if ((i == pageCount - 1) && model != 0) {
                pageUserData = new byte[model];
            } else {
                pageUserData = new byte[pageLen];
            }

            for (int j = 0; j < pageLen; j++) {
                int index = i * pageLen + j;
                if (index < userDataLen) {
                    pageUserData[j] = userData[index];
                }
            }

            byte[] pageResult = cipher.doFinal(pageUserData);
            if (result == null) {
                result = pageResult;
            } else {
                result = concatArray(result, pageResult);
            }
        }

        return result;
    }

    private static byte[] concatArray(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

}
