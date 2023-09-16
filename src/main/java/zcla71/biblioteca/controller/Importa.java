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
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.config.Config;
import zcla71.biblioteca.model.libib.LibibLivro;
import zcla71.biblioteca.model.secret.Secret;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.AddRowResult;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    private int id = 0;

    @GetMapping(value="/importa/libib")
    public Collection<LibibLivro> importaLibib() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    private Livro libibLivro2Livro(LibibLivro libib) {
        Livro result = new Livro();

        result.setId(++id);

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

    @PostMapping(value="/importa/seatable")
    public Collection<Livro> importaSeatable() throws StreamReadException, DatabindException, IOException {
        Secret secret = BibliotecaDao.getInstance().getSecret();
        Config config = BibliotecaDao.getInstance().getConfig();
        Collection<LibibLivro> libibLivros = importaLibib();
        Collection<Livro> result = new ArrayList<Livro>();
        for (LibibLivro libibLivro : libibLivros) {
            Livro livro = libibLivro2Livro(libibLivro);
            result.add(livro);
        }

        SeaTableApi api = new SeaTableApi(secret.getSeaTable().getApiToken());
        Metadata metadata = api.getMetadata();

        // Cria as tabelas que não existem
        for (TableDef tableDef : config.getSeaTable().getBiblioteca().getTables()) {
            try {
                @SuppressWarnings("unused")
                Table table = metadata.getMetadata().getTables().stream()
                    .filter(t -> t.getName().equals(tableDef.getTable_name()))
                    .findFirst()
                    .get();
            } catch (NoSuchElementException e) {
                @SuppressWarnings("unused")
                CreateNewTableResult cTableParamResult = api.createNewTable(new CreateNewTableParam(tableDef));
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

        // Insere dados
        final boolean forceOneByOne = false;
        if (forceOneByOne) { // Versão um por um (lenta)
            for (Livro livro : result) {
                AddRowParam param = new AddRowParam();
                param.setTable_name("livro");
                param.setRow(new Row());
                param.getRow().put("id", livro.getId().toString());
                param.getRow().put("nome", livro.getNome());
                @SuppressWarnings("unused")
                AddRowResult irResult = api.addRow(param);
            }
        } else { // Versão batch
            AppendRowsParam param = new AppendRowsParam();
            param.setTable_name("livro");
            param.setRows(new ArrayList<>());
            for (Livro livro : result) {
                Row row = new Row();
                row.put("id", livro.getId().toString());
                row.put("nome", livro.getNome());
                param.getRows().add(row);
            }
            @SuppressWarnings("unused")
            AppendRowsResult arResult = api.appendRows(param);
        }

        return result;
    }
}
