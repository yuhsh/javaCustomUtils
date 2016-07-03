/**
 * 汉语拼音转换工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.util.HashSet;
import java.util.Set;

import org.custom.constant.UtilConstants;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtils {

  /**
   * 获取拼音集合
   * @param src
   * @return Set<String>
   */
  public static Set<String> getPinyin(String src, String distinguish) {
    if (src != null && !src.trim().equalsIgnoreCase(UtilConstants.EMPTY)) {
      char[] srcChar;
      srcChar = src.toCharArray();
      // 汉语拼音格式输出类
      HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();

      // 输出设置，大小写，音标方式等
      hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
      hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
      hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

      String[][] temp = new String[src.length()][];
      for (int i = 0; i < srcChar.length; i++) {
        char c = srcChar[i];
        // 是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
        if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
          try {
            temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
            if (temp[i] == null) {
              return null;
            }
          } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
          }
        } else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122) || (c == ' ')) {
          temp[i] = new String[] { String.valueOf(srcChar[i]) };
        } else {
          temp[i] = new String[] {UtilConstants.EMPTY};
        }
      }
      String[] pingyinArray = Exchange(temp, distinguish);
      Set<String> pinyinSet = new HashSet<String>();
      for (int i = 0; i < pingyinArray.length; i++) {
        pinyinSet.add(pingyinArray[i]);
      }
      return pinyinSet;
    }
    return null;
  }

  /**
   * 首字母大写
   * @param name 变换字符串
   * @return 首字母大写字符串
   */
  public static String captureName(String name) {
    char[] cs = name.toCharArray();
    if (cs.length > 0) {
      cs[0] -= 32;
      return String.valueOf(cs);
    }
    return name;
  }

  /**
   * 首字母取得
   * @param name 变换字符串
   * @return 首字母取得字符串
   */
  public static String captureOne(String name) {
    char[] cs = name.toCharArray();
    if (cs.length > 0) {
      return String.valueOf(cs[0]);
    }
    return name;
  }

  /**
   * 取得转换字符串
   * @param strJaggedArray 转换字符串
   * @param distinguish 区分
   * @return 取得转换字符串
   */
  public static String[] Exchange(String[][] strJaggedArray, String distinguish) {
    String[][] temp = strJaggedArray;
    if ("0".equals(distinguish)) {
      for (int row = 0; row < strJaggedArray.length; row++) {
        String[] array = strJaggedArray[row];
        for (int col = 0; col < array.length; col++) {
          strJaggedArray[row][col] = captureOne(strJaggedArray[row][col]);
        }
      }
    }
    temp = DoExchange(strJaggedArray);
    return temp[0];
  }

  /**
   * 递归取得转换字符串
   * @param strJaggedArray 转换字符串
   * @return 取得转换字符串
   */
  private static String[][] DoExchange(String[][] strJaggedArray) {
    int len = strJaggedArray.length;
    if (len >= 2) {
      int len1 = strJaggedArray[0].length;
      int len2 = strJaggedArray[1].length;
      int newlen = len1 * len2;
      String[] temp = new String[newlen];
      int Index = 0;
      for (int i = 0; i < len1; i++) {
        for (int j = 0; j < len2; j++) {
          temp[Index] = strJaggedArray[0][i] + strJaggedArray[1][j];
          Index++;
        }
      }
      String[][] newArray = new String[len - 1][];
      for (int i = 2; i < len; i++) {
        newArray[i - 1] = strJaggedArray[i];
      }
      newArray[0] = temp;
      return DoExchange(newArray);
    } else {
      return strJaggedArray;
    }
  }
}
