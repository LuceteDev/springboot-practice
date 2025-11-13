package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j // ✅ PATHCH 실습을 하면서 필수로 추가해야하는 (로그 찍는 기능) 어노테이션
@RestController // 1️⃣ 컨트롤러 선언해주기
public class ArticleApiController {

  @Autowired // 3️⃣ 게시글 리포지터리 주입하기
  private ArticleRepository articleRepository;

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️ GET/POST/PATCH/DELETE 매핑 확인 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //



  // ✅ GET - 전체 게시글 조회 //

  @GetMapping("/api/articles")
  public List<Article> index() { // 2️⃣ index() 메서드 정의하기
      return articleRepository.findAll();
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //



  // ✅ GET - 개별 조회 //
  // ⚠️ GET 단일 조회: @PathVariable Long id 사용

  @GetMapping("/api/articles/{id}") // 1️⃣ URL 정의하기
  public Article show(@PathVariable Long id) { // 2️⃣ URL의 id를 매개변수로 받아올 수 있도록 수정
      return articleRepository.findById(id).orElse(null);
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ POST - 값 제출 및 저장하기 //
  // ⚠️ POST 생성: @RequestBody CoffeeDto dto → Entity 변환 후 save
  
  @PostMapping("/api/articles") // 1️⃣ URL 정의하기
  public Article create(@RequestBody ArticleForm dto) { // 2️⃣ 값 저장을 위해 DTO에 접근할 수 있도록 매개변수 넣기
      
    Article article = dto.toEntity();

      return articleRepository.save(article);
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //
  

  // ✅ PATCH - 값 전체 및 일부 수정하기 //
  // ⚠️ PATCH 수정: 기존 Entity 조회 → patch() 적용 → save
  // ⚠️ REST API 에서의 PATCH는 @RequestBody 를 사용해야함

  @PatchMapping("/api/articles/{id}") // 1️⃣ URL 정의하기
  public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
    // 1️⃣ DTO -> 엔티티 변환하기
    Article article = dto.toEntity(); // ⚠️ 이 article은 DB에 있는 엔티티가 ❌, 갱신하라고 보낸 값임

    // {
    //   "id": 1,
    //   "title": "새로운 제목",
    //   "content": "내용 수정됨"
    // } ‼️이렇게 HTTP 통신에서 JSON 형태로 PATCH 요청을 들어옴
    // ✅ 이 값이 DTO → Article로 변환되어, article에는 { id=1, title="새로운 제목", content="내용 수정됨" } 형태로 들어감
    log.info("id : {}, artilce : {}", id, article.toString());

    // 2️⃣ 타깃 조회하기 = ⚠️ 기존 DB 데이터‼️
    Article target = articleRepository.findById(id).orElse(null);

    // 3️⃣ 잘못된 요청 처리하기 -> public Article❌ ➡️ public ResponseEntity<Article> ✅
    if(target == null || id != article.getId()){
      // 404 반환하도록! ❌
      log.info("잘못된 요청! id : {}, artilce : {}", id, article.toString());

      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }

    // 4️⃣ 업데이트 및 정상 응답(200) 출력 확인하기
    target.patch(article);
    Article updated = articleRepository.save(target); // 4️⃣.1️⃣ article 엔티티 DB에 저장하기
    return ResponseEntity.status(HttpStatus.OK).body(updated); // 4️⃣.2️⃣ 200으로 정상 응답하기
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ DELETE - 게시글 삭제 //
  // ⚠️ DELETE: 기존 Entity 조회 → delete
  // ⚠️ log.info()로 요청 DTO, DB Entity 값 확인

 @DeleteMapping("/api/articles/{id}")
 public ResponseEntity<Article> delete(@PathVariable Long id){
  
  // 1️⃣ 대상 찾기 - 즉 URL에 매핑된 id 값으로 기존 DB 데이터에서 찾는 것이다!
  Article target = articleRepository.findById(id).orElse(null);

  // 2️⃣ 잘못된 요청 처리하기
  if (target == null) {
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
  }

  // 3️⃣ 대상 삭제하기
  articleRepository.delete(target);
  return ResponseEntity.status(HttpStatus.OK).body(null);
 }
}
