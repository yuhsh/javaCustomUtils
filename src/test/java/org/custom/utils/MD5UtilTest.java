package org.custom.utils;

import org.junit.Test;

public class MD5UtilTest {

    @Test
    public void testSend() {
        System.out.println(MD5Util.encode2bytes("MATICSOFT"));
        System.out.println(MD5Util.encode2hex("MATICSOFT").toUpperCase());
        System.out.println(MD5Util.getMd5("MATICSOFT").toUpperCase());
        System.out.println(MD5Util.toMD5("MATICSOFT").toUpperCase());
    }
}
