package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;


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
    System.out.println("프론트쪽" + form.toString());
    // 1️⃣ DTO를 엔티티로 변환
    Article article = form.toEntity();
    // 2️⃣ 리파지토리로 엔티티를 DB에 저장
    System.out.println("DTO" + article.toString()); // DTO가 엔티티로 잘 변환되는지 출력!

    Article saved = articleRepository.save(article);
    System.out.println("DB" + saved.toString()); // article이 DB에 잘 저장되는지 출력!
    return "";
  }

}
