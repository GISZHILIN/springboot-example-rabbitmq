package com.example.rabbitmq.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;

/**
 * @ProjectName:
 * @Package: com.yijiupi.pfm.utils
 * @ClassName: JsonUtils
 * @Description: java类作用描述
 * @Author: Niki Zheng
 * @CreateDate: 2019/3/5 11:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class JsonUtils {
    private final static Gson gson = new Gson();


    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }

    public static boolean validate(String jsonStr) {
        JsonElement jsonElement;
        try {
            jsonElement = JsonParser.parseString(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }
}