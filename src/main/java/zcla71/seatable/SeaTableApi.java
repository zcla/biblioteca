package zcla71.seatable;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import lombok.extern.slf4j.Slf4j;
import zcla71.dao.seatable.config.def.column.ColumnDef;
import zcla71.seatable.model.BaseToken;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.CreateRowLinksBatchParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.InsertColumnParam;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.param.ListRowsSqlParam;
import zcla71.seatable.model.result.AddRowResult;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.CreateRowLinkResult;
import zcla71.seatable.model.result.CreateRowLinksBatchResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.InsertColumnResult;
import zcla71.seatable.model.result.ListRowsResult;
import zcla71.seatable.model.result.ListRowsSqlResult;

// https://api.seatable.io/reference
@Slf4j
public class SeaTableApi {
    private ObjectMapper objectMapper;
    private BaseToken baseToken = null;

    public SeaTableApi(String apiToken) throws IOException {
        objectMapper = new ObjectMapper();
        baseToken = generateBaseToken(apiToken);
    }

    // Authentication

    // https://api.seatable.io/reference/get-base-token-with-api-token
    private BaseToken generateBaseToken(String apiToken) throws IOException {
        log.info("generateBaseToken");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/api/v2.1/dtable/app-access-token/")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("authorization", "Bearer " + apiToken)
            .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        if (response.code() != 200) {
            throw new RuntimeException(responseBody, new RuntimeException(response.message()));
        }
        log.info(response.code() + ": " + responseBody.length() + " bytes.");
        return objectMapper.readValue(responseBody, BaseToken.class);
    }

    // Métodos genéricos

    private int requestCount = 0;

    private Builder prepareBuilder(String url, String method) {
        log.info("Request #" + ++requestCount + ": " + method + " " + url.split("\\?")[0]);
        return new Request.Builder()
                .url(url);
    }

    private Object completeBuilder(Builder builder, Class<? extends Object> resultClass) throws IOException {
        Request request = builder
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        log.info("Response " + response.code() + ": " + responseBody.length() + " bytes.");
        if (response.code() != 200) {
            throw new RuntimeException(responseBody, new RuntimeException(response.message()));
        }
        return objectMapper.readValue(responseBody, resultClass);
    }

    private RequestBody getRequestBody(Object param) throws JsonProcessingException {
        MediaType mediaType = MediaType.parse("application/json");
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        return body;
    }

    public <T> T doDelete(String url, Object param, Class<T> resultClass) throws IOException {
        @SuppressWarnings("unchecked")
        T result = (T) completeBuilder(prepareBuilder(url, "DELETE").delete(getRequestBody(param)), resultClass);
        return result;
    }

    public <T> T doGet(String url, Class<T> resultClass) throws IOException {
        @SuppressWarnings("unchecked")
        T result = (T) completeBuilder(prepareBuilder(url, "GET").get(), resultClass);
        return result;
    }

    public <T> T doPost(String url, Object param, Class<T> resultClass) throws IOException {
        @SuppressWarnings("unchecked")
        T result = (T) completeBuilder(prepareBuilder(url, "POST").post(getRequestBody(param)), resultClass);
        return result;
    }

    public <T> T doPut(String url, Object param, Class<T> resultClass) throws IOException {
        @SuppressWarnings("unchecked")
        T result = (T) completeBuilder(prepareBuilder(url, "PUT").put(getRequestBody(param)), resultClass);
        return result;
    }

    // Base Info

