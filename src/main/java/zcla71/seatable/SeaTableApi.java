package zcla71.seatable;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import zcla71.seatable.model.BaseToken;
import zcla71.seatable.model.ddl.ColumnDef;
import zcla71.seatable.model.ddl.ColumnDefLink;
import zcla71.seatable.model.ddl.ColumnDefNumber;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.CreateRowLinkParam;
import zcla71.seatable.model.param.DeleteRowsParam;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.param.InsertColumnParam;
import zcla71.seatable.model.param.InsertColumnParamData;
import zcla71.seatable.model.param.ListRowsParam;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.CreateRowLinkResult;
import zcla71.seatable.model.result.DeleteRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.InsertColumnResult;
import zcla71.seatable.model.result.ListRowsResult;
import zcla71.seatable.model.result.AddRowResult;

// https://api.seatable.io/reference
public class SeaTableApi {
    private BaseToken baseToken = null;
    
    public SeaTableApi(String apiToken) throws IOException {
        baseToken = generateBaseToken(apiToken);
    }

    // Authentication

    // https://api.seatable.io/reference/get-base-token-with-api-token
    private BaseToken generateBaseToken(String apiToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/api/v2.1/dtable/app-access-token/")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("authorization", "Bearer " + apiToken)
            .build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, BaseToken.class);
    }

    // Base Info

    // https://api.seatable.io/reference/get-metadata
    public Metadata getMetadata() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/metadata/")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, Metadata.class);
    }

    // Rows

    // https://api.seatable.io/reference/list-rows
    public ListRowsResult listRows(ListRowsParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/rows/" + param.getUrlParams();
        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, ListRowsResult.class);
    }

    // https://api.seatable.io/reference/add-row
    public AddRowResult addRow(AddRowParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/rows/")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, AddRowResult.class);
    }

    // https://api.seatable.io/reference/append-rows
    public AppendRowsResult appendRows(AppendRowsParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/batch-append-rows/")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, AppendRowsResult.class);
    }

    // https://api.seatable.io/reference/delete-rows
    public DeleteRowsResult deleteRows(DeleteRowsParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/batch-delete-rows/")
            .delete(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, DeleteRowsResult.class);
    }

    // Links

    // https://api.seatable.io/reference/create-row-link
    public CreateRowLinkResult createRowLink(CreateRowLinkParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/links/")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, CreateRowLinkResult.class);
    }

    // Tables

    // https://api.seatable.io/reference/create-new-table
    private CreateNewTableResult createNewTable_NAO_FUNCIONA_100_PORCENTO(CreateNewTableParam param) throws IOException {
        // Está de acordo com a documentação, mas não funciona (nem usando a interface web do site deles).
        // Exemplos: 1. criar campos do tipo número com column_data preenchido; 2. criar colunas do tipo link (que exigem o column_data preenchido).
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/tables/")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, CreateNewTableResult.class);
    }
    public CreateNewTableResult createNewTable(CreateNewTableParam param) throws IOException {
        // Gambiarra necessária porque o SeaTable não funciona em certos casos. Ver comentário em createNewTable_NAO_FUNCIONA_100_PORCENTO.

        // Cria a tabela com uma coluna dummy. Nota: o SeaTable não permite que ela seja removida depois.
        CreateNewTableParam paramDummy = new CreateNewTableParam();
        paramDummy.setTable_name(param.getTable_name());
        paramDummy.setColumns(new ArrayList<>());
        ColumnDef columnDefDummy = new ColumnDefNumber();
        columnDefDummy.setColumn_name("dummy");
        paramDummy.getColumns().add(columnDefDummy);
        CreateNewTableResult result = this.createNewTable_NAO_FUNCIONA_100_PORCENTO(paramDummy);

        // Cria as outras colunas.
        for (ColumnDef columnDef : param.getColumns()) {
            InsertColumnParam icParam = new InsertColumnParam();
            icParam.setTable_name(param.getTable_name());
            icParam.setColumn_type(columnDef.getColumn_type());
            icParam.setColumn_name(columnDef.getColumn_name());
            if (columnDef instanceof ColumnDefNumber cdn && cdn.getColumn_data() != null) {
                icParam.setColumn_data(new InsertColumnParamData());
                icParam.getColumn_data().put("format", cdn.getColumn_data().getFormat());
                icParam.getColumn_data().put("decimal", cdn.getColumn_data().getDecimal());
                icParam.getColumn_data().put("thousands", cdn.getColumn_data().getThousands());
            }
            if (columnDef instanceof ColumnDefLink cdl && cdl.getColumn_data() != null) {
                icParam.setColumn_data(new InsertColumnParamData());
                icParam.getColumn_data().put("table", cdl.getColumn_data().getTable());
                icParam.getColumn_data().put("other_table", cdl.getColumn_data().getOther_table());
            }
            InsertColumnResult icResult = insertColumn(icParam);
            result.getColumns().add(icResult);
        }

        return result;
    }

    // https://api.seatable.io/reference/delete-table
    public DeleteTableResult deleteTable(DeleteTableParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/tables/")
            .delete(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, DeleteTableResult.class);
    }

    // Columns

    // https://api.seatable.io/reference/insert-column
    private InsertColumnResult insertColumn(InsertColumnParam param) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(param);
        RequestBody body = RequestBody.create(mediaType, strBody);
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/dtable-server/api/v1/dtables/" + baseToken.getDtable_uuid() + "/columns/")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Bearer " + baseToken.getAccess_token())
            .build();
        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new RuntimeException(response.message());
        }
        String responseBody = response.body().string();
        return objectMapper.readValue(responseBody, InsertColumnResult.class);
    }
}
