package com.example.client.service;

import com.example.client.dto.UserRequest;
import com.example.client.dto.UserResponse;
import jdk.swing.interop.SwingInterOpUtils;
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

    // post로 보내기
    public UserResponse post(){
        // http://localhost:9090/api/server/user/{userId}/name/{userName}
        // user를 등록시키는 것을 만들거다
        // 1. 주소 만들고
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand("100", "taek")  // expand는 , 로 구분해라 안에 있는 데이터는 {userId}, {userName} 이다
                .toUri();
        System.out.println(uri);
        // 보내고싶은 데이터
        //post 이기 때문에 http body 가 있어야한다.
        // 2. 내가 보내고 싶은 request body 데이터를 (http body) -> object -> object mapper -> json ->rest template -> http body json
        UserRequest req = new UserRequest();
        req.setName("taek");
        req.setAge(10);

        // rest template으로 쏘기만 하면됨 , 서버에서 받아주면 됨
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri, req, UserResponse.class);    // 응답을 뭘로 받을지 지정한다
                                                           // 해당 주소에(uri) request body를 만들어서 응답(req)을 이걸로(UserResponse.class) 받을 거다
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());

        return response.getBody();
    }
}