    // https://api.seatable.io/reference/get-metadata
    public Metadata getMetadata() throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/metadata/";
        Metadata result = doGet(url, Metadata.class);
        log.info("[Get Metadata] versão: " + result.getMetadata().getFormat_version() + "; tabelas: " + result.getMetadata().getTables().size() + ".");
        return result;
    }

    // Rows

    // https://api.seatable.io/reference/list-rows
    public ListRowsResult listRows(ListRowsParam param) throws IOException {
        // TODO A API só traz os 1000 primeiros. Fazer mais consultas com o parâmetro "start" até não vir mais nada.
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/rows/" + param.getUrlParams();
        ListRowsResult result = doGet(url, ListRowsResult.class);
        log.info("[List Rows] rows: " + result.getRows().size() + ".");
        return result;
    }

    // https://api.seatable.io/reference/list-rows-with-sql
    public ListRowsSqlResult listRowsSql(ListRowsSqlParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-db/api/v1/query/" + baseToken.getDtable_uuid() + "/";
        ListRowsSqlResult result = doPost(url, param, ListRowsSqlResult.class);
        log.info("[List Rows (with SQL)] success: " + result.getSuccess() + "; results: " + result.getResults().size() + ".");
        return result;
    }

    // https://api.seatable.io/reference/add-row
    public AddRowResult addRow(AddRowParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/rows/";
        AddRowResult result = doPost(url, param, AddRowResult.class);
        log.info("[Add Row] fields: " + result.size() + ".");
        return result;
    }

    // https://api.seatable.io/reference/append-rows
    public AppendRowsResult appendRows(AppendRowsParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/batch-append-rows/";
        AppendRowsResult result = doPost(url, param, AppendRowsResult.class);
        log.info("[Append Rows] rows inserted: " + result.getInserted_row_count() + ".");
        return result;
    }

    // https://api.seatable.io/reference/delete-rows
    public DeleteRowsResult deleteRows(DeleteRowsParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/batch-delete-rows/";
        DeleteRowsResult result = doDelete(url, param, DeleteRowsResult.class);
        log.info("[Delete Rows] rows deleted: " + result.getDeleted_rows() + ".");
        return result;
    }

    // Links

    // https://api.seatable.io/reference/create-row-link
    public CreateRowLinkResult createRowLink(CreateRowLinkParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/links/";
        CreateRowLinkResult result = doPost(url, param, CreateRowLinkResult.class);
        log.info("[Create Row Link] success: " + result.getSuccess() + ".");
        return result;
    }

    // https://api.seatable.io/reference/put_dtable-server-api-v1-dtables-base-uuid-batch-update-links
    public CreateRowLinksBatchResult createRowLinksBatch(CreateRowLinksBatchParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/batch-update-links/";
        CreateRowLinksBatchResult result = doPut(url, param, CreateRowLinksBatchResult.class);
        log.info("[Create Row Links (Batch)] success: " + result.getSuccess() + ".");
        return result;
    }

    // Tables

    // https://api.seatable.io/reference/create-new-table
    private CreateNewTableResult createNewTable_NAO_FUNCIONA_100_PORCENTO(CreateNewTableParam param) throws IOException {
        // Está de acordo com a documentação, mas não funciona (nem usando a interface web do site deles).
        // Exemplos: 1. criar campos do tipo número com column_data preenchido; 2. criar colunas do tipo link (que exigem o column_data preenchido).
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/tables/";
        CreateNewTableResult result = doPost(url, param, CreateNewTableResult.class);
        log.info("[Create New Table] name: " + result.getName() + ".");
        return result;
    }
    public CreateNewTableResult createNewTable(CreateNewTableParam param) throws IOException {
        // Gambiarra necessária porque o SeaTable não funciona em certos casos. Ver comentário em createNewTable_NAO_FUNCIONA_100_PORCENTO.

        // Cria a tabela só com a primeira coluna, porque o SeaTable exige.
        CreateNewTableParam paramFirst = new CreateNewTableParam();
        paramFirst.setTable_name(param.getTable_name());
        paramFirst.setColumns(new ArrayList<>());
        ColumnDef columnDefFirst = param.getColumns().iterator().next();
        paramFirst.getColumns().add(columnDefFirst);
        CreateNewTableResult result = this.createNewTable_NAO_FUNCIONA_100_PORCENTO(paramFirst);

        // Cria as outras colunas.
        for (ColumnDef columnDef : param.getColumns()) {
            if (columnDef == columnDefFirst) {
                continue;
            }
            InsertColumnParam icParam = new InsertColumnParam(param.getTable_name(), columnDef);
            InsertColumnResult icResult = insertColumn(icParam);
            result.getColumns().add(icResult);
        }

        return result;
    }

    // https://api.seatable.io/reference/delete-table
    public DeleteTableResult deleteTable(DeleteTableParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/tables/";
        DeleteTableResult result = doDelete(url, param, DeleteTableResult.class);
        log.info("[Delete Table] success: " + result.getSuccess() + ".");
        return result;
    }

    // Columns

    // https://api.seatable.io/reference/insert-column
    public InsertColumnResult insertColumn(InsertColumnParam param) throws IOException {
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/columns/";
        InsertColumnResult result = doPost(url, param, InsertColumnResult.class);
        log.info("[Insert Column] name: " + result.getName() + ".");
        return result;
    }
}
