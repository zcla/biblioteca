package zcla71.dao.seatable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zcla71.dao.seatable.config.SeaTableBase;
import zcla71.dao.seatable.config.SeaTableConfig;
import zcla71.dao.seatable.config.def.TableDef;
import zcla71.dao.seatable.config.def.column.ColumnDef;
import zcla71.dao.seatable.config.def.column.ColumnDefNumber;
import zcla71.dao.seatable.transaction.TransactionException;
import zcla71.dao.seatable.transaction.TransactionExecutionData;
import zcla71.dao.seatable.transaction.TransactionOperation;
import zcla71.dao.seatable.transaction.TransactionOperationAddRow;
import zcla71.dao.seatable.transaction.TransactionOperationAppendRows;
import zcla71.dao.seatable.transaction.TransactionOperationCreateRowLink;
import zcla71.dao.seatable.transaction.TransactionOperationGeneratesId;
import zcla71.seatable.SeaTableApi;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Row;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.metadata.column.Column;
import zcla71.seatable.model.metadata.column.ColumnDate;
import zcla71.seatable.model.metadata.column.ColumnLink;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.CreateRowLinksBatchParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.InsertColumnParam;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.CreateRowLinksBatchResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.InsertColumnResult;
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

            // Remove todas as tabelas para que sejam recriadas
            String dummyTableName = null;
            if (base.getOptions().getStartup().getRecreateExistingTables()) {
                // Cria uma tabela dummy porque o SeaTable não permite que uma base não tenha nenhuma tabela
                dummyTableName = "dummy_" + getNewId();
                CreateNewTableParam cntParam = new CreateNewTableParam();
                cntParam.setTable_name(dummyTableName);
                Collection<ColumnDef> columns = new ArrayList<>();
                ColumnDef columnDef = new ColumnDefNumber();
                columnDef.setColumn_name("id");
                columns.add(columnDef);
                cntParam.setColumns(columns);
                @SuppressWarnings("unused")
                CreateNewTableResult ctpResult = api.createNewTable(cntParam);

                // Remove todas as outras tabelas
                for (Table table : metadata.getMetadata().getTables()) {
                    DeleteTableParam tableDeleteDef = new DeleteTableParam(table.getName());
                    @SuppressWarnings("unused")
                    DeleteTableResult success = api.deleteTable(tableDeleteDef);
                }

                metadata = api.getMetadata();
            }

            // Cria as tabelas que não existem
            if (base.getOptions().getStartup().getCreateMissingTables()) {
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
            }
            if (reloadMetadata) {
                metadata = api.getMetadata();
                reloadMetadata = false;
            }

            // Cria as colunas que não existem
            if (base.getOptions().getStartup().getCreateMissingColumns()) {
                for (TableDef tableDef : base.getTables()) {
                    for (ColumnDef columnDef : tableDef.getColumns()) {
                        if (!metadata.getMetadata().getTables()
                                .stream().filter(t -> t.getName().equals(tableDef.getTable_name())).findFirst().get().getColumns()
                                .stream().anyMatch(c -> c.getName().equals(columnDef.getColumn_name()))) {
                            InsertColumnParam icParam = new InsertColumnParam(tableDef.getTable_name(), columnDef);
                            @SuppressWarnings("unused")
                            InsertColumnResult icResult = api.insertColumn(icParam);
                            reloadMetadata = true;
                        }
                    }
                }
            }

            // Exclui tabelas não previstas na configuração
            if (base.getOptions().getStartup().getRemoveAlienTables()) {
                for (Table table : metadata.getMetadata().getTables()) {
                    try {
                        @SuppressWarnings("unused")
                        TableDef tableDef = base.getTables().stream()
                            .filter(t -> t.getTable_name().equals(table.getName()))
                            .findFirst()
                            .get();
                    } catch (NoSuchElementException e) {
                        if (!table.getName().equals(dummyTableName)) {
                            DeleteTableParam tableDeleteDef = new DeleteTableParam(table);
                            @SuppressWarnings("unused")
                            DeleteTableResult success = api.deleteTable(tableDeleteDef);
                            reloadMetadata = true;
                        }
                    }
                }
            }

            // Exclui a tabela dummy criada em base.getOptions().getStartup().getRecreateExistingTables()
            if (dummyTableName != null) {
                DeleteTableParam tableDeleteDef = new DeleteTableParam(dummyTableName);
                @SuppressWarnings("unused")
                DeleteTableResult success = api.deleteTable(tableDeleteDef);
                reloadMetadata = true;
            }

            // Atualiza metadata
            if (reloadMetadata) {
                metadata = api.getMetadata();
                reloadMetadata = false;
            }

            if (base.getOptions().getStartup().getEraseData()) {
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
    }

    // Transação

    private Collection<TransactionOperation> transaction = null;

    public void startTransaction() throws TransactionException {
        if (transaction != null) {
            throw new TransactionException("Já há uma transação em andamento!");
        }
        transaction = new ArrayList<>();
    }

    public void commitTransaction() throws IOException, SeaTableDaoException {
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
                converteDadosParaValoresEsperadosPelaAPI(actualOperation);
                resolvedOperations.add(singleOperation);
            } else {
                if (singleOperation instanceof TransactionOperationAddRow arOperation) {
                    resolvedOperations = transaction.stream().filter(t -> (t instanceof TransactionOperationAddRow toar) && (toar.getParam().getTable_name().equals(arOperation.getParam().getTable_name()))).toList();
                    AppendRowsParam arParam = new AppendRowsParam(new ArrayList<Row>(), arOperation.getParam().getTable_name());
                    for (TransactionOperation operation : resolvedOperations) {
                        converteDadosParaValoresEsperadosPelaAPI(operation);
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
            if (actualOperation instanceof TransactionOperationGeneratesId togi) {
                executions.add(new TransactionExecutionData(togi));
            }
            final Collection<TransactionOperation> ro = resolvedOperations;
            nonLink = nonLink.stream().filter(nl -> !ro.contains(nl)).toList();
        }

        // "Traduz" de id para _id
        Map<String, String> idMap = new HashMap<>();
        for (TransactionExecutionData execution : executions) {
            idMap.putAll(execution.getIds());
        }

        // Só os CreateRowLink
        Collection<TransactionOperationCreateRowLink> link = transaction.stream().filter(t -> (t instanceof TransactionOperationCreateRowLink)).map(TransactionOperationCreateRowLink.class::cast).toList();

        // Procura IDs que não estão no idMap.
        Collection<CreateRowLinkParam> params = link.stream().map(TransactionOperationCreateRowLink::getParam).collect(Collectors.toCollection(ArrayList::new));
        Collection<String> idsTodos = params.stream().map(CreateRowLinkParam::getTable_row_id).collect(Collectors.toCollection(ArrayList::new));
        idsTodos.addAll(params.stream().map(CreateRowLinkParam::getOther_table_row_id).collect(Collectors.toCollection(ArrayList::new)));
        Collection<String> idsFaltando = idsTodos.stream().filter(id -> idMap.get(id) == null).collect(Collectors.toCollection(ArrayList::new));
        if (idsFaltando.size() > 0) {
            throw new RuntimeException("Implementar");
        }

        while (link.size() > 0) {
            TransactionOperationCreateRowLink tocrl1 = link.iterator().next();
            Collection<TransactionOperationCreateRowLink> resolvedOperations = new ArrayList<>();
            if (FORCE_ONE_BY_ONE) {
                tocrl1.applyIdMap(idMap);
                tocrl1.execute(api);
                link = link.stream().filter(l -> !(l.equals(tocrl1))).toList();
                resolvedOperations.add(tocrl1);
            } else {
                String table_id = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(tocrl1.getParam().getTable_name())).findFirst().get().get_id();
                String other_table_id = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(tocrl1.getParam().getOther_table_name())).findFirst().get().get_id();
                String link_id = tocrl1.getParam().getLink_id();
                Collection<TransactionOperationCreateRowLink> group1 = link.stream().filter(t -> (
                    t.getParam().getTable_name().equals(tocrl1.getParam().getTable_name()) &&
                    t.getParam().getOther_table_name().equals(tocrl1.getParam().getOther_table_name()) &&
                    t.getParam().getLink_id().equals(tocrl1.getParam().getLink_id())
                )).toList();
                Collection<String> row_id_list = new ArrayList<>();
                Map<String, Collection<String>> other_rows_ids_map = new HashMap<>();
                while (group1.size() > 0) {
                    TransactionOperationCreateRowLink tocrl2 = group1.iterator().next();
                    String row_id = idMap.get(tocrl2.getParam().getTable_row_id());
                    row_id_list.add(row_id);
                    other_rows_ids_map.put(row_id, new ArrayList<>());
                    Collection<TransactionOperationCreateRowLink> group2 = group1.stream().filter(t -> (t.getParam().getTable_row_id().equals(tocrl2.getParam().getTable_row_id()))).toList();
                    while (group2.size() > 0) {
                        TransactionOperationCreateRowLink tocrl3 = group2.iterator().next();
                        String other_row_id = idMap.get(tocrl3.getParam().getOther_table_row_id());
                        other_rows_ids_map.get(row_id).add(other_row_id);
                        resolvedOperations.add(tocrl3);
                        final Collection<TransactionOperationCreateRowLink> ro = resolvedOperations;
                        group2 = group2.stream().filter(nl -> !ro.contains(nl)).toList();
                    }
                    final Collection<TransactionOperationCreateRowLink> ro = resolvedOperations;
                    group1 = group1.stream().filter(nl -> !ro.contains(nl)).toList();
                }
                CreateRowLinksBatchParam crlbParam = new CreateRowLinksBatchParam(table_id, other_table_id, link_id, row_id_list, other_rows_ids_map);
                @SuppressWarnings("unused")
                CreateRowLinksBatchResult crlbResult = api.createRowLinksBatch(crlbParam);
            }
            final Collection<TransactionOperationCreateRowLink> ro = resolvedOperations;
            link = link.stream().filter(nl -> !ro.contains(nl)).toList();
        }

        // Fim
        discardTransaction();
    }

    private void converteDadosParaValoresEsperadosPelaAPI(TransactionOperation singleOperation) throws SeaTableDaoException {
        if (singleOperation instanceof TransactionOperationAddRow toar) {
            Row row = toar.getParam().getRow();
            for (String key : row.keySet()) {
                Object value = row.get(key);
                // "Traduz" as datas para o formato esperado pela API
                if (value instanceof Date) {
                    String table_name = toar.getParam().getTable_name();
                    Table table = metadata.getMetadata().getTables().stream().filter(t -> t.getName().equals(table_name)).findFirst().get();
                    String column_name = key;
                    Column column = table.getColumns().stream().filter(c -> c.getName().equals(column_name)).findFirst().get();
                    if (column instanceof ColumnDate cd) {
                        String format = cd.getData().getFormat();
                        DateFormat df = null;
                        switch (format) {
                            case "YYYY-MM-DD":
                                df = new SimpleDateFormat("yyyy-MM-dd");
                                break;
                            case "YYYY-MM-DD HH:mm":
                                df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                break;
                            case "M/D/YYYY":
                                df = new SimpleDateFormat("M/d/yyyy");
                                break;
                            case "M/D/YYYY HH:mm":
                                df = new SimpleDateFormat("M/d/yyyy HH:mm");
                                break;
                            case "DD/MM/YYYY":
                                df = new SimpleDateFormat("dd/MM/yyyy");
                                break;
                            case "DD/MM/YYYY HH:mm":
                                df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                break;
                            case "DD.MM.YYYY":
                                df = new SimpleDateFormat("dd.MM.yyyy");
                                break;
                            case "DD.MM.YYYY HH:mm":
                                df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                break;
                            default:
                                throw new SeaTableDaoException("Formato de data desconhecido: " + format);
                        }
                        row.put(key, df.format(value));
                    } else {
                        throw new SeaTableDaoException("Tentando colocar um java.util.Date numa coluna do tipo " + column.getType() + "?");
                    }
                }
            }
        }
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
            if (column instanceof ColumnLink columnLink) {
                Collection<String> other_table_row_ids = new ArrayList<>();
                Object obj = arParam.getRow().get(columnLink.getName());
                if (obj instanceof Collection objC) {
                    @SuppressWarnings("unchecked")
                    Collection<String> objCS = objC;
                    other_table_row_ids.addAll(objCS);
                } else {
                    if (obj instanceof String objS) {
                        other_table_row_ids.add(objS);
                    } else {
                        throw new RuntimeException("Coluna não é Collection nem String");
                    }
                }
                if (other_table_row_ids != null) {
                    for (String other_table_row_id : other_table_row_ids) {
                        CreateRowLinkParam crlParam = new CreateRowLinkParam();
                        crlParam.setTable_name(table.getName());
                        String otherTableName = metadata.getMetadata().getTables().stream().filter(t -> t.get_id().equals(columnLink.getData().getOther_table_id())).findFirst().get().getName();
                        crlParam.setOther_table_name(otherTableName);
                        crlParam.setLink_id(columnLink.getData().getLink_id());
                        crlParam.setTable_row_id((String) arParam.getRow().get("id"));
                        crlParam.setOther_table_row_id(other_table_row_id);
                        this.transaction.add(new TransactionOperationCreateRowLink(crlParam));
                    }
                }
            }
        }
    }
}
