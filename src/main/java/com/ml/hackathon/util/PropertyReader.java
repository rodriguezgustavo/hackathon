package com.ml.hackathon.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by gurodriguez
 */
public class PropertyReader {

    public static Object getProperty(String key){
        String resourceName = "db.properties"; // could also be a constant
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try {
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            props.load(resourceStream);
            return props.get(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Method getGetter(Class<?> classObj,String property) throws NoSuchMethodException {
        char[] charArray=property.toCharArray();
        charArray[0]=Character.toUpperCase(charArray[0]);
        return classObj.getMethod("get"+String.valueOf(charArray));
    }
}
