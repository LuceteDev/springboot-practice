package com.example.firstproject.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// 1️⃣ 어노테이션 선언
@Entity // 해당 클래스가 엔티티임을 선언, 클래스 필드를 바탕으로 DB에 테이블 생성
@Getter // 각 필드 값을 조회할 수 있는 Getter 메서드 자동 생성
@ToString // 모든 필드를 출력할 수 있는 toString 메서드 자동 생성
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor // 매개변수가 아예 없는 기본 생성자 자동 생성

public class Comment {
  // 2️⃣ 필드 선언
  // 3️⃣ id필드에 @Id 붙여서 대표키 선언하기
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 4️⃣ @GeneratedValue 붙여서 autoincrement 역할하도록 선언하기, (데이터를 생성할 때마다 +1 되도록 설정)
  private Long id; // 대표키

  @ManyToOne // 5️⃣ Comment 엔티티와 Article 엔티티를 n:1 관계로 설정
  @JoinColumn(name = "article_id") // 6️⃣ @JoinColumn(name = "외래키 이름") 으로 외래키 생성, Article 엔티티의 기본키(id)와 매핑
  private Article article; // 해당 댓글의 부모 게시글

  @Column // 7️⃣ 해당 필드를 테이블의 속성으로 매핑
  private String nickname; // 댓글 단 사람
  @Column // 7️⃣ 해당 필드를 테이블의 속성으로 매핑
  private String body; // 댓글 본문

  // 〰️〰️〰️〰️〰️〰️〰️〰️ 8️⃣ Comment 엔티티 정상 작동 여부 검토하기 〰️〰️〰️〰️〰️〰️〰️〰️ //
  
}
