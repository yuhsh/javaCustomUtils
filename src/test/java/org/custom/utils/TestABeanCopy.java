package org.custom.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.springframework.cglib.beans.BeanCopier;

public class TestABeanCopy {
    
// 给BeanUtils的ConvertUtils注册BigDecimal转换器
//    private static final BigDecimal BIGDECIMAL_NULL = null;
//    static {
//        // 这里一定要注册默认值，使用null也可以
//        BigDecimalConverter bd = new BigDecimalConverter(BIGDECIMAL_NULL);
//        ConvertUtils.register(bd, java.math.BigDecimal.class);
//    }
     
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        ABean aBean = new ABean();
        
        ABean aBeanCopy = new ABean();
        
        aBean.setBbValue("TestValue");
        aBean.setCcDate(new Date());
        //aBean.setAaValue(new BigDecimal("10"));
        
        // TODO BeanUtils.copyPropertiesEx
//        BeanUtils.copyPropertiesEx(aBeanCopy, aBean);
        
        // TODO Apache BeanUtils.copyProperties
        BeanUtils.copyProperties(aBeanCopy, aBean);
        // TODO Apache PropertyUtils
//        org.apache.commons.beanutils.PropertyUtils.copyProperties(aBeanCopy, aBean);
        // TODO Spring BeanUtils
//        org.springframework.beans.BeanUtils.copyProperties(aBean, aBeanCopy);
        // TODO Cglib BeanCopier
//        BeanCopier bc = BeanCopier.create(ABean.class, ABean.class,false);
//        bc.copy(aBean, aBeanCopy, null);

        
        System.out.println(aBeanCopy.getBbValue());
        System.out.println(aBeanCopy.getAaValue());
        System.out.println(aBeanCopy.getCcDate());
    }
}
