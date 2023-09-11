package zcla71.biblioteca.dao;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import zcla71.biblioteca.model.seatable.BaseToken;

public class SeaTableDao {
    // https://api.seatable.io/reference

    private String apiToken = null;
    private BaseToken baseToken = null;

    public SeaTableDao(String apiToken) throws IOException {
        this.apiToken = apiToken;
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
}
