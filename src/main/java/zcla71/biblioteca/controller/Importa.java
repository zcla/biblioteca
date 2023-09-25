package zcla71.biblioteca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.biblioteca.dao.BibliotecaDao;
import zcla71.biblioteca.model.Autor;
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.libib.Importacao;
import zcla71.biblioteca.model.libib.LibibLivro;
import zcla71.dao.seatable.SeaTableDao;
import zcla71.dao.seatable.SeaTableDaoException;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.param.AddRowParam;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/libib/livro")
    public Collection<LibibLivro> libibLivro() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException, SeaTableDaoException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    @PostMapping(value="/libib/importacao-pre")
    private Importacao importa() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException, SeaTableDaoException {
        Collection<LibibLivro> libibLivros = libibLivro();

        Importacao result = new Importacao();
        result.setAutores(new ArrayList<Autor>());
        result.setLivros(new ArrayList<Livro>());

        for (LibibLivro libibLivro : libibLivros) {
            Livro livro = new Livro();

            // id
            livro.setId(SeaTableDao.getNewId());

            // nome
            String nome = libibLivro.getTitle();
            String regexIni = "^(.*)(, )((O|A)s?|D(o|a|e)s?|El|L(o|a)s?)";
            String regex = regexIni + "$";
            if (nome.matches(regex)) {
                nome = nome.replaceFirst(regex, "$3 $1");
            }
            regex = regexIni + "( ?(\\/|-|:) .*)$";
            if (nome.matches(regex)) {
                nome = nome.replaceFirst(regex, "$3 $1$7");
            }
            livro.setNome(nome);

            // autores
            if (libibLivro.getCreators() != null) {
                livro.setIdsAutores(new ArrayList<String>());
                String[] splAutores = libibLivro.getCreators().split(",");
                for (String strAutor : splAutores) {
                    String nomeAutor = strAutor.trim();
                    Autor autor = null;
                    try {
                        autor = result.getAutores().stream().filter(a -> a.getNome().equals(nomeAutor)).findFirst().get();
                    } catch (NoSuchElementException e) {
                        autor = new Autor();
                        autor.setId(SeaTableDao.getNewId());
                        autor.setNome(nomeAutor);
                        result.getAutores().add(autor);
                    }
                    livro.getIdsAutores().add(autor.getId());
                }
            }

            // isbn13
            livro.setIsbn13(libibLivro.getEan_isbn13());

            // fim
            result.getLivros().add(livro);
        }

        return result;
    }

    @PostMapping(value="/libib/importacao-old")
    public Importacao libibImportacao_old() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException, SeaTableDaoException {
        Importacao result = importa();

        BibliotecaDao dao = BibliotecaDao.getInstance();

        dao.startTransaction();
        try {
            dao.commitTransaction();

            return result;
        } catch (Exception e) {
            dao.discardTransaction();
            throw e;
        }
    }

    @PostMapping(value="/libib/importacao")
    public Importacao libibImportacao() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException, SeaTableDaoException {
        Collection<LibibLivro> libibLivros = libibLivro();

        Importacao result = new Importacao();
        result.setAutores(new ArrayList<Autor>());
        result.setLivros(new ArrayList<Livro>());

        BibliotecaDao dao = BibliotecaDao.getInstance();

        dao.startTransaction();
        try {
            for (LibibLivro libibLivro : libibLivros) {
                Livro livro = new Livro();

                // id
                livro.setId(SeaTableDao.getNewId());

                // nome
                String nome = libibLivro.getTitle();
                String regexIni = "^(.*)(, )((O|A)s?|D(o|a|e)s?|El|L(o|a)s?)";
                String regex = regexIni + "$";
                if (nome.matches(regex)) {
                    nome = nome.replaceFirst(regex, "$3 $1");
                }
                regex = regexIni + "( ?(\\/|-|:) .*)$";
                if (nome.matches(regex)) {
                    nome = nome.replaceFirst(regex, "$3 $1$7");
                }
                livro.setNome(nome);

                // autores
                if (libibLivro.getCreators() != null) {
                    livro.setIdsAutores(new ArrayList<String>());
                    String[] splAutores = libibLivro.getCreators().split(",");
                    for (String strAutor : splAutores) {
                        String nomeAutor = strAutor.trim();
                        Autor autor = null;
                        try {
                            autor = result.getAutores().stream().filter(a -> a.getNome().equals(nomeAutor)).findFirst().get();
                        } catch (NoSuchElementException e) {
                            autor = new Autor();
                            autor.setId(SeaTableDao.getNewId());
                            autor.setNome(nomeAutor);
                            result.getAutores().add(autor);

                            dao.addRow(new AddRowParam(
                                new Row(new Object[][] {
                                    { "id", autor.getId() },
                                    { "nome", autor.getNome() }
                                }),
                                "autor"
                            ));
                        }
                        livro.getIdsAutores().add(autor.getId());
                    }
                }

                // isbn13
                livro.setIsbn13(libibLivro.getEan_isbn13());

                result.getLivros().add(livro);

                Row row = new Row(new Object[][] {
                    { "id", livro.getId() },
                    { "nome", livro.getNome() },
                    { "isbn13", livro.getIsbn13()}
                });
                row.put("autores", livro.getIdsAutores());
                dao.addRow(new AddRowParam(
                    row,
                    "livro"
                ));
            }

            dao.commitTransaction();

            return result;
        } catch (Exception e) {
            dao.discardTransaction();
            throw e;
        }
    }
}
