package com.xiyuan.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/7/29.
 */
public class BeanUtil {

    private static String timeFormatStr = "yyyy-MM-dd HH:mm:ss";

    private static SimpleDateFormat timeFormat = new SimpleDateFormat(timeFormatStr);

    public static void setTimeFormatStr(String timeFormatStr) {
        BeanUtil.timeFormatStr = timeFormatStr;
        timeFormat = new SimpleDateFormat(timeFormatStr);
    }

    public static <From, To> void translate(From from, To to, String ...ignores) {
        if (from != null && to != null) {
            Class<?> fromClazz =  from.getClass();
            Class<?> toClazz =  to.getClass();
            Field[] fromClazzFields = fromClazz.getDeclaredFields();
            List<String> ignoreList = null;
            if (ignores != null) {
                ignoreList = Arrays.asList(ignores);
            }
            else {
                ignoreList = new ArrayList<>();
            }

            for (Field fromField: fromClazzFields) {

                try {
                    fromField.setAccessible(true);
                    String fromFieldName = fromField.getName();
                    if (!ignoreList.contains(fromFieldName)) {
                        Field toField = toClazz.getField(fromFieldName);
                        if (toField != null) {
                            toField.setAccessible(true);
                            copyFieldValue(from, fromField, to, toField);
                        }

                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    private static <From, To> void copyFieldValue(From from, Field fromField, To to, Field toField) {
        if (from != null && fromField != null
                && to != null && toField != null) {
            Class<?> fromFieldType = fromField.getType();
            Class<?> toFieldType = toField.getType();

            try {
                if (fromFieldType == toFieldType) {
                    toField.set(to, fromField.get(from));
                }
                else if (isNumber(fromFieldType) && isNumber(toFieldType)) {
                    if (getNumerLevel(fromFieldType) <= getNumerLevel(toFieldType)) {
                        toField.set(to, fromField.get(from));
                    }
                    else {
                        Method method = getNumberObjectType(fromFieldType).getMethod(getNumerType(toFieldType) + "Value");
                        toField.set(to, method.invoke(fromField.get(from)));
                    }
                }
                else if (isString(toFieldType)) {
                    if (isDate(fromFieldType)) {
                        toField.set(to, timeFormat.format((Date)fromField.get(from)));
                    }
                    else {
                        toField.set(to, fromField.get(from) + "");
                    }
                }
                else if (isString(fromFieldType)) {
                    String fromVal = (String)fromField.get(from);
                    if (isDate(toFieldType)) {
                        toField.set(to, timeFormat.parse(fromVal));
                    }
                    else if (isNumber(toFieldType)) {
                        Method method = Double.class.getMethod(getNumerType(toFieldType) + "Value");
                        toField.set(to, method.invoke(Double.parseDouble(fromVal)));
                    }
                    else if (isBoolean(toFieldType)) {
                        toField.set(to, Boolean.parseBoolean(fromVal));
                    }
                    else if (isByte(toFieldType)) {
                        toField.set(to, Byte.parseByte(fromVal));
                    }
                    else if (isChar(toFieldType)) {
                        if (fromVal.isEmpty()) {
                            toField.set(to, '\0');
                        }
                        else {
                            toField.set(to, fromVal.charAt(0));
                        }
                    }
                }
                else if (isDate(fromFieldType) && isNumber(toFieldType)) {
                    Method method = Long.class.getMethod(getNumerType(toFieldType) + "Value");
                    toField.set(to, method.invoke(((Date) fromField.get(from)).getTime()));
                }
                else if (isDate(toFieldType) && isNumber(fromFieldType)) {
                    Double d = Double.parseDouble("" + fromField.get(from));
                    toField.set(to, new Date(d.longValue()));
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isNumber(Class<?> clazz) {
        return clazz == int.class || clazz == Integer.class
                || clazz == long.class || clazz == Long.class
                || clazz == float.class || clazz == Float.class
                || clazz == double.class || clazz == Double.class
                || clazz == short.class || clazz == Short.class;
    }

    private static Class<?> getNumberObjectType(Class<?> clazz) {
        if (clazz == short.class || clazz == Short.class) {
            return Short.class;
        }
        else if (clazz == int.class || clazz == Integer.class) {
            return Integer.class;
        }
        else if (clazz == long.class || clazz == Long.class) {
            return Long.class;
        }
        else if (clazz == float.class || clazz == Float.class) {
            return Float.class;
        }
        else if (clazz == double.class || clazz == Double.class) {
            return Double.class;
        }
        else {
            return Short.class;
        }
    }

    private static String getNumerType(Class<?> clazz) {
        if (clazz == short.class || clazz == Short.class) {
            return "short";
        }
        else if (clazz == int.class || clazz == Integer.class) {
            return "int";
        }
        else if (clazz == long.class || clazz == Long.class) {
            return "long";
        }
        else if (clazz == float.class || clazz == Float.class) {
            return "float";
        }
        else if (clazz == double.class || clazz == Double.class) {
            return "double";
        }
        else {
            return "short";
        }
    }

    private static int getNumerLevel(Class<?> clazz) {
        if (clazz == short.class || clazz == Short.class) {
            return 0;
        }
        else if (clazz == int.class || clazz == Integer.class) {
            return 1;
        }
        else if (clazz == long.class || clazz == Long.class) {
            return 2;
        }
        else if (clazz == float.class || clazz == Float.class) {
            return 3;
        }
        else if (clazz == double.class || clazz == Double.class) {
            return 4;
        }
        else {
            return Integer.MAX_VALUE;
        }
    }

    private static boolean isBoolean(Class<?> clazz) {
        return clazz == boolean.class || clazz == Boolean.class;
    }

    private static boolean isString(Class<?> clazz) {
        return clazz == String.class;
    }

    private static boolean isByte(Class<?> clazz) {
        return clazz == byte.class || clazz == Byte.class;
    }

    private static boolean isChar(Class<?> clazz) {
        return clazz == char.class || clazz == Character.class;
    }

    private static boolean isDate(Class<?> clazz) {
        return clazz == Date.class;
    }

}
