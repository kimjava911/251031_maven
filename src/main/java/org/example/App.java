// 회사의 도메인(url)을 의미
// 구분이 안된다 -> 다른 모듈(라이브러리)와 구분을 하기 위해 본인 회사, 소속, ...
// kr.co.programmers ...
package org.example;

import io.github.cdimascio.dotenv.Dotenv;

public class App
{
    public static void main( String[] args )
    {
        Dotenv dotenv = Dotenv.load(); // .env를 읽어들여와서 get으로 쓸 수 있게 만들어줌
        System.out.println(dotenv.get("HELLO"));
    }
}
