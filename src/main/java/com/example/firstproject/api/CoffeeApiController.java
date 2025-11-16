package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Slf4j // 1️⃣ - @RestController, @Slf4j 선언 확인
@RestController // 1️⃣ - @RestController, @Slf4j 선언 확인

public class CoffeeApiController {
  
  @Autowired // 2️⃣ - @Autowired 선언 | 서비스 객체 주입
  private CoffeeService coffeeService;

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️ GET/POST/PATCH/DELETE 매핑 확인 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ GET - 전체 메뉴 조회 //

  @GetMapping("/api/coffees")
  // 3️⃣ - GET 전체 조회: URL매핑 확인 + List<Coffee> index() : 기본 양식 ➡️ List<Coffee> index()
  public List<Coffee> index() { 
    return coffeeService.index(); // 4️⃣ - 서비스로 위임
  }
  
  // 5️⃣ - Talend API로 JSON 반환 확인 : GET()
  

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ GET - 개별 조회 //
  // ⚠️ GET 단일 조회: @PathVariable Long id 사용

  @GetMapping("/api/coffees/{id}") // 3️⃣ - URL 정의하기
    public Coffee show(@PathVariable Long id) { // 4️⃣ - URL의 id를 매개변수로 받아올 수 있도록 수정
        return coffeeService.show(id); // 5️⃣ - 서비스로 위임
    }
    
    // 6️⃣ - Talend API로 JSON 반환 확인 : GET()

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ POST - 값 제출 및 저장하기 //
  // ⚠️ POST 생성: @RequestBody CoffeeDto dto → Entity 변환 후 save

  @PostMapping("/api/coffees") // 3️⃣ - URL 정의하기
  public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto) { // 3️⃣- 값 저장을 위해 DTO에 접근할 수 있도록 매개변수 넣기
    
    Coffee created = coffeeService.create(dto); // 4️⃣ - 서비스에 dto를 같이 위임

    return (created != null) ? // 8️⃣ - created 객체가 null 인지의 여부에 따라 삼항 연산자로 (상태)응답 반환
    ResponseEntity.status(HttpStatus.OK).body(created) :
    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();

  }
  // 9️⃣ - Talend API로 JSON 반환 확인 : POST()

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ PATCH - 값 전체 및 일부 수정하기 //
  // ⚠️ PATCH 수정: 기존 Entity 조회 → patch() 적용 → save
  // ⚠️ REST API 에서의 PATCH는 @RequestBody 를 사용해야함

  @PatchMapping("/api/coffees/{id}") // 3️⃣ URL 정의하기 + 매개변수, 반한형 수정
  public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
    
    Coffee updated = coffeeService.update(id, dto); // 4️⃣ - 서비스를 통해서 수정
    
    return (updated != null) ? // ⚠️ 9️⃣ - 수정되면 정상, 실패시 오류 응답하기 
    ResponseEntity.status(HttpStatus.OK).body(updated) :
    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
  }
  //  1️⃣0️⃣ - Talend API로 JSON 반환 확인 : PATCH()


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  // ✅ DELETE - 게시글 삭제 //
  // ⚠️ DELETE: 기존 Entity 조회 → delete
  // ⚠️ 서버의 상태 코드를 출력하기 위해 기존 `public Coffee` → `ResponseEntity<Coffee>` 로 수정
  // ⚠️ log.info()로 요청 DTO, DB Entity 값 확인

  @DeleteMapping("/api/coffees/{id}") // 3️⃣ - URL 정의하기
  public ResponseEntity<Coffee> delete(@PathVariable Long id){ // 4️⃣ - URL의 id를 매개변수로 받아올 수 있도록 수정
    
    Coffee deleted = coffeeService.delete(id); // 5️⃣ - 서비스에 id와 같이 위임
    
    return (deleted != null) ? // ⚠️9️⃣ - 삭제 결과에 따라 응답 처리
    ResponseEntity.status(HttpStatus.OK).body(deleted) :
    ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
  }

  // 1️⃣0️⃣ - Talend API로 JSON 반환 확인 : DELETE()
}
