package com.example.firstproject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import com.fasterxml.jackson.annotation.JacksonInject.Value;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;



@Slf4j // 로깅 기능을 위한 어노테이션 추가


@Controller


public class ArticleController {

  @Autowired // 3️⃣ 스프링부트가 미리 생성해 놓은 리파지토리 객체 주입!
  private ArticleRepository articleRepository;

  // 2025.11.20 댓글 출력하기 위해 추가 코드{
  @Autowired
  private CommentService commentService;
  //}

  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @GetMapping("articles/new")
  public String newArticleForm(){
    return "articles/new";
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @PostMapping("/articles/create")
  public String createArticle(ArticleForm form) {  
    // System.out.println("프론트쪽" + form.toString());
    log.info(form.toString());

    // 1️⃣ DTO를 엔티티로 변환
    Article article = form.toEntity();
    // System.out.println("DTO" + article.toString()); // DTO가 엔티티로 잘 변환되는지 출력!
    log.info(article.toString());
    
    // 2️⃣ 리파지토리로 엔티티를 DB에 저장
    Article saved = articleRepository.save(article);
    log.info(saved.toString());
    // System.out.println("DB" + saved.toString()); // article이 DB에 잘 저장되는지 출력!
    
    // 3️⃣ 뷰 페이지 반환 
    // ✅ ⚠️엔티티에 @Getter 추가해주기
    return "redirect:/articles/" + saved.getId();
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  
  // 2025.11.08 추가 코드
  @GetMapping("/articles/{id}") // 2️⃣ 가져온 데이터를 모델에 등록하기, 컨트롤러에서는 변수를 사용할땐 {} 는 하나만 사용함!
  public String show(@PathVariable Long id, Model model) { // 매개변수로 id 받아오기
    // ✅ Pathvariable은 URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션
    log.info("id = " + id);
    //‼️ 4장에서 배운 로깅 기능을 이용해서 디버깅하기

    // 1️⃣ id를 조회해 DB에서 해당 데이터 가져오기
    // Article articleEntity = articleRepository.findById(id);
    // ✅ articleRepository가 findBy(id)로 찾은 값을 반환할 때 
    // ✅ 반환형이 Article이 아니라서 오류가 발생 한 것을 Optional<> 로 반환형을 맞춰줘서 해결!
    
    // Optional<Article> articleEntity = articleRepository.findById(id); // 이번 실습에선 사용하지 않음
    Article articleEntity = articleRepository.findById(id).orElse(null);
    
    // 2025.11.20 댓글 출력하기 위해 추가 코드{
    List<CommentDto> commentDtos = commentService.comments(id);
    model.addAttribute("commentDtos", commentDtos); // mustahce에 사용될 변수

  //}

    // 2️⃣ 모델에 데이터 등록하기
    // name이라는 이름으로 value 객체 추가
    // model.addAttribute(String name, Object value); ✅ 이러한 형식으로 사용!
    model.addAttribute("article", articleEntity);
    // article 라는 이름으로 articleEntity 엔티티/객체 추가

    // 3️⃣ 조회한 데이터를 사용자에게 보여 주기 위한 뷰 페이지 만들고 반환하기
    return "articles/show";
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @GetMapping("/articles")
  public String index(Model model) { // 2️⃣ 모델에 데이터 등록하기
    // 1️⃣ 모든 데이터 가져오기
    // List<Article> articleEntityList = articleRepository.findAll(); 
    // ❌ findAll() 메서드가 반환하는 데이터 타입은 Iterable 이어서 List<> 와는 타입이 다르기 때문에 발생한 에러!
    // 해결방법 1️⃣ 다운 캐스팅 List<Article> articleEntityList = (List<Article>) articleRepository.findAll(); 
    // 해결방법 2️⃣ 업 캐스팅 Iterable<Article> articleEntityList = articleRepository.findAll();
    // 해결방법 3️⃣ findAll() 메서드가 Iterable이 아닌 ☑️ ArrayList 형으로 변환하도록 변경
    ArrayList<Article> articleEntityList = articleRepository.findAll();

    // 2️⃣ 모델에 데이터 등록하기
    model.addAttribute("articleList", articleEntityList);

    // 3️⃣ 뷰 페이지 설정하기  
    return "articles/index";
      
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //

  @GetMapping("/articles/{id}/edit") // 2️⃣ URL 요청 접수
  public String edit(@PathVariable Long id, Model model) { // 1️⃣ 메서드 생성 및 뷰 페이지 설정

    // 3️⃣ DB에서 수정할 데이터 가져오기
    Article articleEntity = articleRepository.findById(id).orElse(null);

    // 4️⃣ 모델에 데이터 등록하기
    model.addAttribute("article", articleEntity);
    
    // ☑️ 뷰 페이지 설정
    return "articles/edit";
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @PostMapping("/articles/update")
  public String update(ArticleForm form) { // 매개변수로 DTO 받아 오기 -> 수정 폼에서 전송한 데이터는 DTO로 받는다? 왜지?
    log.info(form.toString());
    
    // 1️⃣ DTO를 엔티티로 변환하기
    Article articleEntity = form.toEntity(); // DTO(form)를 엔티티(articleEntity)로 변환하기
    log.info(articleEntity.toString());

    // 2️⃣ DB에 저장된 게시글을 폼 데이터를 엔티티로 변환한 Article 객체의 id를 기준으로 조회해서 꺼내온 원본 데이터 저장
    Article target = articleRepository.findById(articleEntity.getId()).orElse(null); 

    // 2️⃣-2️⃣ 기존 데이터 값을 갱신하기
    if (target != null) {
      articleRepository.save(articleEntity); // 엔티티를 DB에 저장(갱신)
    }

    // 3️⃣ 수정 결과 페이지로 리다이렉트하기
      return "redirect:/articles/" + articleEntity.getId();
  }


  // 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ 영역 분리 〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️〰️ //


  @GetMapping("/articles/{id}/delete") // URL 요청 접수
  public String delete(@PathVariable Long id, RedirectAttributes rttr) { // RedirectAttributes rttr 를 추가하여 삭제 완료 메시지 남기기
    log.info("컨트롤러 접근시에 출력되는 삭제 요청이 접수되었다는 메시지");

    // 1️⃣ 삭제할 대상 가져오기 -> 해당 id를 가져오기 위해 
    // ✅ Pathvariable로 URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져오는 어노테이션 이용하기
    Article target = articleRepository.findById(id).orElse(null);
    log.info(target.toString());


    // 2️⃣ 대상 엔티티 삭제하기 -> if 문으로 null 인지 확인하고 진행
    if (target != null) {
      articleRepository.delete(target);
      rttr.addFlashAttribute("msg", "삭제됐습니다!");
    }

    // 3️⃣ 결과 페이지로 리다이렉트하기

      return "redirect:/articles";
  }

}
