/**
 * 字符编码变换工具类
 */
package org.custom.utils;

import java.io.UnsupportedEncodingException;

import org.custom.constant.UtilConstants;

public class TransformCharacterUtils {

    /**
     * 将字符编码转换成US-ASCII码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toASCII(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toUTF_8(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toUTF_16LE(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toUTF_16(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_UTF_16);
    }

    /**
     * 将字符编码转换成GBK码
     * @param str 对象字符串
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String toGBK(String str) throws UnsupportedEncodingException {
        return this.changeCharset(str, UtilConstants.ENCODED_GBK);
    }

    /**
     * 字符串编码转换的实现方法
     * @param str 待转换编码的字符串
     * @param newCharset 目标编码
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     * @param str 待转换编码的字符串
     * @param oldCharset 原编码
     * @param newCharset 目标编码
     * @return 转换后字符串
     * @throws UnsupportedEncodingException
     */
    public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用旧的字符编码解码字符串。解码可能会出现异常。
            byte[] bs = str.getBytes(oldCharset);
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return null;
    }
}
