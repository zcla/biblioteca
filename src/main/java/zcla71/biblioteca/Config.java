package zcla71.biblioteca;

import java.io.File;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    private static Config instance = null;

    public static Config getInstance() throws StreamReadException, DatabindException, IOException {
        if (instance == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = ResourceUtils.getFile("classpath:config.json");
            instance = objectMapper.readValue(file, Config.class);
        }
        return instance;
    }

    private String libibCsvFileLocation;

    public String getLibibCsvFileLocation() {
        return libibCsvFileLocation;
    }

    public void setLibibCsvFileLocation(String libibCsvFileLocation) {
        this.libibCsvFileLocation = libibCsvFileLocation;
    }
}
