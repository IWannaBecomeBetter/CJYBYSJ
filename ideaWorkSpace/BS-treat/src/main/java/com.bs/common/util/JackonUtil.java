package com.bs.common.util;

import com.bs.common.exception.ApplicationException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.util.ArrayList;


/**
 * JSON工具类
 * Created by fusj on 15/12/21.
 */
public class JackonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 把java对象转换成JSON
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static String writeEntity2JSON(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new ApplicationException("字符串解析错误", ex);
        }
    }

    /**
     * 将JSON转换成java对象
     *
     * @param json
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T readJson2Entity(String json, Class<T> clazz) {
        try {
            T result = clazz.newInstance();

            if (StringUtil.isNotEmptyObject(json)) {
                result = objectMapper.readValue(json, clazz);
            }

            return result;
        } catch (Exception ex) {
            throw new ApplicationException("字符串解析错误", ex);
        }
    }

    /**
     * 将JSON转换成java对象--对象List
     * @param json
     * @return
     * @throws Exception
     */
    public static <T> T readJson2Entity1(String json, Class object) {
        try {

            JavaType javaType = getCollectionType(ArrayList.class, object);
            return objectMapper.readValue(json, javaType);
        } catch (Exception ex) {
            throw new ApplicationException("字符串解析错误", ex);
        }
    }


    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
