// 회사의 도메인(url)을 의미
// 구분이 안된다 -> 다른 모듈(라이브러리)와 구분을 하기 위해 본인 회사, 소속, ...
// kr.co.programmers ...
package org.example;

import org.example.service.GeminiService;

import java.io.IOException;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
//        BlogSearchService blogSearchService = new BlogSearchService();
//        List<BlogResponse.BlogItem> items = blogSearchService.search("코쿠시보", 10);
//        items.stream().forEach(System.out::println);
//        SupabaseService supabaseService = new SupabaseService();
//        supabaseService.saveBlogItem(items.get(0));
//        for (BlogResponse.BlogItem item : items) {
//            supabaseService.saveBlogItem(item);
//            // insert ~ : 2번 => bulk
//        }
        GeminiService geminiService = new GeminiService();
        String answer = geminiService.chat("자바를 만든 사람의 이름은?");
        System.out.println(answer);
    }
}
