// 회사의 도메인(url)을 의미
// 구분이 안된다 -> 다른 모듈(라이브러리)와 구분을 하기 위해 본인 회사, 소속, ...
// kr.co.programmers ...
package org.example;

import io.github.cdimascio.dotenv.Dotenv;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        Dotenv dotenv = Dotenv.load(); // .env를 읽어들여와서 get으로 쓸 수 있게 만들어줌
        String CLIENT_ID = dotenv.get("NAVER_CLIENT_ID");
        String CLIENT_SECRET = dotenv.get("NAVER_CLIENT_SECRET");
        HttpClient httpClient = HttpClient.newHttpClient();
        // https://developers.naver.com/docs/serviceapi/search/blog/blog.md
        String baseUrl = "https://openapi.naver.com/v1/search/blog.json";
        String query = "골반통신";
        int display = 100;
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        /*
        curl  "https://openapi.naver.com/v1/search/blog.xml?query=%EB%A6%AC%EB%B7%B0&display=10&start=1&sort=sim" \
            -H "X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}" \
            -H "X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}" -v
         */
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("%s?query=%s&display=%d".formatted(baseUrl, query, 100)))
                .header("X-Naver-Client-Id", CLIENT_ID)
                .header("X-Naver-Client-Secret", CLIENT_SECRET)
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        String body = httpResponse.body();
        ObjectMapper objectMapper = new ObjectMapper(); // 문자열 -> 클래스/레코드 객체
        BlogResponse blogResponse = objectMapper.readValue(body, BlogResponse.class);
        System.out.println(blogResponse);
    }
}
