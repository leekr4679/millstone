package com.base.app.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private final ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true);
        this.mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true);
        this.mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        this.mapper.registerModule(new JavaTimeModule());
    }

    public static JsonUtils getInstance() {
        return new JsonUtils();
    }

    private static ObjectMapper getMapper() {
        return getInstance().mapper;
    }

    public static String toJson(Object object) {
        try {
            return getMapper().writeValueAsString(object);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T fromJson(String jsonStr, Class<T> cls) {
        try {
            return getMapper().readValue(jsonStr, cls);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            ObjectMapper objectMapper = getMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(object);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String toPrettyJson(String json) {
        Object jsonObject = fromJson(json, Object.class);

        try {
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
            return "";
        }
    }

    public static String toListJson(Object object) {
        try {
            Map<String, Object> map = Maps.newHashMap();
            map.put("list", object);
            return getMapper().writeValueAsString(map);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T fromJsonToMap(String jsonStr) {
        try {
            return (T) getMapper().readValue(jsonStr, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T fromJson(String jsonStr, TypeReference<T> typeReference) {
        try {
            return getMapper().readValue(jsonStr, typeReference);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static JsonNode fromJson(String json) {
        try {
            return getMapper().readTree(json);
        } catch (IOException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static JsonNode fromJson(Object object) {
        try {
            return (JsonNode)getMapper().convertValue(object, JsonNode.class);
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    public static <T extends Collection> T fromJson(String jsonStr, CollectionType collectionType) {
        try {
            return (T) getMapper().readValue(jsonStr, collectionType);
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    public static List<Map<String, Object>> fromJsonToList(String jsonStr) {
        try {
            return (List)fromJson(jsonStr, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
}
