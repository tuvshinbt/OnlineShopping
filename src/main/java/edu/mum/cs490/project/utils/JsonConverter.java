/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum.cs490.project.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author tuvshuu
 */
public class JsonConverter {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T jsonToObject(String data, Class<T> clazz) throws IOException, NullPointerException {
        T t = mapper.readValue(data, clazz);
        return t;
    }

    public static <T> String objectToJson(T t) throws JsonProcessingException {
       return mapper.writeValueAsString(t);
    }
}
