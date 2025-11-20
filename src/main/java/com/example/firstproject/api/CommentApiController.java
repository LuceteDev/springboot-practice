package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController // 1️⃣ REST 컨트롤러 선언하기
public class CommentApiController {

  @Autowired // 2️⃣ 댓글 서비스 객체 주입
  private CommentService commentService;

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ GET - 댓글 조회 //
  @GetMapping("/api/articles/{articleId}/comments")
  public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

    // 1️⃣ 서비스에 위임
    List<CommentDto> dtos = commentService.comments(articleId);

    // 2️⃣ 결과 응답
      return ResponseEntity.status(HttpStatus.OK).body(dtos);
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  
  // ✅ POST - 댓글 생성 //
  @PostMapping("/api/articles/{articleId}/comments")
  public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {
      
    // 1️⃣ 서비스에 위임
    CommentDto createDto = commentService.create(articleId, dto);

    // 2️⃣ 결과 응답
    return ResponseEntity.status(HttpStatus.OK).body(createDto);

  }
  

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ PATCH - 댓글 수정//
  @PatchMapping("/api/comments/{id}")
  public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {
      
    // 1️⃣ 서비스에 위임
    CommentDto updateDto = commentService.update(id, dto);

    // 2️⃣ 결과 응답
    return ResponseEntity.status(HttpStatus.OK).body(updateDto);

  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ DELETE - 댓글 삭제//
  @DeleteMapping("/api/comments/{id}")
  public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
      
    // 1️⃣ 서비스에 위임
    CommentDto deleteDto = commentService.delete(id);

    // 2️⃣ 결과 응답
    return ResponseEntity.status(HttpStatus.OK).body(deleteDto);

  }
}
