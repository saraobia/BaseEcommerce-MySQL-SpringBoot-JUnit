package com.ec.controller;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ec.exception.*;

@ControllerAdvice
public class ControllerCustomizedResponseEntityException extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ArticleNotFoundException.class)
    public final ResponseEntity<Object> handleArticleNotFoundException(ArticleNotFoundException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NOT_FOUND, 
                                       request);
    }

	   
    @ExceptionHandler(ClientNotFoundException.class)
    public final ResponseEntity<Object> handleClientNotFoundException(ClientNotFoundException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NOT_FOUND, 
                                       request);
    }
    
    @ExceptionHandler(ClientAlreadyExistsException.class)
    public final ResponseEntity<Object> handleClientAlreadyExistsException(ClientAlreadyExistsException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.BAD_REQUEST, 
                                       request);
    }

    @ExceptionHandler(ClientOperationException.class)
    public final ResponseEntity<Object> handleClientOperationException(ClientOperationException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.INTERNAL_SERVER_ERROR, 
                                       request);
    }
    
    @ExceptionHandler(OrderAlreadyExistsException.class)
    public final ResponseEntity<Object> handleOrderAlreadyExistsException(OrderAlreadyExistsException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.CONFLICT, 
                                       request);
    }
    
    @ExceptionHandler(OrderNotConfirmedException.class)
    public final ResponseEntity<Object> handleOrderNotConfirmedException(OrderNotConfirmedException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.CONFLICT, 
                                       request);
    }
    
    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NOT_FOUND, 
                                       request);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NOT_FOUND, 
                                       request);
    }
    


    @ExceptionHandler(UserGenericException.class)
    public final ResponseEntity<Object> handleUserGenericException(UserGenericException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.INTERNAL_SERVER_ERROR, 
                                       request);
    }

    // Generic Exception
    @ExceptionHandler(NoContentException.class)
    public final ResponseEntity<Object> handleNoContentExceptions(NoContentException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NO_CONTENT, 
                                       request);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.NOT_FOUND, 
                                       request);
    }
    

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(new Date(), 
                                                         ex.getMessage(),
                                                         request.getDescription(true));
        return handleExceptionInternal(ex, 
                                       exResp, 
                                       new HttpHeaders(), 
                                       HttpStatus.INTERNAL_SERVER_ERROR, 
                                       request);
    }
}
