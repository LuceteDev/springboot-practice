package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가 어노테이션
@ToString
@Getter // 컨트롤러에서 saved.getId()를 사용하기 위해 추가한 어노테이션
// @Table(name="Article_Table") // 생성 테이블명을 명시해보려고 추가한 어노테이션
@Entity // 1️⃣ 엔티티 선언
public class Article {

  @Id // 2️⃣ 엔티티의 대푯값 지정
  // 👇 3️⃣ 자동 생성 기능 추가 (숫자가 자동으로 매겨짐!)
  @GeneratedValue(strategy = GenerationType.IDENTITY) // (strategy = GenerationType.IDENTITY) -> DB가 id 자동 생성하도록 설정! [더미 코드가 있을 경우 기본키 위반이 출력되기 때문!]
  private Long id;

  @Column // 4️⃣ title 필드 선언, DB 테이블의 title 열과 연결됨
  private String title;
  @Column // 4️⃣ content 필드 선언, DB 테이블의 content 열과 연결됨
  private String content;

  // REST API PATCH 실습중에 일부만 수정할 경우, 변경하지 않은 값은 null이 되니까, 이를 방지해야함!
  // Article의 RestController에서 target.patch(article); 이렇게 적어서 자동 생성한 메서드임!

  public void patch(Article article) { // 수정할 내용이 있는 경우에만 동작하면 되니까
    if (article.title != null) { // 즉 갱신할 값이 들어오면 
      this.title = article.title; // 갱신하는 것!
    }
    if (article.content != null) {
      this.content = article.content;
    }
  }


  // 5️⃣ Article 객체의 생성 및 초기화를 위해 생성자 생성하기 -> 롬복 추가로 제거
  // public Article(Long id, String title, String content) {
  //   this.id = id;
  //   this.title = title;
  //   this.content = content;
  // }

  // 6️⃣ toString() 메서드 추가하기 -> 롬복 추가로 제거
  // @Override
  // public String toString() {
  //   return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
  // }

}