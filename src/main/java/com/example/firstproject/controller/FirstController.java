package com.example.firstproject.controller;

// 아래 타입을 임포트 해야함!
import org.springframework.stereotype.Controller;

// URL을 매핑하기 위해 필요한 get 매핑 임포트 해주기
import org.springframework.web.bind.annotation.GetMapping;

// Model 클래스 패키지 임포트 하기
import org.springframework.ui.Model;

// 컨트롤러 선언하기
@Controller
public class FirstController {

  @GetMapping("/hi") // URL 요청 접수 부분
  public String niceToMeetYou(Model model){ // model 객체 받아오기
    
    // 변수값을 '변수명' 이라는 이름으로 추가하기
    model.addAttribute("username", "LUCETEDEV");
    
    return "greetings"; // greetings.mustache 파일 반환
  } 
  
}
