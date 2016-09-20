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
        Properties properties = new Properties();
        InputStream inputStream = PropertyUtils.class
                .getResourceAsStream(_propertiesName);
//         通过类加载器读取
//        properties.load(new FileReader(_propertiesName));
//        通过类加载器的路径来读取配置文件
//        properties.load(PropertyUtils.class
//                .getResourceAsStream(_propertiesName));
        try {
            properties.load(inputStream);
//            // 读取属性
//            properties.getProperty(key);
//            properties.getProperty(key, defaultValue);
//            // 修改属性
//            properties.setProperty(key, value);
            if (properties.getProperty(key) != null
                    && !"".equals(properties.getProperty(key))) {
                value = properties.getProperty(key).trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                properties.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return value;
    }
    
}
