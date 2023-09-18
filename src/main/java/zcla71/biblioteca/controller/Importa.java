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
import zcla71.biblioteca.model.config.Config;
import zcla71.biblioteca.model.libib.Importacao;
import zcla71.biblioteca.model.libib.LibibLivro;
import zcla71.biblioteca.model.secret.Secret;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.CreateRowLinkResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.ListRowsResult;
import zcla71.seatable.model.result.AddRowResult;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/libib/livro")
    public Collection<LibibLivro> libibLivro() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    private Importacao importa() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        Collection<LibibLivro> libibLivros = libibLivro();

        Importacao result = new Importacao();
        result.setAutores(new ArrayList<Autor>());
        int lastIdAutor = 0;
        result.setLivros(new ArrayList<Livro>());
        int lastIdLivro = 0;

        for (LibibLivro libibLivro : libibLivros) {
            Livro livro = new Livro();

            // id
            livro.setId(++lastIdLivro);

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
                livro.setAutores(new ArrayList<Autor>());
                String[] splAutores = libibLivro.getCreators().split(",");
                for (String strAutor : splAutores) {
                    String nomeAutor = strAutor.trim();
                    Autor autor = null;
                    try {
                        autor = result.getAutores().stream().filter(a -> a.getNome().equals(nomeAutor)).findFirst().get();
                    } catch (NoSuchElementException e) {
                        autor = new Autor();
                        autor.setId(++lastIdAutor);
                        autor.setNome(nomeAutor);
                        result.getAutores().add(autor);
                    }
                    livro.getAutores().add(autor);
                }
            }

            result.getLivros().add(livro);
        }

        return result;
    }

    private void setupDatabase(SeaTableApi api, Metadata metadata) throws StreamReadException, DatabindException, IOException {
        Config config = BibliotecaDao.getInstance().getConfig();

        // Cria as tabelas que não existem
        for (TableDef tableDef : config.getSeaTable().getBiblioteca().getTables()) {
            try {
                @SuppressWarnings("unused")
                Table table = metadata.getMetadata().getTables().stream()
                    .filter(t -> t.getName().equals(tableDef.getTable_name()))
                    .findFirst()
                    .get();
            } catch (NoSuchElementException e) {
                CreateNewTableParam cntParam = new CreateNewTableParam();
                cntParam.setTable_name(tableDef.getTable_name());
                cntParam.setColumns(tableDef.getColumns());
                @SuppressWarnings("unused")
                CreateNewTableResult ctpResult = api.createNewTable(cntParam);
                System.out.println(ctpResult);
            }
        }

        // Exclui tabelas que não deveriam existir
        for (Table table : metadata.getMetadata().getTables()) {
            try {
                @SuppressWarnings("unused")
                TableDef tableDef = config.getSeaTable().getBiblioteca().getTables().stream()
                    .filter(t -> t.getTable_name().equals(table.getName()))
                    .findFirst()
                    .get();
            } catch (NoSuchElementException e) {
                DeleteTableParam tableDeleteDef = new DeleteTableParam(table);
                @SuppressWarnings("unused")
                DeleteTableResult success = api.deleteTable(tableDeleteDef);
            }
        }

        // Exclui dados das tabelas
        for (Table table : metadata.getMetadata().getTables()) {
            while (true) {
                ListRowsParam lrp = new ListRowsParam(table.getName());
                ListRowsResult lrr = api.listRows(lrp);
                if (lrr.getRows().size() == 0) {
                    break;
                }
                DeleteRowsParam param = new DeleteRowsParam();
                param.setTable_name(table.getName());
                param.setRow_ids(new ArrayList<>());
                for (Row row : lrr.getRows()) {
                    param.getRow_ids().add((String) row.get("_id"));
                }
                @SuppressWarnings("unused")
                DeleteRowsResult drResult = api.deleteRows(param);
            }
        }
    }

    @PostMapping(value="/libib/importacao")
    public Importacao libibImportacao() throws StreamReadException, DatabindException, IOException {
        Importacao result = importa();

        Secret secret = BibliotecaDao.getInstance().getSecret();
        SeaTableApi api = new SeaTableApi(secret.getSeaTable().getApiToken());

        Metadata metadata = api.getMetadata();

        setupDatabase(api, metadata);

        // Insere dados
        boolean forceOneByOne = false;

        if (forceOneByOne) {
            for (Autor autor : result.getAutores()) {
                AddRowParam param = new AddRowParam();
                param.setTable_name("autor");
                param.setRow(new Row());
                param.getRow().put("id", autor.getId().toString());
                param.getRow().put("nome", autor.getNome());
                @SuppressWarnings("unused")
                AddRowResult irResult = api.addRow(param);
                System.out.println(irResult);
            }
        } else {
            AppendRowsParam param = new AppendRowsParam();
            param.setTable_name("autor");
            param.setRows(new ArrayList<>());
            for (Autor autor : result.getAutores()) {
                Row row = new Row();
                row.put("id", autor.getId().toString());
                row.put("nome", autor.getNome());
                param.getRows().add(row);
            }
            @SuppressWarnings("unused")
            AppendRowsResult arResult = api.appendRows(param);
        }

        ListRowsParam lrParam = new ListRowsParam("autor");
        ListRowsResult lrResult = api.listRows(lrParam);

        forceOneByOne = true;
        if (forceOneByOne) { // Versão um por um (lenta)
            String link_id = metadata.getMetadata()
                    .getTables().stream().filter(t -> t.getName().equals("livro")).findFirst().get()
                    .getColumns().stream().filter(c -> c.getName().equals("autores")).findFirst().get()
                    .getData().getLink_id();
            for (Livro livro : result.getLivros()) {
                AddRowParam param = new AddRowParam();
                param.setTable_name("livro");
                param.setRow(new Row());
                param.getRow().put("id", livro.getId().toString());
                param.getRow().put("nome", livro.getNome());
                Collection<String> autores = new ArrayList<String>();
                if (livro.getAutores() != null) {
                    for (Autor autor : livro.getAutores()) {
                        Row row = lrResult.getRows().stream().filter(a -> a.get("nome").equals(autor.getNome())).findFirst().get();
                        if (row != null) {
                            autores.add((String) row.get("_id"));
                        }
                    }
                    param.getRow().put("autores", autores);
                }
                AddRowResult irResult = api.addRow(param);
                // Insere os links, que NÃO são inseridos no addRow.
                for (String table_row_id : autores) {
                    CreateRowLinkParam crlParam = new CreateRowLinkParam();
                    crlParam.setTable_name("livro");
                    crlParam.setOther_table_name("autor");
                    crlParam.setLink_id(link_id);
                    crlParam.setTable_row_id(irResult.get("_id"));
                    crlParam.setOther_table_row_id(table_row_id);
                    @SuppressWarnings("unused")
                    CreateRowLinkResult crlResult = api.createRowLink(crlParam);
                }
            }
        } else { // Versão batch
        //     AppendRowsParam param = new AppendRowsParam();
        //     param.setTable_name("livro");
        //     param.setRows(new ArrayList<>());
        //     for (Livro livro : result.getLivros()) {
        //         Row row = new Row();
        //         row.put("id", livro.getId().toString());
        //         row.put("nome", livro.getNome());
        //         param.getRows().add(row);
        //     }
        //     @SuppressWarnings("unused")
        //     AppendRowsResult arResult = api.appendRows(param);
        }

        return result;
    }
}
