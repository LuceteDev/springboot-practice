package com.example.firstproject.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service // 서비스 객체 생성
public class ArticleService {
  @Autowired
  private ArticleRepository articleRepository; // 게시글 리포지터리 객체 주입

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  public List<Article> index() {
    return articleRepository.findAll();
  }

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  public Article show(Long id) {
    return articleRepository.findById(id).orElse(null);
  }

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  public Article create(ArticleForm dto) {
    Article article = dto.toEntity();

    if(article.getId() != null){
      return null;
    }
    
    return articleRepository.save(article);
  }

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  public Article update(Long id, ArticleForm dto) {

    Article article = dto.toEntity(); // ⚠️ 이 article은 DB에 있는 엔티티가 ❌, 갱신하라고 보낸 값임

    log.info("id : {}, artilce : {}", id, article.toString());

    // 2️⃣ 타깃 조회하기 = ⚠️ 기존 DB 데이터‼️
    Article target = articleRepository.findById(id).orElse(null);

    // 3️⃣ 잘못된 요청 처리하기
    if(target == null || id != article.getId()){
      // 404 반환하도록! ❌
      log.info("잘못된 요청! id : {}, artilce : {}", id, article.toString());

      return null; // 응답은 컨트롤러가 하므로 여기서는 null 반환
    }

    // 4️⃣ 업데이트 및 정상 응답(200) 출력 확인하기
    target.patch(article);
    Article updated = articleRepository.save(target); // 4️⃣.1️⃣ article 엔티티 DB에 저장하기

    return updated; // 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  public Article delete(Long id) {

    // 1️⃣ 대상 찾기
    Article target = articleRepository.findById(id).orElse(null);

    // 2️⃣ 잘못된 요청 처리하기
    if (target == null) {
    return null;
  }

    // 3️⃣ 대상 삭제하기
    articleRepository.delete(target);
    return target;

  }

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //
  

  public List<Article> createArticles(List<ArticleForm> dtos) {
    
    // 1️⃣ - dto 묶음을 엔티티 묶음으로 변환하기 -> stream 문법 사용
    List<Article> articleList = dtos.stream() // 1. dtos 를 스트림화 | 4. 최종 결과를 저장
    .map(dto -> dto.toEntity()) // 2. map() 으로 dto가 하나하나 올 때마다 dto.Entity() 수행해 매핑
    .collect(Collectors.toList()); // 3. 매핑한 것을 리스트로 묶음
    
    // 2️⃣ - 엔티티 묶음을 DB에 저장하기
    articleList.stream() // 1. articleList를 스트림화
    .forEach(article -> articleRepository.save(article)); // 2. article이 하나씩 올 때마다 리포지터리를 통해 DB에 저장

    // 3️⃣ - 강제 예외 발생시키기
    articleRepository.findById(-1L) // 1. id가 -1인 데이터 찾기
    .orElseThrow(() -> new IllegalAccessError("결제 실패!")); // 2. 찾는 데이터가 없으면 예외 발생

    // 4️⃣ - 결과 값 반환하기
    return articleList;
  }
}
