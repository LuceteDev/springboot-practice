package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity // 1️⃣ - @Entity 선언 확인
@AllArgsConstructor // 4️⃣ - 기본 생성자 @NoArgsConstructor, 모든 필드 생성자 @AllArgsConstructor 확인  
@NoArgsConstructor  // 4️⃣ - 기본 생성자 @NoArgsConstructor, 모든 필드 생성자 @AllArgsConstructor 확인  
@Getter // 5️⃣ - @Getter, @ToString으로 로그 출력 가능
@ToString // 5️⃣ - @Getter, @ToString으로 로그 출력 가능

public class Coffee {
  @Id // 2️⃣ - @Id, @GeneratedValue로 PK 자동 생성 설정  
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 2️⃣ - @Id, @GeneratedValue로 PK 자동 생성 설정  
  private Long id;
  
  @Column // 3️⃣ - 필드 선언: name(String), price(int)
  private String name;
  @Column // 3️⃣ - 필드 선언: name(String), price(int)
  private String price;


  // 6️⃣ - UPDATE 메서드때 사용될 patch 메서드 정의 (PATCH(update 쿼리) 컨트롤러 정의할 때 생성)
  public void patch(Coffee coffee) {
    if (coffee.name != null) { // 즉 갱신할 값이 들어오면 
    this.name = coffee.name; // 갱신하는 것!
    }
    if (coffee.price != null) {
      this.price = coffee.price;
    }
  }

}
