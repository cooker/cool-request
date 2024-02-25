package com.cool.request.utils;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.intellij.util.Base64;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class GsonUtils {
    private static final Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(byte[].class,
            new ByteArrayToBase64TypeAdapter()).create();

    public static <T> T readValue(String value, Class<T> tClass) {
        try {
            return gson.fromJson(value, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Object> toMap(String json) {
        if (StringUtils.isEmpty(json)) return new HashMap<>();
        try {
            java.lang.reflect.Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            return gson.fromJson(json, type);
        } catch (Exception e) {
        }
        return new HashMap<>();
    }

    public static String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    public static String format(String source) {
        try {
            if (StringUtils.isEmpty(source)) return source;
            JsonElement jsonElement = new JsonParser().parse(source);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.toJson(jsonElement);
        } catch (Exception e) {
            return source;
        }
    }

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode(json.getAsString());
        }

        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(Base64.encode(src));
        }
    }
}
