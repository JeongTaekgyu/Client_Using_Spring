package com.example.client.controller;

import com.example.client.dto.UserResponse;
import com.example.client.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ApiController {

    //@Autowired  // @Autowired는 옛날 방식이고 요즘엔 생성자 주입방식을 사용한다.
    private final RestTemplateService restTemplateService;

    // default 생성자 만들어서 final에 대해서 스프링에서 자동으로 주입을 해준다
    public ApiController(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    @GetMapping("/hello")
    public UserResponse getHello(){
        return restTemplateService.hello();
        // 컨트롤러로(get으로) 요청이 들어오면 restTemplateService 통해서 서버로 호출해서 응답을 받아서 response로 내린다
    }
}
