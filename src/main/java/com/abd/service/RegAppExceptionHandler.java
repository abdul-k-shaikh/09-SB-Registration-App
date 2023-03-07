package com.abd.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abd.exception.AppError;
import com.abd.exception.RegAppException;

@RestControllerAdvice
public class RegAppExceptionHandler {
	
	@ExceptionHandler(value=RegAppException.class)
	public ResponseEntity<AppError> handleAppException(RegAppException regAppException) {
		AppError error = new AppError();
		error.setErrorCode("REGAPP101");
		error.setErrorMsg(regAppException.getMessage());
		//Another way to display message same as line 18
		error.setErrorMsg("Some error occured, Try after sometime");
		
		ResponseEntity<AppError> entity = new ResponseEntity<AppError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		return entity;
	}
}
