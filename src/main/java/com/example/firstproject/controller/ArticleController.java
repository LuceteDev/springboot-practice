package com.example.firstproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j // 로깅 기능을 위한 어노테이션 추가
@Controller
public class ArticleController {
  @Autowired // 3️⃣ 스프링부트가 미리 생성해 놓은 리파지토리 객체 주입!
  private ArticleRepository articleRepository;


  @GetMapping("articles/new")
  public String newArticleForm(){
    return "articles/new";
  }

  @PostMapping("/articles/create")
  public String createArticle(ArticleForm form) {  
    // System.out.println("프론트쪽" + form.toString());
    log.info(form.toString());

    // 1️⃣ DTO를 엔티티로 변환
    Article article = form.toEntity();
    // 2️⃣ 리파지토리로 엔티티를 DB에 저장
    // System.out.println("DTO" + article.toString()); // DTO가 엔티티로 잘 변환되는지 출력!
    log.info(article.toString());

    Article saved = articleRepository.save(article);
    // System.out.println("DB" + saved.toString()); // article이 DB에 잘 저장되는지 출력!
    log.info(saved.toString());
    
    return "";
  }
  
  // 2025.11.08 추가 코드
  @GetMapping("/articles/{id}") // 컨트롤러에서는 변수를 사용할땐 {} 는 하나만 사용함!
  public String show(@PathVariable Long id) { // 매개변수로 id 받아오기
    // ✅ Pathvariable은 URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션
    log.info("id = " + id);
    //‼️ 4장에서 배운 로깅 기능을 이용해서 디버깅하기

    // 1. id를 조회해 DB에서 해당 데이터 가져오기
    // 2. 가져온 데이터를 모델에 등록하기
    // 3. 조회한 데이터를 사용자에게 보여 주기 위한 뷰 페이지 만들고 반환하기
  
    
    // id를 조회해 데이터 가져오기
    // Article articleEntity = articleRepository.findById(id);
    // ✅ articleRepository가 findBy(id)로 찾은 값을 반환할 때 
    // ✅ 반환형이 Article이 아니라서 오류가 발생 한 것을 Optional<> 로 반환형을 맞춰줘서 해결!
    
    // Optional<Article> articleEntity = articleRepository.findById(id); // 이번 실습에선 사용하지 않음
    Article articleEntity = articleRepository.findById(id).orElse(null);
      return "";
  }
  

}
