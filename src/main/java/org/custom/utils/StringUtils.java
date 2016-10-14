/**
 * 字符串处理工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.custom.constant.UtilConstants;

public class StringUtils {

    /** 16进制 */
    private static String hexString = "0123456789abcdef";
    /** 半角字符 */
    private static String half_character
      = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
    /** 全角字符 */
    private static String[] full_characters = {
            "１", "２", "３", "４", "５", "６", "７", "８", "９", "０",
            "！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ",
            "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ",
            "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ",
            "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ",
            "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ",
            "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：",
            "'", "\"", "，", "〈", "。", "〉", "／", "？" };
    /** 随机字符串取值范围 */
    public static final char[] RANDOM_STRING_RANGE_VALUE = {
      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
      'j', 'k', 'i', 'm', 'n', 'o', 'p', 'q', 'r',
      's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
      'J', 'K', 'I', 'M', 'N', 'O', 'P', 'Q', 'R',
      'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
      '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /** 表示月份 */
    public static String[] MONTH_ENGLIST_NAME = {
      "JAN", "FEB", "MAR", "APR",
      "MAY", "JUN", "JUL", "AUG",
      "SEP", "OCT", "NOV", "DEC"};

    /**
     * 获得一个UUID字符串
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
    
    /**
     * NULL转换成空字符串
     * @param value 对象字符串
     * @return 处理结果字符串
     */
    public static String nullToEmpty(String value) {
        return value == null ? UtilConstants.EMPTY : value;
    }

    /**
     * 字符串比较
     * @param first 比较基准字符串
     * @param second 比较对象字符串
     * @return 比较结果
     */
    public static Boolean equals(final Object first, final Object second) {
        if (first == null && second == null) {
            return Boolean.TRUE;
        }
        if (first == null || second == null) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(first.toString().equals(second.toString()));
    }

    /**
     * 去除字符串左边的空格
     * @param value 对象字符串
     * @return 去除空格后字符串
     */
    public static String ltrim(String target) {
        if (CheckUtils.isNullOrEmpty(target)) {
            return target;
        } else {
            return target.replaceAll("^[　 ]+", "");
        }
    }

    /**
     * 去除字符串右边的空格
     * @param value 对象字符串
     * @return 去除空格后字符串
     */
    public static String rTrim(String target) {
        if (CheckUtils.isNullOrEmpty(target)) {
            return target;
        } else {
            return target.replaceAll("[　 ]+$", "");
        }
    }

    /**
     * 去除字符串两侧字符串
     * @param value 对象字符串
     * @return 去除两侧空格字符串
     */
    public static String trim(String target) {
        if (CheckUtils.isNullOrEmpty(target)) {
            return target;
        } else {
            return target.replaceAll("^[　 ]+|[　 ]+$", "");
        }
    }

    /**
     * 从对象字符串中去除指定开头子字符串
     * @param target 对象字符串
     * @param remove 将要被移除的子字符串
     * @return 结果字符串
     */
    public static String removeStart(String target, String remove) {
        if (CheckUtils.isNullOrEmpty(target) || CheckUtils.isNullOrEmpty(remove)) {
            return target;
        }
        if (target.startsWith(remove)) {
            return target.substring(remove.length());
        }
        return target;
    }
    
    /**
     * 从对象字符串中去除指定结尾子字符串
     * @param target 对象字符串
     * @param remove 将要被移除的子字符串
     * @return 结果字符串
     */
    public static String removeEnd(String target, String remove) {
        if (CheckUtils.isNullOrEmpty(target) || CheckUtils.isNullOrEmpty(remove)) {
            return target;
        }
        if (target.endsWith(remove)) {
            return target.substring(0, target.length() - remove.length());
        }
        return target;
    }
    
    /**
     * 半角的符号转换成全角符号.(即英文字符转中文字符)
     * @param value 对象字符串
     * @return String 结果字符串
     */
    public static String changeToFull(String value) {
        String result = UtilConstants.EMPTY;
        for (int i = 0; i < value.length(); i++) {
            int pos = half_character.indexOf(value.charAt(i));
            if (pos != -1) {
                result += full_characters[pos];
            } else {
                result += value.charAt(i);
            }
        }
        return result;
    }
    
    /**
     * CharSequence串中是否一个都不包含字符数组searchChars中的字符。
     * @param charSequence 字符串
     * @param searchChars 字符数组
     * @return boolean 都不包含返回true，否则返回false。
     */
    public static boolean containsNone(CharSequence charSequence, char... searchChars) {
        if (CheckUtils.isNull(charSequence) || CheckUtils.isNull(searchChars)) {
            return true;
        }
        int csLen = charSequence.length();
        int csLast = csLen - 1;
        int searchLen = searchChars.length;
        int searchLast = searchLen - 1;
        for (int i = 0; i < csLen; i++) {
            char ch = charSequence.charAt(i);
            for (int j = 0; j < searchLen; j++) {
                if (searchChars[j] == ch) {
                    if (Character.isHighSurrogate(ch)) {
                        if (j == searchLast) {
                            // missing low surrogate, fine, like
                            // String.indexOf(String)
                            return false;
                        }
                        if (i < csLast
                                && searchChars[j + 1] == charSequence.charAt(i + 1)) {
                            return false;
                        }
                    } else {
                        // ch is in the Basic Multilingual Plane
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    /**
     * 替换占位符
     * @param messageValue 对象信息内容
     * @param paramValues 占位符替换内容
     * @return 替换后字符串
     */
    public String replacePlaceholder(String messageValue, String... paramValues) {
        // 占位符参数取得
        String[] arrayValues = paramValues;
        StringBuffer buffer = new StringBuffer();
        // 替换占位符
        for (int index = 0; index < arrayValues.length; index++) {
            buffer.delete(0, buffer.length());
            Pattern pattern = Pattern.compile("\\{" + index + "\\}");
            Matcher matcher = pattern.matcher(messageValue);
            while (matcher.find()) {
                matcher.appendReplacement(buffer, arrayValues[index]);
            }
            matcher.appendTail(buffer);
            messageValue = buffer.toString();
        }
        return buffer.toString();
    }

    /**
     * Byte数取得
     * @param value 对象字符串
     * @param encode 字符编码
     * @return 返回指定字符编码对应的Byte数，如未指定字符编码，返回Shift_JIS编码字符数
     */
    public static int getByte(String value, String encode) {
        if (!CheckUtils.isNullOrEmpty(value)) {
            try {
                if (!CheckUtils.isNullOrEmpty(value)) {
                    return value.getBytes(encode).length;
                } else {
                    return value.getBytes(UtilConstants.ENCODED_SHIFT_JIS).length;
                }
            } catch (UnsupportedEncodingException e) {
                return 0;
            }
        }
        return 0;
    }

    /**
     * 字符串进制变换
     * 字符串到16进制
     * @param value 对象字符串
     * @return 16进制字符串
     */
    public static String encode(String value) {
        byte[] bytes = value.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * 16进制到字符串变换
     * @param bytes 16进制数
     * @return 变换后字符串
     */
    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }
    
    /**
     * 文字列左补位
     * @param value 编辑对象文字列
     * @param length 编辑结果长度
     * @param ch 补位文字
     * @return 处理结果文字类
     */
    public static String lpad(String value, int length, char ch) {

        String string = nullToEmpty(value);
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < length - string.length(); i++) {
            sb.insert(0, ch);
        }
        return sb.toString();
    }

    /**
     * 文字列右补位
     * @param value 编辑对象文字列
     * @param length 编辑结果长度
     * @param ch 补位文字
     * @return 处理结果文字类
     */
    public static String rpad(String value, int length, char ch) {

        String string = nullToEmpty(value);
        StringBuilder sb = new StringBuilder(string);
        for (int i = 0; i < length - string.length(); i++) {
            sb.append(ch);
        }
        return sb.toString();
    }
    
    /**
     * 英文大文字变换
     *
     * @param str
     *            输入文字列
     * @return 转换后文字列
     */
    public static String upperCase(String str) {
      if (str == null) {
        return null;
      }
      return str.toUpperCase();
    }

    /**
     * 英文小文字变换
     *
     * @param str
     *            输入文字列
     * @return 转换后文字列
     */
    public static String lowerCase(String str) {
      if (str == null) {
        return null;
      }
      return str.toLowerCase();
    }
    
    /**
     * 产生随机符串
     * @param len 随机数长度指定
     * @return 指定长度的随机字符串
     */
    public static String genRandomStr(int len) {
      int maxNum = 62;
      int index;
      int count = 0;

      StringBuilder sb = new StringBuilder();
      Random rd = new Random();
      while(count < len) {
        index = Math.abs(rd.nextInt(maxNum));
        if (index >= 0 && index < RANDOM_STRING_RANGE_VALUE.length) {
          sb.append(RANDOM_STRING_RANGE_VALUE[index]);
          count++;
        }
      }
      return sb.toString();
    }
    
    /**
     * 产生随机符串
     * @param len 随机数长度指定
     * @return 指定长度的随机字符串
     */
    public static String genRandomVerication(int len) {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(String.valueOf((char) (rd.nextInt(10) + 48)));
        }
        return sb.toString();
    }
}
