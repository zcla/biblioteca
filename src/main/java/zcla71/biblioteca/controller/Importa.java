package zcla71.biblioteca.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.biblioteca.Config;
import zcla71.biblioteca.Secret;
import zcla71.biblioteca.dao.CsvDao;
import zcla71.biblioteca.dao.SeaTableDao;
import zcla71.biblioteca.model.LibibLivro;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibLivro> importaLibib() throws StreamReadException, DatabindException, IOException {
        Config config = Config.getInstance();
        File file = ResourceUtils.getFile(config.getLibibCsvFileLocation());
        CsvDao<LibibLivro> dao = new CsvDao<LibibLivro>(LibibLivro.class);
        Collection<LibibLivro> result = dao.read(file);
        return result;
    }

    @GetMapping(value="/importa/seatable")
    public void importaSeatable() throws StreamReadException, DatabindException, IOException {
        Secret secret = Secret.getInstance();
        Collection<LibibLivro> libib = importaLibib();
        SeaTableDao dao = new SeaTableDao(secret.getApiToken());
        for (LibibLivro livro : libib) {
            
        }
    }
}
