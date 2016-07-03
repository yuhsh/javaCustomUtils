/**
 * 数值计算工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.math.BigDecimal;
import java.util.List;

public class NumberUtils {

  /**
   * 数值加算处理。
   * @param num1 数值1
   * @param num2 数值2
   * @return 加算结果
   */
  public static BigDecimal add(BigDecimal num1, BigDecimal num2) {
    if (null == num1) {
      num1 = BigDecimal.ZERO;
    }
    if (null == num2) {
      num2 = BigDecimal.ZERO;
    }
    return num1.add(num2);
  }

  /**
   * 数值减算处理
   * @param num1 数值1
   * @param num2 数值2
   * @return 减算结果
   */
  public static BigDecimal minus(BigDecimal num1, BigDecimal num2) {
    if (null == num1) {
      num1 = BigDecimal.ZERO;
    }
    if (null == num2) {
      num2 = BigDecimal.ZERO;
    }
    return num1.subtract(num2);
  }
  
  /**
   * 数值乘算处理
   * @param num1 数值1
   * @param num2 数值2
   * @return 乘算结果
   */
  public static BigDecimal multiply(BigDecimal num1, BigDecimal num2) {
    if (null == num1) {
      num1 = BigDecimal.ZERO;
    }
    if (null == num2) {
      num2 = BigDecimal.ZERO;
    }
    return num1.multiply(num2);
  }
  
  /**
   * 数值除算处理
   * @param num1 被除数
   * @param num2 除数
   * @param scale 精确小数位
   * @param mode 舍入模式
   * @return
   */
  public static BigDecimal divide(BigDecimal num1, BigDecimal num2, int scale, int mode) {
    if (null == num1) {
      num1 = BigDecimal.ZERO;
    }
    if (num2 == null) {
      return null;
    }
    return num1.divide(num2, scale, mode);
  }
  
  /**
   * 向上舍入数值除算处理
   * @param num1 被除数
   * @param num2 除数
   * @return
   */
  public static BigDecimal divide(BigDecimal num1, BigDecimal num2) {
    return divide(num1, num2, 0, BigDecimal.ROUND_HALF_UP);
  }
  
  /**
   * 数值求余处理
   * @param num1 被除数
   * @param num2 除数
   * @return 余数
   */
  public static BigDecimal remainder(BigDecimal num1, BigDecimal num2) {
    if (null == num2 || BigDecimal.ZERO.compareTo(num2) == 0) {
      return null;
    }
    if (null == num1 || BigDecimal.ZERO.compareTo(num1) == 0) {
      return BigDecimal.ZERO;
    }
    return num1.remainder(num2);
  }
  
  /**
   * 整除判断
   * @param num1 被除数
   * @param num2 除数
   * @return 判定结果
   */
  public static boolean isDivisible(BigDecimal num1, BigDecimal num2) {
    BigDecimal result = remainder(num1, num2);
    return result.compareTo(BigDecimal.ZERO) == 0 ? true : false;
  }
  
  /**
   * 负数变换
   * @param num 数值1
   * @return 计算结果
   */
  public static BigDecimal minusMultiply(BigDecimal num) {
    if (null == num) {
      num = BigDecimal.ZERO;
    }
    return num.multiply(BigDecimal.valueOf(-1));
  }
  
  /**
   * 数值List求和处理
   * @param list 数值List
   * @return 求和结果
   */
  public static BigDecimal sum(List<BigDecimal> list) {
    if (list == null) {
      return null;
    }
    BigDecimal sum = BigDecimal.ZERO;
    for (BigDecimal each : list) {
      sum = NumberUtils.add(sum, each);
    }
    return sum;
  }
  
  /**
   * 字符串转换成int型
   * @param str 需要转换的字符串
   * @param defaultValue 默认值
   * @return 成功时返回转换后的值，失败时返回默认值
   */
  public static int StringtoInt(String str, int defaultValue) {
    if (str == null) {
      return defaultValue;
    }
    try {
        return Integer.parseInt(str);
    } catch (NumberFormatException nfe) {
        return defaultValue;
    }
  }
}
