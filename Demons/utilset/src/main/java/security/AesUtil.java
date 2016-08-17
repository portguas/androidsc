package security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zzz on 8/16/2016.
 */

public class AesUtil {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM_NAME = "AES";

    private AesUtil() {
    }

    public static byte[] encrypt(String var0, String var1, byte[] var2) throws Exception {
        SecretKeySpec var3 = new SecretKeySpec(var1.getBytes(), ALGORITHM_NAME);
        Cipher var4 = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec var5 = new IvParameterSpec(var0.getBytes());
        var4.init(1, var3, var5);
        byte[] var6 = var4.doFinal(var2);
        return var6;
    }

    public static byte[] decrypt(String var0, String var1, byte[] var2) throws Exception {
        SecretKeySpec var3 = new SecretKeySpec(var1.getBytes(), ALGORITHM_NAME);
        Cipher var4 = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec var5 = new IvParameterSpec(var0.getBytes());
        var4.init(2, var3, var5);
        byte[] var6 = var4.doFinal(var2);
        return var6;
    }
}
