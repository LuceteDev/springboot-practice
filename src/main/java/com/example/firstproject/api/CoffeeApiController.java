package com.example.firstproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;

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
  
  @Autowired // 2️⃣ - @Autowired 선언 | 커피 리포지터리 주입
  private CoffeeRepository coffeeRepository;

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️ GET/POST/PATCH/DELETE 매핑 확인 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ GET - 전체 메뉴 조회 //

  @GetMapping("/api/coffees")
  // 3️⃣ - GET 전체 조회: URL매핑 확인 + List<Coffee> index() : 기본 양식 ➡️ List<Coffee> index()
  public List<Coffee> index() { 
    return coffeeRepository.findAll(); // 4️⃣ - JSON 반환하기 : .findAll() 메서드
  }
  
  // 5️⃣ - Talend API로 JSON 반환 확인 : GET()
  

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ GET - 개별 조회 //
  // ⚠️ GET 단일 조회: @PathVariable Long id 사용

  @GetMapping("/api/coffees/{id}") // 3️⃣ - URL 정의하기
    public Coffee show(@PathVariable Long id) { // 4️⃣ - URL의 id를 매개변수로 받아올 수 있도록 수정
        return coffeeRepository.findById(id).orElse(null); // 5️⃣ 반환하기 : - id를 찾고, 없으면 null
    }
    
    // 6️⃣ - Talend API로 JSON 반환 확인 : GET()

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ POST - 값 제출 및 저장하기 //
  // ⚠️ POST 생성: @RequestBody CoffeeDto dto → Entity 변환 후 save

  @PostMapping("/api/coffees") // 3️⃣ - URL 정의하기
  public Coffee create(@RequestBody CoffeeDto dto) { // 4️⃣ - 값 저장을 위해 DTO에 접근할 수 있도록 매개변수 넣기

    // ⚠️ DTO(CoffeeDto)에서 엔티티(Coffee) 객체로 변환
    Coffee coffee = dto.toEntity(); // 5️⃣ - Entity 변환 후 DB에 저장하기 전 메모리 상의 Coffee 객체를 생성

    // ⚠️ 리포지터리에서 CrudRepository 를 상속받았으니 CRUD 기능을 사용할 수 있음. 이중 save(entity) 메서드를 이용할 것
      return coffeeRepository.save(coffee); // 6️⃣ - DB에 저장 후 저장된 객체 반환
  }
  // 7️⃣ - Talend API로 JSON 반환 확인 : POST()

  
  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  // ✅ PATCH - 값 전체 및 일부 수정하기 //
  // ⚠️ PATCH 수정: 기존 Entity 조회 → patch() 적용 → save
  // ⚠️ REST API 에서의 PATCH는 @RequestBody 를 사용해야함

  @PatchMapping("/api/coffees/{id}") // 3️⃣ URL 정의하기
  public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
    
    // 4️⃣ - DTO -> 엔티티 변환하기
    Coffee coffee = dto.toEntity(); // ⚠️ 이 coffee는 DB에 있는 엔티티가 ❌, 갱신하라고 보낸 값임
    // ⚠️ 즉 DB에 저장하는 게 아니라, HTTP PATCH 요청으로 들어온 JSON 데이터를 자바 객체(Coffee)로 변환해서 메모리 상에 올려놓은 상태

    // {
    //   "id": 1,
    //   "title": "새로운 제목",
    //   "content": "내용 수정됨"
    // } ‼️이렇게 HTTP 통신에서 JSON 형태로 PATCH 요청을 들어옴
    // ✅ 이 값이 DTO → Coffee로 변환되어, coffee에는 ( id=1, title="새로운 제목", content="내용 수정됨" ) 형태로 들어감

    log.info("id : {}, artilce : {}", id, coffee.toString());


    // 5️⃣ - 타깃 조회하기 = ⚠️ 기존 DB 데이터‼️
    Coffee target = coffeeRepository.findById(id).orElse(null);


    // 6️⃣ - 잘못된 요청 처리하기 -> public Coffee ❌ ➡️ public ResponseEntity<Coffee> ✅
    if(target == null || id != coffee.getId()){
      // 404 반환하도록! ❌
      log.info("잘못된 요청! id : {}, artilce : {}", id, coffee.toString());

      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }

    // 7️⃣ - ⚠️ 업데이트 및 정상 응답(200) 출력 확인하기
    // 7️⃣ - patch() 메서드 빠른 수정 으로 생성 후 정의하기
    // 7️⃣ - save() 메서드와 반환 정상 응답(200) 출력 작성하기
    target.patch(coffee);
    Coffee updated = coffeeRepository.save(target); // 4️⃣.1️⃣ coffee 엔티티 DB에 저장하기
    return ResponseEntity.status(HttpStatus.OK).body(updated); // 4️⃣.2️⃣ 200으로 정상 응답하기
  }
  // 8️⃣ - Talend API로 JSON 반환 확인 : PATCH()


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  // ✅ DELETE - 게시글 삭제 //
  // ⚠️ DELETE: 기존 Entity 조회 → delete
  // ⚠️ 서버의 상태 코드를 출력하기 위해 기존 `public Coffee` → `ResponseEntity<Coffee>` 로 수정
  // ⚠️ log.info()로 요청 DTO, DB Entity 값 확인

  @DeleteMapping("/api/coffees/{id}") // 3️⃣ - URL 정의하기
  public ResponseEntity<Coffee> delete(@PathVariable Long id){ // 4️⃣ - URL의 id를 매개변수로 받아올 수 있도록 수정
    
    // 5️⃣ - 대상 찾기 - 즉 URL에 매핑된 id 값으로 기존 DB 데이터에서 찾는 것이다!
    Coffee target = coffeeRepository.findById(id).orElse(null);

    // 6️⃣ - 잘못된 요청 처리하기
    if (target == null) {
      return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
    }

    // 7️⃣ - 대상 삭제 후 반환하기
    coffeeRepository.delete(target);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  // 8️⃣ - Talend API로 JSON 반환 확인 : DELETE()
}
