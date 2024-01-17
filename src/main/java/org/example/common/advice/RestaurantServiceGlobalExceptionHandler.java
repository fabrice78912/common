package org.example.common.advice;


import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CustomErrorResponse;
import org.example.common.dto.GlobalErrorCode;
import org.example.common.exception.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@RestControllerAdvice
@Slf4j
public class RestaurantServiceGlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),GlobalErrorCode.ERROR_ORDER_NOT_FOUND );
        log.error("RestaurantServiceGlobalExceptionHandler::handleOrderNotFoundException exception caught {}",ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }


    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<?> handleUserExistException(UserExistException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.CONFLICT, ex.getMessage(),GlobalErrorCode.ERROR_CODE_USER_EXIST );
        log.error("RestaurantServiceGlobalExceptionHandler::handleUserExistException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }



    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
        CustomErrorResponse errorResponse=getResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(),GlobalErrorCode.UNAUTHORIZED_CODE );
        log.error("RestaurantServiceGlobalExceptionHandler::handleBadCredentialsException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.NOT_FOUND, ex.getMessage(),GlobalErrorCode.CODE_USER_NOT_FOUND );
        log.error("RestaurantServiceGlobalExceptionHandler::handleUserNotFoundException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<?> handleProductException(ProductException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(GlobalErrorCode.CODE_USER_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("RestaurantServiceGlobalExceptionHandler::handleProductException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(OrderException ex){
        CustomErrorResponse errorResponse=getResponse(HttpStatus.NOT_FOUND, ex.getMessage(),GlobalErrorCode.CODE_USER_NOT_FOUND );
        log.error("RestaurantServiceGlobalExceptionHandler::handleOrderException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handleOConnectionException(ConnectException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),GlobalErrorCode.GENERIC_ERROR );
        log.error("RestaurantServiceGlobalExceptionHandler::handleOConnectionException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> handleCourseNotFoundException(CourseNotFoundException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.NOT_FOUND, ex.getMessage(),GlobalErrorCode.CODE_USER_NOT_FOUND );
        log.error("RestaurantServiceGlobalExceptionHandler::handleCourseNotFoundException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<?> handleCartItemException(CartItemException ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.NOT_FOUND, ex.getMessage(),GlobalErrorCode.CODE_USER_NOT_FOUND );
        log.error("RestaurantServiceGlobalExceptionHandler::handleCartItemException exception caught {}",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<?> handleGenericException(Exception ex){
        CustomErrorResponse errorResponse= getResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(),GlobalErrorCode.GENERIC_ERROR );
        log.error("RestaurantServiceGlobalExceptionHandler::handleGenericException exception caught {}",ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    private CustomErrorResponse getResponse(HttpStatus status,
     String errorMessage,
    String errorCode){

        return CustomErrorResponse.builder()
                .status(status)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();

    }


}
