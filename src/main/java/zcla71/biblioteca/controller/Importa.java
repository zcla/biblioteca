package zcla71.biblioteca.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.biblioteca.dao.BibliotecaDao;
import zcla71.biblioteca.model.Livro;
import zcla71.biblioteca.model.config.Config;
import zcla71.biblioteca.model.libib.LibibLivro;
import zcla71.biblioteca.model.secret.Secret;
import zcla71.seatable.SeaTableConnection;
import zcla71.seatable.model.ddl.RowDef;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.ddl.TableDeleteDef;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.InsertRowResult;

@RestController
@RequestMapping(produces={ MediaType.APPLICATION_JSON_VALUE })
public class Importa {
    @GetMapping(value="/importa/libib")
    public Collection<LibibLivro> importaLibib() throws StreamReadException, DatabindException, IllegalStateException, FileNotFoundException, IOException {
        return BibliotecaDao.getInstance().getLibibLivros();
    }

    private Livro libibLivro2Livro(LibibLivro libib) {
        Livro result = new Livro();

        // TODO Fazer direito
        Integer id = 1;
        result.setId(id);

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
        Config config = BibliotecaDao.getInstance().getConfig();
        Collection<LibibLivro> libibLivros = importaLibib();
        Collection<Livro> result = new ArrayList<Livro>();
        for (LibibLivro libibLivro : libibLivros) {
            Livro livro = libibLivro2Livro(libibLivro);
            result.add(livro);
        }

        SeaTableConnection dao = new SeaTableConnection(secret.getSeaTable().getApiToken());
        Metadata metadata = dao.getMetadata();

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
                Table tableMetadata = dao.createTable(tableDef);
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
                TableDeleteDef tableDeleteDef = new TableDeleteDef(table);
                @SuppressWarnings("unused")
                DeleteTableResult success = dao.deleteTable(tableDeleteDef);
            }
        }

        // Insere dados
        // TODO Lento DEMAIS (3:32)! Tentar o "Append Rows": https://api.seatable.io/reference/append-rows
        for (Livro livro : result) {
            RowDef rowDef = new RowDef();
            rowDef.setTable_name("livro");
            rowDef.setRow(new HashMap<>());
            rowDef.getRow().put("id", livro.getId().toString());
            rowDef.getRow().put("nome", livro.getNome());
            InsertRowResult obj = dao.insertRow("livro", rowDef);
            System.out.println(obj);
        }

        return result;
    }
}
