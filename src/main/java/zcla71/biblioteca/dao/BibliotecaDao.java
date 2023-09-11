package zcla71.biblioteca.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import zcla71.biblioteca.model.LibibLivro;
import zcla71.biblioteca.model.app.Config;
import zcla71.biblioteca.model.app.Secret;

public class BibliotecaDao {
    private static String CONFIG_RESOURCE_LOCATION = "classpath:config.json";
    private static String SECRET_RESOURCE_LOCATION = "classpath:secret.json";
    private static BibliotecaDao instance = null;

    private Object getResource(Class<?> classe, String resourceLocation) throws StreamReadException, DatabindException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = ResourceUtils.getFile(resourceLocation);
        return objectMapper.readValue(file, classe);
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
