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
            // if (forceOneByOne) {
            //     for (Autor autor : result.getAutores()) {
            //         AddRowParam param = new AddRowParam();
            //         param.setTable_name("autor");
            //         param.setRow(new Row());
            //         param.getRow().put("id", autor.getId().toString());
            //         param.getRow().put("nome", autor.getNome());
            //         @SuppressWarnings("unused")
            //         AddRowResult irResult = api.addRow(param);
            //         System.out.println(irResult);
            //     }
            // } else {
            //     AppendRowsParam param = new AppendRowsParam();
            //     param.setTable_name("autor");
            //     param.setRows(new ArrayList<>());
            //     for (Autor autor : result.getAutores()) {
            //         Row row = new Row();
            //         row.put("id", autor.getId().toString());
            //         row.put("nome", autor.getNome());
            //         param.getRows().add(row);
            //     }
            //     @SuppressWarnings("unused")
            //     AppendRowsResult arResult = api.appendRows(param);
            // }
    
            // ListRowsParam lrParam = new ListRowsParam("autor");
            // ListRowsResult lrResult = api.listRows(lrParam);
    
            // forceOneByOne = true;
            // if (forceOneByOne) { // Versão um por um (lenta)
            //     String link_id = metadata.getMetadata()
            //             .getTables().stream().filter(t -> t.getName().equals("livro")).findFirst().get()
            //             .getColumns().stream().filter(c -> c.getName().equals("autores")).findFirst().get()
            //             .getData().getLink_id();
            //     for (Livro livro : result.getLivros()) {
            //         AddRowParam param = new AddRowParam();
            //         param.setTable_name("livro");
            //         param.setRow(new Row());
            //         param.getRow().put("id", livro.getId().toString());
            //         param.getRow().put("nome", livro.getNome());
            //         Collection<String> autores = new ArrayList<String>();
            //         if (livro.getAutores() != null) {
            //             for (Autor autor : livro.getAutores()) {
            //                 Row row = lrResult.getRows().stream().filter(a -> a.get("nome").equals(autor.getNome())).findFirst().get();
            //                 if (row != null) {
            //                     autores.add((String) row.get("_id"));
            //                 }
            //             }
            //             param.getRow().put("autores", autores);
            //         }
            //         AddRowResult irResult = api.addRow(param);
            //         // Insere os links, que NÃO são inseridos no addRow.
            //         for (String table_row_id : autores) {
            //             CreateRowLinkParam crlParam = new CreateRowLinkParam();
            //             crlParam.setTable_name("livro");
            //             crlParam.setOther_table_name("autor");
            //             crlParam.setLink_id(link_id);
            //             crlParam.setTable_row_id(irResult.get("_id"));
            //             crlParam.setOther_table_row_id(table_row_id);
            //             @SuppressWarnings("unused")
            //             CreateRowLinkResult crlResult = api.createRowLink(crlParam);
            //         }
            //     }
            // } else { // Versão batch
            // //     AppendRowsParam param = new AppendRowsParam();
            // //     param.setTable_name("livro");
            // //     param.setRows(new ArrayList<>());
            // //     for (Livro livro : result.getLivros()) {
            // //         Row row = new Row();
            // //         row.put("id", livro.getId().toString());
            // //         row.put("nome", livro.getNome());
            // //         param.getRows().add(row);
            // //     }
            // //     @SuppressWarnings("unused")
            // //     AppendRowsResult arResult = api.appendRows(param);
            // }
    
            // -----------------------
    
            // // Insere dados
            // boolean forceOneByOne = false;
    
            // if (forceOneByOne) {
            //     for (Autor autor : result.getAutores()) {
            //         AddRowParam param = new AddRowParam();
            //         param.setTable_name("autor");
            //         param.setRow(new Row());
            //         param.getRow().put("id", autor.getId().toString());
            //         param.getRow().put("nome", autor.getNome());
            //         @SuppressWarnings("unused")
            //         AddRowResult irResult = api.addRow(param);
            //         System.out.println(irResult);
            //     }
            // } else {
            //     AppendRowsParam param = new AppendRowsParam();
            //     param.setTable_name("autor");
            //     param.setRows(new ArrayList<>());
            //     for (Autor autor : result.getAutores()) {
            //         Row row = new Row();
            //         row.put("id", autor.getId().toString());
            //         row.put("nome", autor.getNome());
            //         param.getRows().add(row);
            //     }
            //     @SuppressWarnings("unused")
            //     AppendRowsResult arResult = api.appendRows(param);
            // }
    
            // ListRowsParam lrParam = new ListRowsParam("autor");
            // ListRowsResult lrResult = api.listRows(lrParam);
    
            // forceOneByOne = true;
            // if (forceOneByOne) { // Versão um por um (lenta)
            //     String link_id = metadata.getMetadata()
            //             .getTables().stream().filter(t -> t.getName().equals("livro")).findFirst().get()
            //             .getColumns().stream().filter(c -> c.getName().equals("autores")).findFirst().get()
            //             .getData().getLink_id();
            //     for (Livro livro : result.getLivros()) {
            //         AddRowParam param = new AddRowParam();
            //         param.setTable_name("livro");
            //         param.setRow(new Row());
            //         param.getRow().put("id", livro.getId().toString());
            //         param.getRow().put("nome", livro.getNome());
            //         Collection<String> autores = new ArrayList<String>();
            //         if (livro.getAutores() != null) {
            //             for (Autor autor : livro.getAutores()) {
            //                 Row row = lrResult.getRows().stream().filter(a -> a.get("nome").equals(autor.getNome())).findFirst().get();
            //                 if (row != null) {
            //                     autores.add((String) row.get("_id"));
            //                 }
            //             }
            //             param.getRow().put("autores", autores);
            //         }
            //         AddRowResult irResult = api.addRow(param);
            //         // Insere os links, que NÃO são inseridos no addRow.
            //         for (String table_row_id : autores) {
            //             CreateRowLinkParam crlParam = new CreateRowLinkParam();
            //             crlParam.setTable_name("livro");
            //             crlParam.setOther_table_name("autor");
            //             crlParam.setLink_id(link_id);
            //             crlParam.setTable_row_id(irResult.get("_id"));
            //             crlParam.setOther_table_row_id(table_row_id);
            //             @SuppressWarnings("unused")
            //             CreateRowLinkResult crlResult = api.createRowLink(crlParam);
            //         }
            //     }
            // } else { // Versão batch
            // //     AppendRowsParam param = new AppendRowsParam();
            // //     param.setTable_name("livro");
            // //     param.setRows(new ArrayList<>());
            // //     for (Livro livro : result.getLivros()) {
            // //         Row row = new Row();
            // //         row.put("id", livro.getId().toString());
            // //         row.put("nome", livro.getNome());
            // //         param.getRows().add(row);
            // //     }
            // //     @SuppressWarnings("unused")
            // //     AppendRowsResult arResult = api.appendRows(param);
            // }
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
                                "autor",
                                new Row(new Object[][] {
                                    { "id", autor.getId() },
                                    { "nome", autor.getNome() }
                                })
                            ));
                        }
                        livro.getIdsAutores().add(autor.getId());
                    }
                }

                result.getLivros().add(livro);

                if (livro.getIdsAutores() != null) {
                    dao.addRow(new AddRowParam(
                        "livro",
                        new Row(new Object[][] {
                            { "id", livro.getId() },
                            { "nome", livro.getNome() },
                            { "autores", livro.getIdsAutores() }
                        })
                    ));
                }
            }

            dao.commitTransaction();

            return result;
        } catch (Exception e) {
            dao.discardTransaction();
            throw e;
        }
    }
}
