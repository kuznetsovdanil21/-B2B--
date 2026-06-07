package ru.course.b2b.service;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.course.b2b.model.CompanyInfo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DaDataService {

    private static final String API_KEY =
            "8d9e5bb9335179bb522fcaf973fda906accca402";

    private static final String URL =
            "https://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party";

    public CompanyInfo findCompanyByInn(String inn)
            throws IOException, InterruptedException {

        JSONObject requestBody =
                new JSONObject();

        requestBody.put(
                "query",
                inn
        );

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(URL))
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .header(
                                "Accept",
                                "application/json"
                        )
                        .header(
                                "Authorization",
                                "Token " + API_KEY
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofString(
                                        requestBody.toString()
                                )
                        )
                        .build();

        HttpClient client =
                HttpClient.newHttpClient();

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        JSONObject json =
                new JSONObject(
                        response.body()
                );

        JSONArray suggestions =
                json.getJSONArray(
                        "suggestions"
                );

        if (suggestions.isEmpty()) {
            return null;
        }

        JSONObject data =
                suggestions
                        .getJSONObject(0)
                        .getJSONObject("data");

        return new CompanyInfo(
                data.optString("name", ""),
                data.optString("inn", ""),
                data.optString("address", ""),
                data.optString("okved", "")
        );
    }
}