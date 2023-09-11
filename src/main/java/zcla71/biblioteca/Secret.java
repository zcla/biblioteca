package zcla71.biblioteca;

import java.io.File;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Secret {
    private static Secret instance = null;

    public static Secret getInstance() throws StreamReadException, DatabindException, IOException {
        if (instance == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = ResourceUtils.getFile("classpath:secret.json");
            instance = objectMapper.readValue(file, Secret.class);
        }
        return instance;
    }

    private String apiToken;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
