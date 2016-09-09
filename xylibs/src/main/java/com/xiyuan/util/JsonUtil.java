package com.xiyuan.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiyuan_fengyu on 2016/7/25.
 */
public class JsonUtil {

    public static final Gson gson = new Gson();

    public static final JsonParser parser = new JsonParser();

    public static JsonElement objToJson(Object obj) {
        try {
            return gson.toJsonTree(obj);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static JsonArray objsToJsonArray(List<Object> objs) {
        JsonArray jsonArray = new JsonArray();
        if (objs != null) {
            for (Object obj: objs) {
                JsonElement temp = objToJson(obj);
                if (temp != null) {
                    jsonArray.add(temp);
                }
            }
        }
        return jsonArray;
    }

    public static <T> T jsonToObj(JsonElement json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static <T> ArrayList<T> jsonArrToObjs(JsonArray jsonArr, Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();

        if (jsonArr != null && clazz != null) {
            for (int i = 0, len = jsonArr.size(); i < len; i++) {
                T temp = jsonToObj(jsonArr.get(i), clazz);
                if (temp != null) {
                    list.add(temp);
                }
            }
        }

        return list;
    }

}
