package com.example.firstproject.dto;

import com.example.firstproject.entity.Member;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor // 2️⃣ 생성자 생성을 이 어노테이션으로 간결하게
@ToString // 5️⃣ toString() 메서드를 간결하게 하기 위한 어노테이션

public class MemberForm {

  // 1️⃣ 필드 선언하기
  private String email; // 이메일을 받을 필드
  private String password; // 비밀번호를 받을 필드


  // 2️⃣ 생성자 추가하기
//   public MemberForm(String email, String password) {
//   this.email = email;
//   this.password = password;
// }

  // 3️⃣ 생성자와 동일하게 메소드도 자동완성을 할 수가 있었다.
  // 그러나 toEntity() 는 없다.. 뭐지.. 원래 이 과정은 실습때도 제일 마지막에 하던데
  
  // toEntity()는 컨트롤러 페이지나 이를 호출하는 페이지에서 자동 완성 해야하는 것 같다..?!
  // GPT 답변 : 👉 toEntity()는 IDE가 자동 생성해주는 게 아니라, DTO → Entity 변환을 위해 직접 작성하는 메서드입니다.
  // 즉 (IDE의 “빠른 수정”으로 자동 생성 후 내용을 채워 넣으면 된다!)

  // @Override
  // public String toString() {
  //   // TODO Auto-generated method stub
  //   // return super.toString();
  //       return "MemberForm{"
  //       + "email='" + email + '\''   // title 값 출력
  //       + ", password='" + password + '\''   // content 값 출력
  //       + '}';
  // }
  
  // 4️⃣ 필자는 컨트롤러 페이지에서 toEntity()를 호출하기 때문에, 컨트롤러에서 코드자동완성을 이용했다.
  // 빠른 수정 -> create Method 를 수행하자.
  
  public Member toEntity() {
    // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    return new Member(null, email, password);
  }  

}
