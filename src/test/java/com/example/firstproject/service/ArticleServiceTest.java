package com.example.firstproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
// import staic org.junit.jupiter.api.Assertions.*; 앞으로 사용할 수 있는 패키지 임포트

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ArticleServiceTest {

  @Autowired
  ArticleService articleService;

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  void testCreate_성공_title과_content만_있는_dto_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    String title = "2025.11.16_TEST";
    String content = "4444";
    ArticleForm dto = new ArticleForm(null, title, content); // dto 생성
    Article expected = new Article(4L, title, content);

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.create(dto);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  void testCreate_실패_id가_포함된_dto_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    Long id = 4L;
    String title = "2025.11.16_TEST";
    String content = "4444";
    ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
    Article expected = new Article(4L, title, content);

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.create(dto);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected, article);
  }

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  @Transactional
  void testDelete_성공_존재하는_id_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    Long id = 1L;
    Article expected = new Article(id, "test", "1111");

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.delete(id);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  @Transactional
  void testDelete_실패_존재하지_않는_id_입력 () { // ⚠️ null 이 반환되어야 성공인 테스트
    // 1️⃣ 예상 데이터 작성하기
    Long id = -1L;
    Article expected = null;

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.delete(id);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected, article);
  }



  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  void testIndex() {
    // 1️⃣ 예상 데이터 작성하기 -> data.sql의 더미 데이터를 뜻함
    
    // 2️⃣ 실제 데이터 획득하기
    List<Article> article = articleService.index();
    Article a = new Article(1L, "test", "1111");
    Article b = new Article(2L, "data", "2222");
    Article c = new Article(3L, "stub", "3333");
    List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(),article.toString());

  }
  

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  void testShow_성공_존재하는_id_입력 () {
    // 1️⃣ 예상 데이터 작성하기 -> data.sql의 더미 데이터를 뜻함
    Long id = 1L;
    Article expected = new Article(id, "test", "1111");

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.show(id);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  
  @Test
  void testShow_실패_존재하지_않는_id_입력 () {
    // 1️⃣ 예상 데이터 작성하기 -> data.sql의 더미 데이터를 뜻함
    Long id = -1L;
    Article expected = new Article(id, "test", "1111");

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.show(id);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  @Transactional
  void testUpdate_성공_존재하는_id_title_content가_있는_dto_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    Long id = 1L;
    String title = "2025.11.16_TEST";
    String content = "4444";
    ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
    Article expected = new Article(id, title, content);

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.update(id, dto);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  @Transactional
  void testUpdate_성공_존재하는_id_title만_있는_dto_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    Long id = 1L;
    String title = "2025.11.16_TEST";
    String content = null;
    ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
    Article expected = new Article(1L, "2025.11.16_TEST", "1111");

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.update(id, dto);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @Test
  @Transactional
  void testUpdate_실패_존재하지_않는_id의_dto_입력 () {
    // 1️⃣ 예상 데이터 작성하기
    Long id = 1L;
    String title = "2025.11.16_TEST";
    String content = "1234";
    ArticleForm dto = new ArticleForm(id, title, content); // dto 생성
    Article expected = null;

    // 2️⃣ 실제 데이터 획득하기
    Article article = articleService.update(id, dto);

    // 3️⃣ 예쌍데이터와 실제 데이터 비교해 검증하기
    assertEquals(expected.toString(), article.toString());
  }
  
}
