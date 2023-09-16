package zcla71.seatable;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import zcla71.seatable.model.BaseToken;
import zcla71.seatable.model.metadata.Metadata;
import zcla71.seatable.model.param.AppendRowsParam;
import zcla71.seatable.model.param.CreateNewTableParam;
import zcla71.seatable.model.param.AddRowParam;
import zcla71.seatable.model.param.DeleteTableParam;
import zcla71.seatable.model.result.AppendRowsResult;
import zcla71.seatable.model.result.CreateNewTableResult;
import zcla71.seatable.model.result.DeleteTableResult;
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
        return objectMapper.readValue(response.body().string(), BaseToken.class);
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
        return objectMapper.readValue(response.body().string(), Metadata.class);
    }

    // Rows

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

    // Tables

    // https://api.seatable.io/reference/create-new-table
    public CreateNewTableResult createNewTable(CreateNewTableParam param) throws IOException {
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
}
