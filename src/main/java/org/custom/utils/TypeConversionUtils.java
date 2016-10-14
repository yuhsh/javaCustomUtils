package org.custom.utils;

import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class TypeConversionUtils {

    /**
     * Map转换成指定类型
     * @param map
     * @param clazz
     * @return
     */
// 1.8.0
//    public static <T> T toBean(Map<?, ?> map, Class<T> clazz) {
// 1.9.3
    public static <T> T toBean(Map<String, ? extends Object> map, Class<T> clazz) {
        try {
            // 创建clazz实例
            T bean = clazz.newInstance();
            ConvertUtils.register(new DateConverter(), java.util.Date.class);
            // 使用BeanUtils.populate把map的数据封闭到bean中
            BeanUtils.populate(bean, map);
            return bean;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
