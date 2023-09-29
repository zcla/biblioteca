package zcla71.biblioteca.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;

import zcla71.biblioteca.model.config.BibliotecaConfig;
import zcla71.biblioteca.model.libib.LibibLivro;
import zcla71.biblioteca.model.secret.Secret;
import zcla71.dao.seatable.SeaTableDao;
import zcla71.dao.seatable.SeaTableDaoException;
import zcla71.utils.Utils;

public class BibliotecaDao extends SeaTableDao {
    private static String CONFIG_RESOURCE_LOCATION = "/config.json";
    private static String SECRET_RESOURCE_LOCATION = "/secret.json";
    private static BibliotecaDao instance = null;

    public static BibliotecaDao getInstance() throws StreamReadException, DatabindException, IOException, SeaTableDaoException {
        if (instance == null) {
            BibliotecaConfig config = Utils.getResourceAsObject(BibliotecaConfig.class, CONFIG_RESOURCE_LOCATION);
            Secret secret = Utils.getResourceAsObject(Secret.class, SECRET_RESOURCE_LOCATION);
            instance = new BibliotecaDao(config, secret);
        }
        return instance;
    }

    private BibliotecaConfig config;

    private BibliotecaDao(BibliotecaConfig config, Secret secret) throws IOException, SeaTableDaoException {
        super(config.getSeaTable(), secret.getSeaTable().getApiToken());
        this.config = config;
    }

    // ----- Libib

    private Object getCsv(Class<?> classe, String resourceLocation) throws IllegalStateException, FileNotFoundException {
        return new CsvToBeanBuilder<>(new FileReader(ResourceUtils.getFile(resourceLocation)))
            .withType(classe)
            .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
            .build()
            .parse();
    }

    @SuppressWarnings("unchecked")
    public Collection<LibibLivro> getLibibLivros() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return (Collection<LibibLivro>) getCsv(LibibLivro.class, config.getLibib().getCsvFileLocation());
    }
}
