package org.custom.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import flexjson.JSONDeserializer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JSONUtils {

    /**
     * JSON字符串转换为Map类型
     * @param StringJson
     * @return
     */
    public static Map<String, String> convertJson2Map(String jsonString) {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap =  (Map<String, String>) new JSONDeserializer<Map<String, String>>()
                .deserialize(jsonString);
        return returnMap;
    }
    
    /**
     * JSON字符串转换为指定javaBean对象
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertJson2Bean(String jsonString, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        T bean = (T) JSONObject.toBean(jsonObject, beanClass);
        return bean;
    }

    /**
     * JSON对象字符串转换为指定javaBean对象
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertJson2Bean(Object json, Class<T> beanClass) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        T bean = (T) JSONObject.toBean(jsonObject, beanClass);
        return bean;
    }
    
    /**
     * 将java对象转换为json字符串
     * @param bean
     * @return
     */
    public static String bean2Json(Object bean) {
        JSONObject json = JSONObject.fromObject(bean);
        return json.toString();
    }
    
    /**
     * 将javaBean对象转换为json字符串（指定属性）
     * @param bean
     * @param _nory_changes
     * @param nory
     * @return
     */
    public static String bean2Json(Object bean, String[] _nory_changes, boolean nory) {
        JSONObject json = null;
        // 转换_nory_changes指定属性
        if (nory) {
            // 反射得到指定ClassBean的所有字段名
            Field[] fields = bean.getClass().getDeclaredFields();
            String str = "";
            for (Field field : fields) {
                str += (":" + field.getName());
            }
            //  获取父类字段名
            fields = bean.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                str += (":" + field.getName());
            }
            str += ":";
            for (String s : _nory_changes) {
                str = str.replace(":" + s + ":", ":");
            }
            json = JSONObject.fromObject(bean, configJson(str.split(":")));
        } else {
        // 转换_nory_changes指定以外属性
            json = JSONObject.fromObject(bean, configJson(_nory_changes));
        }
        return json.toString();
    }
    
    private static JsonConfig configJson(String[] excludes) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(false);
        return jsonConfig;
    }

    /**
     * 将javaList转换成json字符串
     * @param beans
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String beanList2Json(List beans) {
        StringBuilder rest = new StringBuilder();
        rest.append("[");
        int size = beans.size();
        for (int i = 0; i < size; i++) {
            rest.append(bean2Json(beans.get(i)) + ((i < size - 1) ? "," : ""));
        }
        rest.append("]");
        return rest.toString();
    }
    
    /**
     * 将javaList转换成json字符串（指定属性）
     * @param beans
     * @param _nory_changes
     * @param nory
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String beanList2Json(List beans, String[] _nory_changes,
            boolean nory) {
        StringBuffer rest = new StringBuffer();
        rest.append("[");
        int size = beans.size();
        for (int i = 0; i < size; i++) {
            try {
                rest.append(bean2Json(beans.get(i), _nory_changes, nory));
                if (i < size - 1) {
                    rest.append(",");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rest.append("]");
        return rest.toString();
    }
    
    /**
     * 将JsonHash表达式中获取map
     * @param jsonString
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static Map json2Map(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonObject.get(key).toString();
            valueMap.put(key, value);
        }
        return valueMap;
    }
    
    /**
     * 将JsonHash表达式中获取List<Map>
     *
     * @param jsonString
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Map> json2MapList(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        List<Map> list = new ArrayList<Map>(size);
        for (int i = 0; i < size; i++) {
            String temp = jsonArray.getString(i);
            HashMap tempMap = (HashMap) json2Map(temp);
            list.add(tempMap);
        }
        return list;
    }
    
    /**
     * map集合转换成json格式数据
     *
     * @param map
     * @return
     */
    public static String map2Json(Map<String, ?> map, String[] _nory_changes, boolean nory) {
        String s_json = "{";
        Set<String> key = map.keySet();
        for (Iterator<?> it = key.iterator(); it.hasNext();) {
            String s = (String) it.next();
            if (map.get(s) == null) {
            } else if (map.get(s) instanceof List<?>) {
                s_json += (s + ":" + beanList2Json((List<?>) map.get(s), _nory_changes, nory));
            } else {
                JSONObject json = JSONObject.fromObject(map);
                return json.toString();
            }
            if (it.hasNext()) {
                s_json += ",";
            }
        }
        s_json += "}";
        return s_json;
    }

    /**
     * 从json数组中得到相应java数组
     * @param jsonString
     * @return
     */
    public static Object[] json2ObjectArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

    /**
     * JavaList转换成字符串
     * @param list
     * @return
     */
    public static String list2Json(List<?> list) {
        JSONArray jsonArray = JSONArray.fromObject(list);
        return jsonArray.toString();
    }
    
    /**
     * 从json对象集合表达式中得到一个java对象列表
     *
     * @param jsonString
     * @param beanClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> json2BeanList(String jsonString, Class<T> beanClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        JSONObject jsonObject;
        T bean;
        int size = jsonArray.size();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            jsonObject = jsonArray.getJSONObject(i);
            bean = (T) JSONObject.toBean(jsonObject, beanClass);
            list.add(bean);
        }
        return list;
    }
    
    /**
     * 从json数组中解析出java字符串数组
     *
     * @param jsonString
     * @return
     */
    public static String[] json2StringArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];
        int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            stringArray[i] = jsonArray.getString(i);
        }
        return stringArray;
    }

    /**
     * 从json数组中解析出java Integer型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Integer[] json2IntegerArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Integer[] integerArray = new Integer[size];
        for (int i = 0; i < size; i++) {
            integerArray[i] = jsonArray.getInt(i);
        }
        return integerArray;
    }

    /**
     * 从json数组中解析出javaLong型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Long[] json2LongArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Long[] longArray = new Long[size];
        for (int i = 0; i < size; i++) {
            longArray[i] = jsonArray.getLong(i);
        }
        return longArray;
    }

    /**
     * 从json数组中解析出java Double型对象数组
     *
     * @param jsonString
     * @return
     */
    public static Double[] json2DoubleArray(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        int size = jsonArray.size();
        Double[] doubleArray = new Double[size];
        for (int i = 0; i < size; i++) {
            doubleArray[i] = jsonArray.getDouble(i);
        }
        return doubleArray;
    }
    
    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type 要转化的类型
     * @param map  包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException,
                   IllegalAccessException,
                   InstantiationException,
                   InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class<?> className = descriptor.getPropertyType();
            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);
                Object[] args = new Object[1];
                args[0] = value;
                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
