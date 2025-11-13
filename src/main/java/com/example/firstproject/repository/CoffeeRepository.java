package com.example.firstproject.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.firstproject.entity.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee, Long>{ // 1️⃣ - extends CrudRepository<Coffee, Long> 상속
  
  // 2️⃣ - 필요 시 @Override ArrayList<Coffee> findAll()로 반환 타입 맞춤
  // 2️⃣ - 오버라이드 ❌ 오버라이딩✅ Iterable -> ArrayList 로 수정한 것!
  @Override
  ArrayList<Coffee> findAll();

  // 3️⃣ - H2 콘솔에서 DB 연결 확인
}