package org.example.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.BlogResponse;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BlogSearchService {
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String baseUrl = "https://openapi.naver.com/v1/search/blog.json";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public BlogSearchService() {
        Dotenv dotenv = Dotenv.load();
        CLIENT_ID = dotenv.get("NAVER_CLIENT_ID");
        CLIENT_SECRET = dotenv.get("NAVER_CLIENT_SECRET");
    }

    public List<BlogResponse.BlogItem> search(String query, int display) {
        // https://developers.naver.com/docs/serviceapi/search/blog/blog.md
        /*
        curl  "https://openapi.naver.com/v1/search/blog.xml?query=%EB%A6%AC%EB%B7%B0&display=10&start=1&sort=sim" \
            -H "X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}" \
            -H "X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}" -v
         */
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("%s?query=%s&display=%d".formatted(baseUrl, encoded, display)))
                .header("X-Naver-Client-Id", CLIENT_ID)
                .header("X-Naver-Client-Secret", CLIENT_SECRET)
                .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (httpResponse.statusCode() != 200) {
            throw new RuntimeException("%d : 정상 응답이 아님".formatted(httpResponse.statusCode()));
        }
        String body = httpResponse.body();
        BlogResponse blogResponse = objectMapper.readValue(body, BlogResponse.class);
        if (blogResponse == null) {
            throw new RuntimeException("결과가 없습니다!");
        }
        return blogResponse.items();
    }
}
