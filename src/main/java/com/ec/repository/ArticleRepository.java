package com.ec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ec.model.Article;

public interface ArticleRepository extends JpaRepository<Article, String> {
	
}
