package com.example.firstproject.controller;

// @Controller 애너테이션을 쓰려면 이 클래스를 가져와야 함.
import org.springframework.stereotype.Controller;

// @GetMapping 애너테이션을 사용하기 위한 import
import org.springframework.web.bind.annotation.GetMapping;


// Model은 컨트롤러가 뷰(템플릿)로 데이터를 넘겨줄 때 사용하는 객체 타입
// 템플릿에서 ${...} 또는 {{...}}처럼 치환될 값을 여기에 담아 보냄!!
import org.springframework.ui.Model;

// 이 클래스가 웹 요청을 처리하는 컨트롤러(Controller) 라는 것을 스프링에게 알려주는 표시(애너테이션)
@Controller

public class ByeController {
  
  // 2️⃣ 브라우저가 GET /bye 요청을 보낼 때 이 아래의 메서드를 실행하라는 뜻
  @GetMapping("/bye")
  public String seeYouNext(Model model){

    model.addAttribute("nickname", "JWB");
    return "goodbye"; // goodbye.mustache 반환
  }

}