package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;

import lombok.AllArgsConstructor;
import lombok.ToString;


@AllArgsConstructor 
// 클래스 안쪽의 모든 필드, 즉 title, content를 매개변수로 하는 생성자가 자동으로 만들어진다고 함
// 생성자 4줄이 1줄로 간소화 된 것!
@ToString
// ToString() 메서드가 간결해짐!

public class ArticleForm {

  // 1️⃣ 필드 선언하기
  private String title; // 제목을 받을 필드
  private String content; // 내용을 받을 필드

  // 2️⃣ 생성자 추가하기 -> 롬복으로 간소화 실습하기
  // public ArticleForm(String title, String content) {
  //   this.title = title;
  //   this.content = content;
  // }

  // 3️⃣ 데이터를 잘 받았는지 확인할 toString() 메서드 추가 -> 롬복으로 간소화 실습하기
  // @Override
  // public String toString(){
  //   return "ArticleForm{"
  //       + "title='" + title + '\''   // title 값 출력
  //       + ", content='" + content + '\''   // content 값 출력
  //       + '}';
  // }

  // 4️⃣ toEntity() 메서드 자동 코드 생성으로 생성한것
  public Article toEntity() {
    return new Article(null, title, content);
  }

}
