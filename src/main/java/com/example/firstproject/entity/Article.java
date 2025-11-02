package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity // 1️⃣ 엔티티 선언
public class Article {

  @Id // 2️⃣ 엔티티의 대푯값 지정
  @GeneratedValue // 3️⃣ 자동 생성 기능 추가 (숫자가 자동으로 매겨짐!)
  private Long id;

  @Column // 4️⃣ title 필드 선언, DB 테이블의 title 열과 연결됨
  private String title;
  @Column // 4️⃣ content 필드 선언, DB 테이블의 content 열과 연결됨
  private String content;

  // 5️⃣ Article 객체의 생성 및 초기화를 위해 생성자 생성하기
  public Article(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  // 6️⃣ toString() 메서드 추가하기
  @Override
  public String toString() {
    return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
  }

  
}