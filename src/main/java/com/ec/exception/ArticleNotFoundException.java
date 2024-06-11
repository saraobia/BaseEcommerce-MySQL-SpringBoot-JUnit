package com.ec.exception;

public class ArticleNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ArticleNotFoundException(String idArticle) {
		super("Article with ID: " + idArticle + "not found");
	}

	
	

}
