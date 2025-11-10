package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // 1️⃣ 엔티티 선언하기
@AllArgsConstructor // 5️⃣ 생성자 생성을 이 어노테이션으로 간결하게
@NoArgsConstructor // 2-5 셀프체크 기본 생성자 추가하기
@ToString // 6️⃣ toString() 메서드를 간결하게 하기 위한 어노테이션
@Getter

public class Member {
  
  @Id // 2️⃣ 엔티티의 대표값 지정하기
  @GeneratedValue // 3️⃣ 자동 생성 기능 추가 (MySQL의 AUTO_INCREMENT와 비슷)
  private Long id; // 기본키(PK) 역할을 하는 필드 선언
  
  @Column // 4️⃣ 필드 선언하기
  private String email;
  @Column
  private String password;

  // 5️⃣ CTRL + . -> Generate Constructor 클릭하여 필드 3개 모두 선택하여 생성자 생성하기
  // public Member(Long id, String email, String password) {
  //   this.id = id;
  //   this.email = email;
  //   this.password = password;
  // }

  // 6️⃣ toString() 메서드 추가하기
  // @Override
  // public String toString(){
  //   return "Member [id=" + id + ", email=" + email + ", password=" + password + "]";
  // }
  
}