package zcla71.biblioteca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.biblioteca.dao.BibliotecaDao;
import zcla71.biblioteca.dao.SeaTableDao;
import zcla71.biblioteca.model.app.Secret;
import zcla71.biblioteca.model.LibibLivro;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibLivro> importaLibib() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    @GetMapping(value="/importa/seatable")
    public void importaSeatable() throws StreamReadException, DatabindException, IOException {
        Secret secret = BibliotecaDao.getInstance().getSecret();
        Collection<LibibLivro> libib = importaLibib();
        SeaTableDao dao = new SeaTableDao(secret.getApiToken());
        for (LibibLivro livro : libib) {
            
        }
    }
}
