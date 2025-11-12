package com.example.firstproject.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController // REST API용 컨트롤러
public class FirstApiController {

  @GetMapping("/api/hello") // 1️⃣ URL 요청 접수
  public String hello() { // 2️⃣ hello world! 문자열 반환
      return "hello world!";
  }
  
}
