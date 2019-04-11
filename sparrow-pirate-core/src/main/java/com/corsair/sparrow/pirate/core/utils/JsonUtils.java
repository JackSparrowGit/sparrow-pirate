package com.corsair.sparrow.pirate.core.utils;

import com.corsair.sparrow.pirate.core.global.RespEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author jack
 */
@Slf4j
public abstract class JsonUtils {

    private static final ObjectMapper JACKSON = new ObjectMapper();

    /**
     * 通过Jackson转化为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        return toJson(JACKSON,object);
    }

    private static String toJson(ObjectMapper objectMapper,Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}",e);
        }
        return null;
    }

    public static String toFilterJson(Object object, String filterFeilds) {
        return toJson(Squiggly.init(
                new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")),filterFeilds)
                ,object);
    }

    public static <T> T jsonToType(String inputJson, Class<T> targetType) {
        return jsonToType(JACKSON,inputJson,targetType);
    }

    private static <T> T jsonToType(ObjectMapper objectMapper, String inputJson, Class<T> targetType) {
        try {
            return objectMapper.readValue(inputJson,targetType);
        } catch (IOException e) {
            log.error("IOException: {}",e);
        }
        return null;
    }
}
