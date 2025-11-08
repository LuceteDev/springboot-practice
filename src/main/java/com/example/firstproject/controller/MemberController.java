package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j // ✅ 2025.11.08 셀프체크 실습 코드 추가

// 1️⃣ 컨트롤러 선언하기
@Controller
public class MemberController {

  // 7️⃣ 필드 주입 대신 생성자 주입 (@RequiredArgsConstructor)을 추천 (나중에)
  @Autowired 
  private MemberRepository memberRepository;

  
  // 2️⃣ Get, Post 매핑 해주기
  @GetMapping("/signup")
  public String signUpPage() {
      return "articles/SignUp";
  }
  // 2️⃣ Get, Post 매핑 해주기

  @PostMapping("/articles/signup-result")

  // 3️⃣ 디버깅을 위한 매개변수를 넣어줘야 하는데 DTO를 넣어준다
  // 엔티티로 해봤자 어차피 DB 저장 전이어서 id 값이 +1 되지 않아 null 로 전달되고
  // 이후에 saved.toString() 를 해서 정상적으로 저장되는지 확인하는 절차를 거쳐야 하는 것 같다.

  public String signupResult(MemberForm form) {
    
    // 4️⃣ DTO에 폼 데이터가 잘 담겼는지 확인하기
    // System.out.println(form.toString());
    // ✅ 롬복 코드로 즉 로깅 사용하기
    log.info(form.toString());

    // 5️⃣ DTO를 엔티티로 변환
    Member member = form.toEntity();
    // 5️⃣ DTO가 엔티티로 잘 변환되는지 출력!  
    // System.out.println(member.toString()); 
    // ✅ 롬복 코드로 즉 로깅 사용하기
    log.info(member.toString());

    // 6️⃣ 리포지터리로 엔티티를 DB에 저장
    Member saved = memberRepository.save(member);
    // 6️⃣ Member이 DB에 잘 저장되는지 출력!
    // System.out.println("DB" + saved.toString()); 
    // ✅ 롬복 코드로 즉 로깅 사용하기
    log.info(saved.toString());

    return "";
  }
  
  // 보완 포인트:

  // URL 네이밍을 “articles” 대신 “members”로 바꾸면 더 일관됩니다.
  // (/articles/signup-result → /members/signup-result)

  // 마지막에 return ""; 대신, 결과 페이지를 반환하거나 redirect를 쓰는 게 좋습니다.
  
}
