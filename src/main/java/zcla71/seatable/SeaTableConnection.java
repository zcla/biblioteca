package zcla71.seatable;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import zcla71.seatable.model.BaseToken;
import zcla71.seatable.model.ddl.RowDef;
import zcla71.seatable.model.ddl.RowsDef;
import zcla71.seatable.model.ddl.TableDef;
import zcla71.seatable.model.ddl.TableDeleteDef;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.metadata.Table;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.DeleteTableResult;
import zcla71.seatable.model.result.InsertRowResult;

public class SeaTableConnection {
    // https://api.seatable.io/reference

    private BaseToken baseToken = null;

    public SeaTableConnection(String apiToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://cloud.seatable.io/api/v2.1/dtable/app-access-token/")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("authorization", "Bearer " + apiToken)
            .build();
        Response response = client.newCall(request).execute();
        ObjectMapper objectMapper = new ObjectMapper();
        baseToken = objectMapper.readValue(response.body().string(), BaseToken.class);
    }

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
        return objectMapper.readValue(response.body().string(), Metadata.class);
    }

    public Table createTable(TableDef tableDef) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(tableDef);
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
        return objectMapper.readValue(responseBody, Table.class);
    }

    public DeleteTableResult deleteTable(TableDeleteDef tableDeleteDef) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(tableDeleteDef);
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

    public InsertRowResult insertRow(RowDef rowDef) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(rowDef);
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
        return objectMapper.readValue(responseBody, InsertRowResult.class);
    }

    public AppendRowsResult appendRows(RowsDef rowsDef) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String strBody = objectMapper.writeValueAsString(rowsDef);
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
}
