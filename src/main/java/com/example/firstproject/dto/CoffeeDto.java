package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;

import lombok.AllArgsConstructor;
import lombok.ToString;


@AllArgsConstructor // 3️⃣ - @AllArgsConstructor, @ToString으로 테스트용 로그 가능
@ToString // 3️⃣ - @AllArgsConstructor, @ToString으로 테스트용 로그 가능

// - REST 요청/응답용 객체 ? 이거 무슨 의민지 모르겠음

public class CoffeeDto {
  
  // 1️⃣ - 필드 선언: name, price, + id까지(테스트용 더미 데이터와의 기본키 제약조건 위반 방지)
  private String name;
  private String price;
  private Long id;
  
  // 2️⃣ - toEntity() 메서드 구현: DTO → Entity 변환
  public Coffee toEntity(){
    return new Coffee(id, name, price);
  }
}
