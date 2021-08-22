package com.cloud.plat.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @describe x
 */
public class ObjectUtil {

    public static String mapToString(Map<String, String[]> paramMap) {

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StrUtil.endWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
            params.put(key, obj);
        }
        return new Gson().toJson(params);
    }

    public static String mapToStringAll(Map<String, String[]> paramMap) {

        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.put(key, paramValue);
        }
        return new Gson().toJson(params);
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>(16);
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param clazz  将要填充的类型
     */
    public static List<? extends Object> autoFillListEqFields(List<? extends Object> before, Class clazz) {
        List<Object> res = new ArrayList<>();
        for (int i = 0; i < before.size(); i++) {
            Object obj = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(before.get(i), obj);
            res.add(obj);
        }
        return res;
    }

    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param clazz  将要填充的类型
     */
    public static List<? extends Object> autoFillListEqFieldsWithargs(List<? extends Object> before, Class clazz,
                                                                      HashMap<String, Object> paras) throws InvocationTargetException, IllegalAccessException {
        List<Object> res = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < before.size(); i++) {
            Object obj = BeanUtils.instantiateClass(clazz);
            BeanUtils.copyProperties(before.get(i), obj);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                if (paras.keySet().contains(fields[j].getName())) {
                    try {
                        fields[j].set(obj, paras.get(fields[j].getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
            res.add(obj);
        }
        return res;
    }


    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param clazz  将要填充的类型
     */
    public static Object autoFillEqFields(Object before, Class clazz) {
        Object obj = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(before, obj);
        return obj;
    }

    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param paras 参数
     * @param clazz 将要填充的类型
     */
    public static Object autoFillMapFields(Map<String, Object> paras, Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Object obj = BeanUtils.instantiateClass(clazz);
        for (int j = 0; j < fields.length; j++) {
            fields[j].setAccessible(true);
            if (paras.keySet().contains(fields[j].getName())) {
                try {
                    fields[j].set(obj, paras.get(fields[j].getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }


    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param after  将要填充的类型
     */
    public static void autoFillEqFields(Object before, Object after) {
        BeanUtils.copyProperties(before, after);
    }


    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param after  将要填充的类型
     */
    public static void autoFillEqFieldsWithArgs(Object before, Object after) {
        BeanUtils.copyProperties(before, after);
    }


    /**
     * 将两个JavaBean里相同的字段自动填充
     *
     * @param before 原JavaBean对象
     * @param clazz  将要填充的类型
     */
    public static Object autoFillEqFieldsWithArgs(Object before, Class clazz, HashMap<String, Object> fieldNames) {
        Object obj = BeanUtils.instantiateClass(clazz);
        Field[] fields = clazz.getDeclaredFields();
        BeanUtils.copyProperties(before, obj);
        for (int i = 0; i < fields.length; i++) {
            // AccessibleTest类中的成员变量为private,故必须进行此操作
            // 取消属性的访问权限控制，即使private属性也可以进行访问。
            fields[i].setAccessible(true);
            //遍历keyset
            System.out.println(fields[i].getName());
            ////如果有属性名和key相同
            if (fieldNames.keySet().contains(fields[i].getName())) {
                // 将指定对象变量上(user) 此Field对象表示的字段设置为指定的新值。
                try {
                    fields[i].set(obj, fieldNames.get(fields[i].getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return obj;
    }

    /**
     * 功能描述      判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
     *
     * @param
     * @return
     * @author Huyt
     * @date 2019/4/17
     */
    public static boolean isAllFieldNull(Object obj) throws Exception {
        // 得到类对象
        Class stuCla = (Class) obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        //遍历属性
        for (Field f : fs) {
            // 设置属性是可以访问的(私有的也可以)
            f.setAccessible(true);
            // 得到此属性的值
            Object val = f.get(obj);
            //只要有1个属性不为空,那么就不是所有的属性值都为空
            if (val != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 功能描述      判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
     *
     * @param
     * @return
     * @author Huyt
     * @date 2019/4/17
     */
    public static boolean isAllFieldNotNull(Object obj) throws Exception {
        // 得到类对象
        Class stuCla = (Class) obj.getClass();
        //得到属性集合
        Field[] fs = stuCla.getDeclaredFields();
        boolean flag = true;
        //遍历属性
        for (Field f : fs) {
            // 设置属性是可以访问的(私有的也可以)
            f.setAccessible(true);
            // 得到此属性的值
            Object val = f.get(obj);
            //只要有1个属性不为空,那么就不是所有的属性值都为空
            if (val == null) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 功能描述      判断该对象是否: 返回ture表示所有属性为null  返回false表示不是所有属性都是null
     *
     * @param
     * @return
     * @author Huyt
     * @date 2019/4/17
     */
    public static String isAllFieldEmpty(Object obj, HashSet<String> ignore) {
        // 得到类对象
        try {
            Class stuCla = (Class) obj.getClass();
            //得到属性集合
            Field[] fs = stuCla.getDeclaredFields();
            boolean flag = true;
            //遍历属性
            for (Field f : fs) {
                if (ignore.contains(f.getName())) {
                    continue;
                } else {
                    f.setAccessible(true);
                    if (f.getType() != String.class) {
                        // 得到此属性的值
                        Object val = f.get(obj);
                        ;
                        if (val == null) {
                            return f.getName() + "取值为空";
                        }
                    } else {
                        // 得到此属性的值
                        String val = (String) f.get(obj);
                        //只要有1个属性不为空,那么就不是所有的属性值都为空
                        if (val == null || val.equals("")) {
                            return f.getName() + "取值为空";
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
