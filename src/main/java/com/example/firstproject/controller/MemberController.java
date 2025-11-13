package com.example.firstproject.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;

// import ch.qos.logback.core.model.Model;
// import org.springframework.ui.Model; 이 프레임워크를 임포트 해야 아래 model.addAttribute의 에러가 해제됨
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
      return "members/new";
  }
  // 2️⃣ Get, Post 매핑 해주기

  @PostMapping("/join")

  // 3️⃣ 디버깅을 위한 매개변수를 넣어줘야 하는데 DTO를 넣어준다
  // 엔티티로 해봤자 어차피 DB 저장 전이어서 id 값이 +1 되지 않아 null 로 전달되고
  // 이후에 saved.toString() 를 해서 정상적으로 저장되는지 확인하는 절차를 거쳐야 하는 것 같다.

  public String join(MemberForm memberForm) {
    
    // 4️⃣ DTO에 폼 데이터가 잘 담겼는지 확인하기
    // System.out.println(memberForm.toString());
    // ✅ 롬복 코드로 즉 로깅 사용하기
    log.info(memberForm.toString());

    // 5️⃣ DTO를 엔티티로 변환
    Member member = memberForm.toEntity();
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

    return "redirect:/members/" + saved.getId();
  }
  
  // 보완 포인트:

  // URL 네이밍을 “articles” 대신 “members”로 바꾸면 더 일관됩니다.
  // (/articles/signup-result → /members/signup-result)

  // 마지막에 return ""; 대신, 결과 페이지를 반환하거나 redirect를 쓰는 게 좋습니다.


  // ✅ 개별 게시글 보기
  @GetMapping("/members/{id}")
  public String show(@PathVariable Long id, Model model) { // 2️⃣ 가져온 데이터를 모델에 등록하기
    log.info("id = " + id);

    // 1️⃣ id를 조회해 DB에서 해당 데이터 가져오기
    Member member = memberRepository.findById(id).orElse(null);

    // 2️⃣ 가져온 데이터를 모델에 등록하기
    model.addAttribute("member", member);

    // 3️⃣ 조회한 데이터를 사용자에게 보여 주기 위한 뷰 페이지 만들고 반환하기
      return "members/show";
  }
  
  // ✅ 전체 게시글 보기
  @GetMapping("/members")
  public String index(Model model) {
    // 1️⃣ 모든 데이터 가져오기 -> 실습 했던 대로 ArrayList<> 를 이용하기
    ArrayList<Member> members = memberRepository.findAll();
    // 엔티티에 기본 생성자 어노테이션 추가하고 리포지터리로 가서 findAll() 오버라이드 해주기
    
    // 2️⃣ 모델에 데이터 등록하기
    model.addAttribute("members", members);

    // 3️⃣ 뷰 페이지 설정하기  
      return "members/index";
  }

  // ✅ 게시글 수정을 위해서 각 게시글의 정보를 그대로 수정페이지로 넘기는 역할
  @GetMapping("/members/{id}/edit")
  public String edit(@PathVariable Long id, Model model) {

    Member memberEntity = memberRepository.findById(id).orElse(null);

    model.addAttribute("members_edit_page", memberEntity);
    
    return "members/edit";
  }
  
  // ✅ 게시글 수정
  @PostMapping("/members/update")
  public String update(MemberForm form) { // DTO 매개변수로 넣기
      log.info(form.toString());

      Member memberEntity = form.toEntity(); // DTO를 엔티티로 변환하기
      log.info(memberEntity.toString());

      Member target = memberRepository.findById(memberEntity.getId()).orElse(null);

      if (target != null) {
        memberRepository.save(memberEntity);
      }
      
      return "redirect:/members/" + memberEntity.getId();
  }

  // ✅ 게시글 삭제
  @GetMapping("/members/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes rttr) {
    log.info("delete 메소드 호출");

    Member target = memberRepository.findById(id).orElse(null);
    log.info(target.toString());

    if(target != null){
      memberRepository.delete(target);
      rttr.addFlashAttribute("msg", "삭제 성공!");
    }

      return "redirect:/members";
  }
  
  
}
