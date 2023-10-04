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
import zcla71.biblioteca.model.Editora;
import zcla71.biblioteca.model.Grupo;
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.Tag;
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

    @PostMapping(value="/libib/importacao")
    public Importacao libibImportacao() throws Exception {
        Collection<LibibLivro> libibLivros = libibLivro();

        // TODO Bolar um jeito de identificar dados existentes com os do libib para fazer importação sem precisar excluir tudo.

        Importacao result = new Importacao();
        result.setAutores(new ArrayList<Autor>());
        result.setEditoras(new ArrayList<Editora>());
        result.setGrupos(new ArrayList<Grupo>());
        result.setTags(new ArrayList<Tag>());
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

                // isbn10
                livro.setIsbn10(libibLivro.getUpc_isbn10());

                // description
                livro.setDescricao(libibLivro.getDescription());

                // editoras
                if (libibLivro.getPublisher() != null) {
                    livro.setIdsEditoras(new ArrayList<String>());
                    String[] splEditoras = libibLivro.getPublisher().split(",");
                    for (String strEditora : splEditoras) {
                        String nomeEditora = strEditora.trim();
                        Editora editora = null;
                        try {
                            editora = result.getEditoras().stream().filter(a -> a.getNome().equals(nomeEditora)).findFirst().get();
                        } catch (NoSuchElementException e) {
                            editora = new Editora();
                            editora.setId(SeaTableDao.getNewId());
                            editora.setNome(nomeEditora);
                            result.getEditoras().add(editora);

                            dao.addRow(new AddRowParam(
                                new Row(new Object[][] {
                                    { "id", editora.getId() },
                                    { "nome", editora.getNome() }
                                }),
                                "editora"
                            ));
                        }
                        livro.getIdsEditoras().add(editora.getId());
                    }
                }

                // dataPublicacao
                livro.setDataPublicacao(libibLivro.publish_dateAsDate());

                // grupo
                if (libibLivro.getGroup() != null) {
                    String nomeGrupo = libibLivro.getGroup().trim();
                    Grupo grupo = null;
                    try {
                        grupo = result.getGrupos().stream().filter(a -> a.getNome().equals(nomeGrupo)).findFirst().get();
                    } catch (NoSuchElementException e) {
                        grupo = new Grupo();
                        grupo.setId(SeaTableDao.getNewId());
                        grupo.setNome(nomeGrupo);
                        result.getGrupos().add(grupo);

                        dao.addRow(new AddRowParam(
                            new Row(new Object[][] {
                                { "id", grupo.getId() },
                                { "nome", grupo.getNome() }
                            }),
                            "grupo"
                        ));
                    }
                    livro.setIdGrupo(grupo.getId());
                }

                // editoras
                if (libibLivro.getPublisher() != null) {
                    livro.setIdsTags(new ArrayList<String>());
                    if (libibLivro.getTags() != null) {
                        String[] splTags = libibLivro.getTags().split(",");
                        for (String strTag : splTags) {
                            String nomeTag = strTag.trim();
                            Tag tag = null;
                            try {
                                tag = result.getTags().stream().filter(a -> a.getNome().equals(nomeTag)).findFirst().get();
                            } catch (NoSuchElementException e) {
                                tag = new Tag();
                                tag.setId(SeaTableDao.getNewId());
                                tag.setNome(nomeTag);
                                result.getTags().add(tag);
    
                                dao.addRow(new AddRowParam(
                                    new Row(new Object[][] {
                                        { "id", tag.getId() },
                                        { "nome", tag.getNome() }
                                    }),
                                    "tag"
                                ));
                            }
                            livro.getIdsTags().add(tag.getId());
                        }
                    }
                }

                // TODO Tratar description quando contiver json.

                result.getLivros().add(livro);

                Row row = new Row(new Object[][] {
                    { "id", livro.getId() },
                    { "nome", livro.getNome() },
                    { "autores", livro.getIdsAutores() },
                    { "isbn13", livro.getIsbn13() },
                    { "isbn10", livro.getIsbn10() },
                    { "descricao", livro.getDescricao() },
                    { "editoras", livro.getIdsEditoras() },
                    { "dataPublicacao", livro.getDataPublicacao() },
                    { "grupo", livro.getIdGrupo() },
                    { "tags", livro.getIdsTags() }
                });
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
