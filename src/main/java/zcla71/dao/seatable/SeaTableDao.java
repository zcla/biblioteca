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
import zcla71.dao.seatable.transaction.TransactionOperationAppendRows;
import zcla71.dao.seatable.transaction.TransactionOperationCreateRowLink;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.ddl.ColumnDef;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.metadata.Column;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.CreateRowLinksBatchParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.CreateRowLinksBatchResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.ListRowsResult;

public abstract class SeaTableDao {
    // Utils

    public static String getNewId() {
        return UUID.randomUUID().toString();
    }

    // Config

    private final boolean FORCE_ONE_BY_ONE = false;

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
        while (nonLink.size() > 0) {
            TransactionOperation singleOperation = nonLink.iterator().next();
            TransactionOperation actualOperation = null;
            Collection<TransactionOperation> resolvedOperations = new ArrayList<>();
            if (FORCE_ONE_BY_ONE) {
                actualOperation = singleOperation;
                resolvedOperations.add(singleOperation);
            } else {
                if (singleOperation instanceof TransactionOperationAddRow arOperation) {
                    resolvedOperations = transaction.stream().filter(t -> (t instanceof TransactionOperationAddRow toar) && (toar.getParam().getTable_name().equals(arOperation.getParam().getTable_name()))).toList();
                    AppendRowsParam arParam = new AppendRowsParam(new ArrayList<Row>(), arOperation.getParam().getTable_name());
                    for (TransactionOperation operation : resolvedOperations) {
                        if (operation instanceof TransactionOperationAddRow aro) { // Sempre true por causa do filter acima
                            Row row = new Row();
                            for (String key : aro.getParam().getRow().keySet()) {
                                row.put(key, aro.getParam().getRow().get(key));
                            }
                            arParam.getRows().add(row);
                        }
                    }
                    actualOperation = new TransactionOperationAppendRows(arParam, api);
                }
                if (actualOperation == null) {
                    actualOperation = singleOperation;
                    resolvedOperations.add(singleOperation);
                }
            }
            actualOperation.execute(api);
            executions.add(new TransactionExecutionData(actualOperation));
            final Collection<TransactionOperation> ro = resolvedOperations;
            nonLink = nonLink.stream().filter(nl -> !ro.contains(nl)).toList();
        }

        // "Traduz" de id para _id
        // TODO Não pode ser assim porque pode haver ids que já estavam no banco.
        Map<String, String> idMap = new HashMap<>();
        for (TransactionExecutionData execution : executions) {
            idMap.putAll(execution.getIds());
        }

        // Só os CreateRowLink
        Collection<TransactionOperation> link = transaction.stream().filter(t -> (t instanceof TransactionOperationCreateRowLink)).toList();
        
        while (link.size() > 0) {
            TransactionOperation op = link.iterator().next();
            if (FORCE_ONE_BY_ONE) {
                op.applyIdMap(idMap);
                op.execute(api);
                link = link.stream().filter(l -> !(l.equals(op))).toList();
            } else {
                if (op instanceof TransactionOperationCreateRowLink tocrl) { // Sempre true por causa do filter acima
                    String table_id = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(tocrl.getParam().getTable_name())).findFirst().get().get_id();
                    String other_table_id = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(tocrl.getParam().getOther_table_name())).findFirst().get().get_id();
                    String link_id = tocrl.getParam().getLink_id();
                    Collection<String> row_id_list = new ArrayList<>();
                    Map<String, Collection<String>> other_rows_ids_map = new HashMap<>();
                    for (TransactionOperation operation : link) {
                        if (operation instanceof TransactionOperationCreateRowLink tocrl2) { // Sempre true por causa do filter acima
                            row_id_list.add(idMap.get(tocrl2.getParam().getTable_row_id()));
                            Collection<String> param2 = new ArrayList<>();
                            param2.add(idMap.get(tocrl2.getParam().getOther_table_row_id()));
                            other_rows_ids_map.put(idMap.get(tocrl2.getParam().getTable_row_id()), param2);
                        }
                    }
                    CreateRowLinksBatchParam crlbParam = new CreateRowLinksBatchParam(table_id, other_table_id, link_id, row_id_list, other_rows_ids_map);
                    @SuppressWarnings("unused")
                    CreateRowLinksBatchResult crlbResult = api.createRowLinksBatch(crlbParam);
                    link = new ArrayList<TransactionOperation>();
                }
            }
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
