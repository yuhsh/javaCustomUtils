/**
 * 属性文件读取工具类
 * @author yuhaisheng
 */
package org.custom.utils;

import java.io.InputStream;
import java.util.Properties;

public final class PropertyUtils {

    private PropertyUtils() {
    }

    public static String getPropertyValue(String _propertiesName, String key) {
        String value = "";
        Properties prop = new Properties();
        InputStream in = PropertyUtils.class
                .getResourceAsStream(_propertiesName);
        try {
            prop.load(in);
            if (prop.getProperty(key) != null
                    && !"".equals(prop.getProperty(key))) {
                value = prop.getProperty(key).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
