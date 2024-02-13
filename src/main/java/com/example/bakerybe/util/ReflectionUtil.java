package com.example.bakerybe.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    /**
     * Sets the field value on a given object using reflection.
     *
     * @param obj the object to modify.
     * @param fieldName the name of the field.
     * @param value the value to set.
     */

    public static void setFieldValue(Object obj, String fieldName, Object value) {;
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        if (field != null) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Object convertedValue = convertValueToRequiredType(value, fieldType);
            if (convertedValue != null) {
                ReflectionUtils.setField(field, obj, convertedValue);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Object convertValueToRequiredType(Object value, Class<?> requiredType) {

        if (requiredType.isInstance(value)){
            if (requiredType.isInstance(value)) {
                return value;
            } else if (requiredType.equals(Long.class) && value instanceof Integer) {
                return ((Integer) value).longValue();
            } else if (requiredType.equals(Integer.class) && value instanceof Long) {
                return ((Long) value).intValue();
            } else if (requiredType.equals(Double.class) && value instanceof Number) {
                return ((Number) value).doubleValue();
            }
        } else if (requiredType.isEnum() && value instanceof String) {
            try {
                Class<? extends Enum> enumType = (Class<? extends Enum>) requiredType;
                return Enum.valueOf(enumType, (String) value);
            }catch (IllegalArgumentException e){
                return null;
            }
        }
        return null;
    }
}
