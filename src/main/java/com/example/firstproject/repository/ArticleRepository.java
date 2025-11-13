package com.example.firstproject.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.firstproject.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long>{ // 1️⃣ 상속 추가
  
  // 2️⃣ 오버라이드 하기! Iterable -> ArrayList 로 수정
  @Override
  ArrayList<Article> findAll();

}