package zcla71.biblioteca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.biblioteca.dao.BibliotecaDao;
import zcla71.biblioteca.dao.SeaTableDao;
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.app.Secret;
import zcla71.biblioteca.model.libib.LibibLivro;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibLivro> importaLibib() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    private Livro libibLivro2Livro(LibibLivro libib) {
        Livro result = new Livro();

        String nome = libib.getTitle();
        String regexIni = "^(.*)(, )((O|A)s?|D(o|a|e)s?|El|L(o|a)s?)";
        String regex = regexIni + "$";
        if (nome.matches(regex)) {
            nome = nome.replaceFirst(regex, "$3 $1");
        }
        regex = regexIni + "( ?(\\/|-|:) .*)$";
        if (nome.matches(regex)) {
            nome = nome.replaceFirst(regex, "$3 $1$7");
        }
        result.setNome(nome);

        return result;
    }

    @GetMapping(value="/importa/seatable")
    public Collection<Livro> importaSeatable() throws StreamReadException, DatabindException, IOException {
        Secret secret = BibliotecaDao.getInstance().getSecret();
        Collection<LibibLivro> libibLivros = importaLibib();
        Collection<Livro> result = new ArrayList<Livro>();
        for (LibibLivro libibLivro : libibLivros) {
            Livro livro = libibLivro2Livro(libibLivro);
            result.add(livro);
        }

        SeaTableDao dao = new SeaTableDao(secret.getApiToken());

        return result;
    }
}
