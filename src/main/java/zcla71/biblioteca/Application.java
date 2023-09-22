package zcla71.biblioteca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    public static Object getResource(Class<?> classe, String resourceLocation) throws StreamReadException, DatabindException, IOException {
        Resource resource = new ClassPathResource(resourceLocation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reader, classe);
    }
}
