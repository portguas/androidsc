package security;

import java.io.UnsupportedEncodingException;

/**
 * Created by zzz on 8/16/2016.
 */

public class Base64Util {
    private static final byte[] MAP = new byte[]{(byte)65, (byte)66, (byte)67, (byte)68, (byte)69, (byte)70, (byte)71, (byte)72, (byte)73, (byte)74, (byte)75, (byte)76, (byte)77, (byte)78, (byte)79, (byte)80, (byte)81, (byte)82, (byte)83, (byte)84, (byte)85, (byte)86, (byte)87, (byte)88, (byte)89, (byte)90, (byte)97, (byte)98, (byte)99, (byte)100, (byte)101, (byte)102, (byte)103, (byte)104, (byte)105, (byte)106, (byte)107, (byte)108, (byte)109, (byte)110, (byte)111, (byte)112, (byte)113, (byte)114, (byte)115, (byte)116, (byte)117, (byte)118, (byte)119, (byte)120, (byte)121, (byte)122, (byte)48, (byte)49, (byte)50, (byte)51, (byte)52, (byte)53, (byte)54, (byte)55, (byte)56, (byte)57, (byte)43, (byte)47};

    private Base64Util() {
    }

    public static byte[] decode(byte[] var0) {
        return decode(var0, var0.length);
    }

    public static byte[] decode(byte[] var0, int var1) {
        int var2 = var1 / 4 * 3;
        if(var2 == 0) {
            return new byte[0];
        } else {
            byte[] var3 = new byte[var2];
            int var4 = 0;

            while(true) {
                byte var5 = var0[var1 - 1];
                if(var5 != 10 && var5 != 13 && var5 != 32 && var5 != 9) {
                    if(var5 != 61) {
                        int var6 = 0;
                        int var7 = 0;
                        boolean var8 = false;
                        int var9 = 0;

                        for(int var10 = 0; var10 < var1; ++var10) {
                            var5 = var0[var10];
                            if(var5 != 10 && var5 != 13 && var5 != 32 && var5 != 9) {
                                int var11;
                                if(var5 >= 65 && var5 <= 90) {
                                    var11 = var5 - 65;
                                } else if(var5 >= 97 && var5 <= 122) {
                                    var11 = var5 - 71;
                                } else if(var5 >= 48 && var5 <= 57) {
                                    var11 = var5 + 4;
                                } else if(var5 == 43) {
                                    var11 = 62;
                                } else {
                                    if(var5 != 47) {
                                        return null;
                                    }

                                    var11 = 63;
                                }

                                var9 = var9 << 6 | (byte)var11;
                                if(var7 % 4 == 3) {
                                    var3[var6++] = (byte)((var9 & 16711680) >> 16);
                                    var3[var6++] = (byte)((var9 & '\uff00') >> 8);
                                    var3[var6++] = (byte)(var9 & 255);
                                }

                                ++var7;
                            }
                        }

                        if(var4 > 0) {
                            var9 <<= 6 * var4;
                            var3[var6++] = (byte)((var9 & 16711680) >> 16);
                            if(var4 == 1) {
                                var3[var6++] = (byte)((var9 & '\uff00') >> 8);
                            }
                        }

                        byte[] var12 = new byte[var6];
                        System.arraycopy(var3, 0, var12, 0, var6);
                        return var12;
                    }

                    ++var4;
                }

                --var1;
            }
        }
    }

    public static String encode(byte[] var0, String var1) throws UnsupportedEncodingException {
        int var2 = var0.length * 4 / 3;
        var2 += var2 / 76 + 3;
        byte[] var3 = new byte[var2];
        int var4 = 0;
        int var6 = 0;
        int var7 = var0.length - var0.length % 3;

        for(int var5 = 0; var5 < var7; var5 += 3) {
            var3[var4++] = MAP[(var0[var5] & 255) >> 2];
            var3[var4++] = MAP[(var0[var5] & 3) << 4 | (var0[var5 + 1] & 255) >> 4];
            var3[var4++] = MAP[(var0[var5 + 1] & 15) << 2 | (var0[var5 + 2] & 255) >> 6];
            var3[var4++] = MAP[var0[var5 + 2] & 63];
            if((var4 - var6) % 76 == 0 && var4 != 0) {
                var3[var4++] = 10;
                ++var6;
            }
        }

        switch(var0.length % 3) {
            case 1:
                var3[var4++] = MAP[(var0[var7] & 255) >> 2];
                var3[var4++] = MAP[(var0[var7] & 3) << 4];
                var3[var4++] = 61;
                var3[var4++] = 61;
                break;
            case 2:
                var3[var4++] = MAP[(var0[var7] & 255) >> 2];
                var3[var4++] = MAP[(var0[var7] & 3) << 4 | (var0[var7 + 1] & 255) >> 4];
                var3[var4++] = MAP[(var0[var7 + 1] & 15) << 2];
                var3[var4++] = 61;
        }

        return new String(var3, 0, var4, var1);
    }
}
