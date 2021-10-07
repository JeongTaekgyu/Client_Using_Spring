package com.example.client.service;

import com.example.client.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {
    String temp;
    // http://localhost/api/server/hello
    // response
    public UserResponse hello(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/hello")
                .queryParam("name","mark")  // 주소뒤에 queryParameter가 들어감, ex) http://localhost:9090/api/server/hello?name=mark&age=21
                .queryParam("age",21)
                .encode()
                .build()
                .toUri();

        System.out.println(uri.toString());
        RestTemplate restTemplate = new RestTemplate(); // RestTemplate() 뭐하는 건지 알자

        /*String result = restTemplate.getForObject(uri, String.class);   // getForObject가 실행되는 순간이 client에서 http서버로 붙는 순간이다
                                // Object 형태로 가져오겠다.
        return result;*/
        // 상세 정보는 ResponseEntity 추천
        ResponseEntity<UserResponse> result = restTemplate.getForEntity(uri, UserResponse.class);
                            //getForEntity은 가져오겠다는 get이 아니라 http의 get 메서드이고 Entity로 가져오겠다는 뜻이다,  클라이언트에서 getForEntity를 사용해서 get에 대한 메서드를 처리함
        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());

        return result.getBody();
    }
}
