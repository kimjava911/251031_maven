package org.example.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.BlogResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SupabaseService {
    private final String SUPABASE_URL;
    private final String SUPABASE_KEY;
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public SupabaseService() {
        Dotenv dotenv = Dotenv.load();
        SUPABASE_URL = dotenv.get("SUPABASE_URL");
        SUPABASE_KEY = dotenv.get("SUPABASE_KEY");
    }

    public void saveBlogItem(BlogResponse.BlogItem item) {
        /*
        curl -X POST 'https://hrpwhtxwburlzjtgaqqa.supabase.co/rest/v1/blog' \
            -H "apikey: SUPABASE_KEY" \
            -H "Authorization: Bearer SUPABASE_KEY" \
            -H "Content-Type: application/json" \
            -H "Prefer: return=minimal" \
            -d '{ "some_column": "someValue", "other_column": "otherValue" }'
         */
        String payload = mapper.writeValueAsString(item);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SUPABASE_URL + "/rest/v1/blog"))
                // .GET
                .POST(HttpRequest.BodyPublishers.ofString(
                    payload
                ))
                .header("apikey", SUPABASE_KEY)
                .header("Authorization", "Bearer %s".formatted(SUPABASE_KEY))
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .build();
        try {
            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString()
            ); // JSON -> 문자열로 직렬화된 형태로 받아줌
            String body = response.body();
            System.out.println(response.statusCode());
            if (!body.isBlank()) System.out.println(body);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
