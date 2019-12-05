package com.haojishu.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  @Override
  Page<Article> findAll(Pageable pageable);

  @Query(
      value = "select * from  arcticle where title = ?1",
      countQuery = "select  count(*) from arcticle where title = ?1",
      nativeQuery = true)
  Page<Article> findByTitle(String title, Pageable pageable);
}
