package com.xiyuan.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by xiyuan_fengyu on 2016/7/26.
 */
public class PreferenceUtil {

    private static SharedPreferences prefer;

    public static void initialize(Application app) {
        prefer = app.getSharedPreferences(app.getPackageName(), Context.MODE_APPEND);
    }

    public static <T> void put(String key, T value) {
        if (value != null) {
            SharedPreferences.Editor editor = prefer.edit();
            Class<?> clazz = value.getClass();
            if (clazz == int.class || clazz == Integer.class || clazz == short.class || clazz == Short.class) {
                editor.putInt(key, (Integer) value);
            }
            else if (clazz == String.class) {
                editor.putString(key, (String) value);
            }
            else if (clazz == long.class || clazz == Long.class) {
                editor.putLong(key, (Long) value);
            }
            else if (clazz == float.class || clazz == Float.class || clazz == double.class || clazz == Double.class) {
                editor.putFloat(key, (Float) value);
            }
            else if (clazz == boolean.class || clazz == Boolean.class) {
                editor.putBoolean(key, (Boolean) value);
            }
            else if (clazz == Set.class) {
                Set<?> temp = (Set<?>) value;
                Set<String> strs = new HashSet<>();
                if (!temp.isEmpty()) {
                    for (Object obj: temp) {
                        if (obj.getClass() == String.class) {
                            strs.add((String)obj);
                        }
                        else {
                            strs.add(obj.toString());
                        }
                    }
                }
                editor.putStringSet(key, strs);
            }
            else {
                editor.putString(key, JsonUtil.gson.toJson(value));
            }
            editor.apply();
        }
    }

    public static int getInt(String key, int defaultValue) {
        return prefer.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        return prefer.getLong(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        return prefer.getFloat(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return prefer.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return prefer.getBoolean(key, defaultValue);
    }

    public static Set<String> getString(String key, Set<String> defaultValue) {
        return prefer.getStringSet(key, defaultValue);
    }

    public static JsonObject getJsonObject(String key) {
        String jsonStr = prefer.getString(key, null);
        try {
            return JsonUtil.parser.parse(jsonStr).getAsJsonObject();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static JsonArray getJsonArr(String key) {
        String jsonStr = prefer.getString(key, null);
        try {
            return JsonUtil.parser.parse(jsonStr).getAsJsonArray();
        }
        catch (Exception e) {
            return null;
        }
    }

    public static <T> T getObj(String key, Class<T> clazz) {
        try {
            JsonObject jsonObject = getJsonObject(key);
            return JsonUtil.jsonToObj(jsonObject, clazz);
        }
        catch (Exception e) {
            return null;
        }

    }

}
