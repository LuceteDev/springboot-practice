package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;

import lombok.extern.slf4j.Slf4j;


@Slf4j // ✅ PATHCH 실습을 하면서 필수로 추가해야하는 (로그 찍는 기능) 어노테이션
@RestController // 1️⃣ 컨트롤러 선언해주기
public class ArticleApiController {

  // 〰️〰️〰️〰️〰️〰️〰️〰️ 11.14 | 서비스 계층 추가 실습 〰️〰️〰️〰️〰️〰️〰️〰️ //
  // 〰️〰️〰️〰️〰️〰️〰️〰️ 컨트롤러, 서비스, 리포지터리 역할 분업 〰️〰️〰️〰️〰️〰️〰️〰️ //

  @Autowired
  private ArticleService articleService; // 서비스 객체 주입

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️ GET/POST/PATCH/DELETE 매핑 확인 〰️〰️〰️〰️〰️〰️〰️〰️ //



  // ✅ GET - 전체 게시글 조회 //

  @GetMapping("/api/articles")
  public List<Article> index() { // 2️⃣ index() 메서드 정의하기
      return articleService.index();
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //



  // ✅ GET - 개별 조회 //
  // ⚠️ GET 단일 조회: @PathVariable Long id 사용

  @GetMapping("/api/articles/{id}") // 1️⃣ URL 정의하기
  public Article show(@PathVariable Long id) { // 2️⃣ URL의 id를 매개변수로 받아올 수 있도록 수정
      return articleService.show(id);
      
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ POST - 값 제출 및 저장하기 //
  // ⚠️ POST 생성: @RequestBody CoffeeDto dto → Entity 변환 후 save
  
  @PostMapping("/api/articles") // 1️⃣ URL 정의하기
  public ResponseEntity<Article> create(@RequestBody ArticleForm dto) { // 2️⃣ 값 저장을 위해 DTO에 접근할 수 있도록 매개변수 넣기
      
    Article created = articleService.create(dto);

    return (created != null) ? 
    ResponseEntity.status(HttpStatus.OK).body(created) :
    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
    
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //
  

  // ✅ PATCH - 값 전체 및 일부 수정하기 //
  // ⚠️ PATCH 수정: 기존 Entity 조회 → patch() 적용 → save
  // ⚠️ REST API 에서의 PATCH는 @RequestBody 를 사용해야함

  @PatchMapping("/api/articles/{id}") // 1️⃣ URL 정의하기
  public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
    
    Article updated = articleService.update(id, dto); // 서비스를 통해 게시글 수정

    return (updated != null) ? // ⚠️ 수정되면 정상, 실패시 오류 응답하기 
    ResponseEntity.status(HttpStatus.OK).body(updated) :
    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ DELETE - 게시글 삭제 //
  // ⚠️ DELETE: 기존 Entity 조회 → delete
  // ⚠️ log.info()로 요청 DTO, DB Entity 값 확인

 @DeleteMapping("/api/articles/{id}")
 public ResponseEntity<Article> delete(@PathVariable Long id){
  
  // 1️⃣ 대상 찾기 - 서비스에 위임
  Article deleted = articleService.delete(id);

  return (deleted != null) ? // ⚠️ 삭제 결과에 따라 응답 처리
  ResponseEntity.status(HttpStatus.OK).body(deleted) :
  ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();

 }


 // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //
 
 
 // 〰️〰️〰️〰️〰️〰️〰️〰️ 트랜 잭션 맛보기 〰️〰️〰️〰️〰️〰️〰️〰️ //
 @Transactional
 @PostMapping("/api/transaction-test") // 여러 게시글 생성 요청 접수
 public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
  List<Article> createdList = articleService.createArticles(dtos); // 서비스 호출
  return (createdList != null) ? // 생성 결과에 따라 응답 처리
  ResponseEntity.status(HttpStatus.OK).body(createdList) :
  ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();

 }
 

}
