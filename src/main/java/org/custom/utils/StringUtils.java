package org.custom.utils;

import org.custom.constant.UtilContants;

public class StringUtils {

	/**
	 * NULL转换成空字符串
	 * @param value 对象字符串
	 * @return 处理结果字符串
	 */
    public static String nullToEmpty(String value) {
        return value == null ? UtilContants.EMPTY : value;
    }
}
