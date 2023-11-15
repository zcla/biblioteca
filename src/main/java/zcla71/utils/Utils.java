package zcla71.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static String encodeURIComponent(String s) {
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getResourceAsObject(Class<T> classe, String resourceLocation) throws StreamReadException, DatabindException, IOException {
        Resource resource = new ClassPathResource(resourceLocation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        ObjectMapper objectMapper = new ObjectMapper();
        return classe.cast(objectMapper.readValue(reader, classe));
    }

    public static <T> T getStringAsObject(Class<T> classe, String json) throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return classe.cast(objectMapper.readValue(json, classe));
    }
}
