package com.example.firstproject.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.firstproject.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Long>{

//   💡 인터페이스로 만드는 이유는 Spring Data JPA가 내부적으로 자동 구현체를 만들어 주기 때문입니다.
// (save(), findAll() 등 직접 구현할 필요가 없음)

}
