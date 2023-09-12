package zcla71.biblioteca.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import zcla71.biblioteca.model.app.Config;
import zcla71.biblioteca.model.app.Secret;
import zcla71.biblioteca.model.libib.LibibLivro;

public class BibliotecaDao {
    private static String CONFIG_RESOURCE_LOCATION = "config.json";
    private static String SECRET_RESOURCE_LOCATION = "secret.json";
    private static BibliotecaDao instance = null;

    private Object getResource(Class<?> classe, String resourceLocation) throws StreamReadException, DatabindException, IOException {
        Resource resource = new ClassPathResource(resourceLocation);
        BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reader, classe);
    }

    private Object getCsv(Class<?> classe, String resourceLocation) throws IllegalStateException, FileNotFoundException {
        return new CsvToBeanBuilder<>(new FileReader(ResourceUtils.getFile(resourceLocation)))
            .withType(classe)
            .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
            .build()
            .parse();
    }

    public static BibliotecaDao getInstance() {
        if (instance == null) {
            instance = new BibliotecaDao();
        }
        return instance;
    }

    // ----- App

    public Config getConfig() throws StreamReadException, DatabindException, IOException {
        return (Config) getResource(Config.class, CONFIG_RESOURCE_LOCATION);
    }

    public Secret getSecret() throws StreamReadException, DatabindException, IOException {
        return (Secret) getResource(Secret.class, SECRET_RESOURCE_LOCATION);
    }

    // ----- Libib

    @SuppressWarnings("unchecked")
    public Collection<LibibLivro> getLibibLivros() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return (Collection<LibibLivro>) getCsv(LibibLivro.class, getConfig().getLibibCsvFileLocation());
    }
}
