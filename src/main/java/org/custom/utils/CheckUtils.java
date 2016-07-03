/**
 * 各种检查工具类
 * @author yuhaisheng
 */
package org.custom.utils;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.custom.constant.UtilConstants;

public class CheckUtils {

    /**
     * NULL检查
     * @param _obj 检查对象
     * @return NULL：true、非Null：false
     */
    public static boolean isNull(Object _obj) {
        if (_obj != null) {
            return false;
        }
        return true;
    }

    /**
     * 字符串为空检查
     * @param _str 检查字符串
     * @return NULL或""：true、非Null或非""：false
     */
    public static boolean isNullOrEmpty(String _str) {
        if (isNull(_str) || UtilConstants.EMPTY.equals(_str)) {
            return true;
        }
        return false;
    }

    /**
     * 日期字符串合法性检查
     * @param value 日期字符串
     * @param format 日期格式
     * @return 检查结果（合法：true、以为：false）
     */
    public static boolean isDate(String value, String format) {
        if (isNullOrEmpty(value) || isNullOrEmpty(format)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setLenient(false);
        try {
            sdf.applyPattern(format);
        } catch (IllegalArgumentException e) {
            return false;
        }
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(value, pos);
        if (isNull(date) || pos.getIndex() != value.length()) {
            return false;
        }
        return true;
    }
}
