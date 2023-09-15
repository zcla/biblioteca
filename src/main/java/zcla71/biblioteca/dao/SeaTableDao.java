package zcla71.biblioteca.dao;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import zcla71.biblioteca.model.seatable.BaseToken;
import zcla71.biblioteca.model.seatable.ddl.TableDef;
import zcla71.biblioteca.model.seatable.metadata.Metadata;
import zcla71.biblioteca.model.seatable.metadata.Table;

public class SeaTableDao {
    // https://api.seatable.io/reference

    private BaseToken baseToken = null;

    public SeaTableDao(String apiToken) throws IOException {
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
}
