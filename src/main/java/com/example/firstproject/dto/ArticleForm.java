package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

public class ArticleForm {

  // 1️⃣ 필드 선언하기
  private String title; // 제목을 받을 필드
  private String content; // 내용을 받을 필드

  // 2️⃣ 생성자 추가하기
  public ArticleForm(String title, String content) {
    this.title = title;
    this.content = content;
  }

  // 3️⃣ 데이터를 잘 받았는지 확인할 toString() 메서드 추가
  @Override
  public String toString(){
    return "ArticleForm{"
        + "title='" + title + '\''   // title 값 출력
        + ", content='" + content + '\''   // content 값 출력
        + '}';
  }

  // 4️⃣ toEntity() 메서드 자동 코드 생성으로 생성한것
  public Article toEntity() {
    return new Article(null, title, content);
  }

}
