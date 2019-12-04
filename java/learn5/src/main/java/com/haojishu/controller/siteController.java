package com.haojishu.controller;

import com.haojishu.domain.Article;
import com.haojishu.domain.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class siteController {

  @Autowired ArticleRepository articleRepository;

  // 显示文章列表
  @GetMapping
  public String getList(
      @RequestParam(value = "start", defaultValue = "0") Integer start,
      @RequestParam(value = "limit", defaultValue = "2") Integer limit,
      ModelMap map) {
    start = start < 0 ? 0 : start;
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(start, limit, sort);
    Page<Article> page = articleRepository.findAll(pageable);
    System.out.println(page.getNumber());
    System.out.println(page.getNumberOfElements());
    System.out.println(page.getSize());
    System.out.println(page.getTotalElements());
    System.out.println(page.getTotalPages());
    System.out.println(page.isFirst());
    System.out.println(page.isLast());
    System.out.println(page.getContent());
    return "List";
  }

  // 创建文章
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String postArticle() {
    Article article1 = new Article();
    article1.setAuthor("作者");
    article1.setId(1L);
    article1.setTitle("标题");
    articleRepository.saveAndFlush(article1);
    return "create";
  }

  // 编辑文章
  @RequestMapping(value = "/update", method = RequestMethod.GET)
  public Article updateArticle() {
    Article article1 = new Article();
    article1.setId(1L);
    Example<Article> example = Example.of(article1);
    Optional<Article> article2 = articleRepository.findOne(example);
    return article2.orElse(null);
  }

  // 删除文章
  @RequestMapping(value = "/update", method = RequestMethod.GET)
  public String delArticle() {
    Article article1 = new Article();
    article1.setId(1L);
    articleRepository.delete(article1);
    return "delArticle";
  }
}
