package com.example.firstproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoffeeService {

  @Autowired // 서비스가 DB 관련 처리를 담당하도록 컨트롤러에서 위임
  private CoffeeRepository coffeeRepository;

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  public List<Coffee> index() { // - JSON 반환하기 : .findAll() 메서드
    return coffeeRepository.findAll(); 
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  public Coffee show(Long id) { // 반환하기 : - id를 찾고, 없으면 null
    return coffeeRepository.findById(id).orElse(null);
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  public Coffee create(CoffeeDto dto) {

    // ⚠️ DTO(CoffeeDto)에서 엔티티(Coffee) 객체로 변환
    Coffee coffee = dto.toEntity(); // 5️⃣ - Entity 변환 후 DB에 저장하기 전 메모리 상의 Coffee 객체를 생성

    if(coffee.getId() != null){ // ⚠️ - 6️⃣ 데이터를 생성할 때는 id가 필요없음 또한 수정이 아닌 생성담당이기때문에
      return null;              // id가 존재하면 null을 반환해야함      
    }

    // ⚠️ 리포지터리에서 CrudRepository 를 상속받았으니 CRUD 기능을 사용할 수 있음. 이중 save(entity) 메서드를 이용할 것
     return coffeeRepository.save(coffee); // 7️⃣ - DB에 저장 후 저장된 객체 반환
  }


  public Coffee update(Long id, CoffeeDto dto) {

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

      return null;
    }

    // 7️⃣ - ⚠️ 업데이트 및 정상 응답(200) 출력 확인하기
    // 7️⃣ - patch() 메서드 빠른 수정 으로 생성 후 정의하기
    // 7️⃣ - save() 메서드와 반환 정상 응답(200) 출력 작성하기
    target.patch(coffee);
    Coffee updated = coffeeRepository.save(target); // 4️⃣.1️⃣ coffee 엔티티 DB에 저장하기

    return updated; // 8️⃣ - 응답은 컨트롤러가 하므로 여기서는 수정 데이터만 반환
  }


  public Coffee delete(Long id) {
    
    // 6️⃣ - 대상 찾기 - 즉 URL에 매핑된 id 값으로 기존 DB 데이터에서 찾는 것이다!
    Coffee target = coffeeRepository.findById(id).orElse(null);

    // 7️⃣ - 잘못된 요청 처리하기
    if (target == null) {
      return null;
    }

    // 8️⃣ - 대상 삭제 후 반환하기
    coffeeRepository.delete(target);
    return target;
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //
  
}
