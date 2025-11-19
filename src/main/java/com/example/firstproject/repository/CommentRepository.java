package com.example.firstproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.firstproject.entity.Comment;

// 1️⃣ extends JpaRepository<Comment, Long> 상속받기
public interface CommentRepository extends JpaRepository<Comment, Long>{
  
  // 〰️〰️〰️ 특정 게시글의 모든 댓글 조회 〰️〰️〰️ //
  // 3️⃣ @Query 어노테이션으로 직접 적은 쿼리 수행
  @Query(value = "SELECT * FROM comment WHERE article_id = :articleId", nativeQuery = true)

  // 2️⃣ findByArticleId 라는 메서드 이름으로 모든 댓글을 조회하여 List<> 형태로 반환
  // List<Comment> findByArticleId(Long articleId);
  List<Comment> findByArticleId(@Param("articleId") Long articleId);
  // 5️⃣ 테스트 코드 생성하기
  
  // 〰️〰️〰️ 특정 닉네임의 모든 댓글 조회 〰️〰️〰️ //
  // 4️⃣ xml로 쿼리 실행해보기
  List<Comment> findByNickname(String nickname);

}
