package zcla71.dao.seatable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.dao.config.SeaTableBase;
import zcla71.dao.seatable.transaction.TransactionException;
import zcla71.dao.seatable.transaction.TransactionExecutionData;
import zcla71.dao.seatable.transaction.TransactionOperation;
import zcla71.dao.seatable.transaction.TransactionOperationAddRow;
import zcla71.dao.seatable.transaction.TransactionOperationCreateRowLink;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.ddl.ColumnDef;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.metadata.Column;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.ListRowsResult;

public abstract class SeaTableDao {
    // Utils

    public static String getNewId() {
        return UUID.randomUUID().toString();
    }

    // Inicialização
    
    private SeaTableConfig config;
    private SeaTableApi api;
    private Metadata metadata;

    protected SeaTableDao(SeaTableConfig config, String apiToken) throws IOException, SeaTableDaoException {
        this.config = config;

        api = new SeaTableApi(apiToken);
        metadata = api.getMetadata();
        setupDatabase();
    }

    private void setupDatabase() throws StreamReadException, DatabindException, IOException, SeaTableDaoException {
        for (SeaTableBase base : config.getBases()) {
            boolean reloadMetadata = false;

            // Cria as tabelas que não existem
            for (TableDef tableDef : base.getTables()) {
                try {
                    @SuppressWarnings("unused")
                    Table table = metadata.getMetadata().getTables().stream()
                        .filter(t -> t.getName().equals(tableDef.getTable_name()))
                        .findFirst()
                        .get();
                } catch (NoSuchElementException e) {
                    // Verifica se a primeira coluna é "number id"
                    ColumnDef id = tableDef.getColumns().iterator().next();
                    if (!id.getColumn_name().equals("id")) {
                        throw new SeaTableDaoException("A primeira coluna da tabela deve se chamar \"id\".");
                    }
                    if (!id.getColumn_type().equals("text")) {
                        throw new SeaTableDaoException("A primeira coluna da tabela deve ser do tipo \"text\".");
                    }

                    // Cria a tabela
                    CreateNewTableParam cntParam = new CreateNewTableParam();
                    cntParam.setTable_name(tableDef.getTable_name());
                    cntParam.setColumns(tableDef.getColumns());
                    @SuppressWarnings("unused")
                    CreateNewTableResult ctpResult = api.createNewTable(cntParam);
                    reloadMetadata = true;
                }
            }

            // Exclui tabelas que não deveriam existir
            for (Table table : metadata.getMetadata().getTables()) {
                try {
                    @SuppressWarnings("unused")
                    TableDef tableDef = base.getTables().stream()
                        .filter(t -> t.getTable_name().equals(table.getName()))
                        .findFirst()
                        .get();
                } catch (NoSuchElementException e) {
                    DeleteTableParam tableDeleteDef = new DeleteTableParam(table);
                    @SuppressWarnings("unused")
                    DeleteTableResult success = api.deleteTable(tableDeleteDef);
                    reloadMetadata = true;
                }
            }

            // Atualiza metadata
            if (reloadMetadata) {
                metadata = api.getMetadata();
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
    }

    // Transação

    private Collection<TransactionOperation> transaction = null;

    public void startTransaction() throws TransactionException {
        if (transaction != null) {
            throw new TransactionException("Já há uma transação em andamento!");
        }
        transaction = new ArrayList<>();
    }

    public void commitTransaction() throws TransactionException, IOException {
        if (transaction == null) {
            throw new TransactionException("Não há transação em andamento!");
        }
        Collection<TransactionExecutionData> executions = new ArrayList<>();

        // Só os que não são CreateRowLink
        Collection<TransactionOperation> nonLink = transaction.stream().filter(t -> ! (t instanceof TransactionOperationCreateRowLink)).toList();
        for (TransactionOperation op : nonLink) {
            // TODO Batch
            op.execute(api);
            executions.add(new TransactionExecutionData(op));
        }

        // "Traduz" de id para _id
        Map<String, String> idMap = new HashMap<>();
        for (TransactionExecutionData execution : executions) {
            idMap.putAll(execution.getIds());
        }

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

        // Só os CreateRowLink
        Collection<TransactionOperation> link = transaction.stream().filter(t -> (t instanceof TransactionOperationCreateRowLink)).toList();
        for (TransactionOperation op : link) {
            // TODO Batch
            op.applyIdMap(idMap);
            op.execute(api);
        }

        // Fim
        discardTransaction();
    }

    public void discardTransaction() throws TransactionException {
        if (transaction == null) {
            throw new TransactionException("Não há transação em andamento!");
        }
        transaction = null;
    }

    // Operações de transação

    public void addRow(AddRowParam arParam) {
        this.transaction.add(new TransactionOperationAddRow(arParam));
        Table table = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(arParam.getTable_name())).findFirst().get();
        for (String rowKey : arParam.getRow().keySet()) {
            Column column = table.getColumns().stream().filter(c -> c.getName().equals(rowKey)).findFirst().get();
            if (column.getType().equals("link")) {
                @SuppressWarnings("unchecked")
                Collection<String> other_table_row_ids = (Collection<String>) arParam.getRow().get(column.getName());
                for (String other_table_row_id : other_table_row_ids) {
                    CreateRowLinkParam crlParam = new CreateRowLinkParam();
                    crlParam.setTable_name(table.getName());
                    String otherTableName = metadata.getMetadata().getTables().stream().filter(t -> t.get_id().equals(column.getData().getOther_table_id())).findFirst().get().getName();
                    crlParam.setOther_table_name(otherTableName);
                    crlParam.setLink_id(column.getData().getLink_id());
                    crlParam.setTable_row_id((String) arParam.getRow().get("id"));
                    crlParam.setOther_table_row_id(other_table_row_id);
                    this.transaction.add(new TransactionOperationCreateRowLink(crlParam));
                }
            }
        }
    }
}
