package org.custom.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

    public static void copyPropertiesEx(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {
        if (dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        }
        if (orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        }
        PropertyUtilsBean pub = new PropertyUtilsBean();
        PropertyDescriptor origDescriptor[] = pub.getPropertyDescriptors(orig);
        for (int index = 0; index < origDescriptor.length; index++) {
            String name = origDescriptor[index].getName();
            if (pub.isReadable(orig, name)) {
                if (pub.isWriteable(dest, name)) {
                    Object value = pub.getSimpleProperty(orig, name);
                    pub.setSimpleProperty(dest, name, value);
                }
            }
        }
    }
    
    public static <T> List<T> copyBeanList(List<?> orig, Class<T> destClass) {
        List<T> dest = new ArrayList<T>();
        T bean;
        for (int i = 0; i < orig.size(); i++) {
            try {
                bean = (T) destClass.newInstance();
                copyPropertiesEx(bean, orig.get(i));
                dest.add(bean);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return dest;
    }

    /**
     * 比较两个BEAN或MAP对象的值是否相等 如果是BEAN与MAP对象比较时MAP中的key值应与BEAN的属性值名称相同且字段数目要一致
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean domainEquals(Object source, Object target, List nonCompareFields) {
        if (source == null || target == null) {
            return false;
        }
        boolean rv = true;
        if (source instanceof Map) {
            rv = mapOfSrc(source, target, rv);
        } else {
            rv = classOfSrc(source, target, rv, nonCompareFields);
        }
        return rv;
    }

    /**
     * 源目标为MAP类型时
     *
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean mapOfSrc(Object source, Object target, boolean rv) {
        HashMap<String, String> map = new HashMap<String, String>();
        map = (HashMap) source;
        for (String key : map.keySet()) {
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                if (tarMap.get(key) == null) {
                    rv = false;
                    break;
                }
                if (!map.get(key).equals(tarMap.get(key))) {
                    rv = false;
                    break;
                }
            } else {
                String tarValue = getClassValue(target, key) == null ? "" : getClassValue(target, key).toString();
                if (!tarValue.equals(map.get(key))) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 源目标为非MAP类型时
     *
     * @param source
     * @param target
     * @param rv
     * @return
     */
    private static boolean classOfSrc(Object source, Object target, boolean rv, List nonCompareFields) {
        Class<?> srcClass = source.getClass();
        Field[] fields = srcClass.getDeclaredFields();
        for (Field field : fields) {
            String nameKey = field.getName();
            if (nonCompareFields != null && nonCompareFields.contains(nameKey)) {
                continue;
            }
            if (target instanceof Map) {
                HashMap<String, String> tarMap = new HashMap<String, String>();
                tarMap = (HashMap) target;
                String srcValue = getClassValue(source, nameKey) == null ? ""
                        : getClassValue(source, nameKey).toString();
                if (tarMap.get(nameKey) == null) {
                    rv = false;
                    break;
                }
                if (!tarMap.get(nameKey).equals(srcValue)) {
                    rv = false;
                    break;
                }
            } else {
                String srcValue = getClassValue(source, nameKey) == null ? ""
                        : getClassValue(source, nameKey).toString();
                String tarValue = getClassValue(target, nameKey) == null ? ""
                        : getClassValue(target, nameKey).toString();
                if (!srcValue.equals(tarValue)) {
                    rv = false;
                    break;
                }
            }
        }
        return rv;
    }

    /**
     * 根据字段名称取值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getClassValue(Object obj, String fieldName) {
        if (obj == null) {
            return null;
        }
        try {
            Class beanClass = obj.getClass();
            Method[] ms = beanClass.getMethods();
            for (int i = 0; i < ms.length; i++) {
                // 非get方法不取
                if (!ms[i].getName().startsWith("get")) {
                    continue;
                }
                Object objValue = null;
                try {
                    objValue = ms[i].invoke(obj, new Object[] {});
                } catch (Exception e) {
                    continue;
                }
                if (objValue == null) {
                    continue;
                }
                if (ms[i].getName().toUpperCase().equals(fieldName.toUpperCase())
                        || ms[i].getName().substring(3).toUpperCase().equals(fieldName.toUpperCase())) {
                    return objValue;
                } else if (fieldName.toUpperCase().equals("SID") && (ms[i].getName().toUpperCase().equals("ID")
                        || ms[i].getName().substring(3).toUpperCase().equals("ID"))) {
                    return objValue;
                }
            }
        } catch (Exception e) {
            // logger.info("取方法出错！" + e.toString());
        }
        return null;
    }
}
