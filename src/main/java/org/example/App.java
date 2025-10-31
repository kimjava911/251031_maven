// 회사의 도메인(url)을 의미
// 구분이 안된다 -> 다른 모듈(라이브러리)와 구분을 하기 위해 본인 회사, 소속, ...
// kr.co.programmers ...
package org.example;

import org.example.model.BlogResponse;
import org.example.service.BlogSearchService;

import java.io.IOException;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        BlogSearchService blogSearchService = new BlogSearchService();
        List<BlogResponse.BlogItem> items = blogSearchService.search("레제편", 50);
        items.stream().forEach(System.out::println);
    }
}
