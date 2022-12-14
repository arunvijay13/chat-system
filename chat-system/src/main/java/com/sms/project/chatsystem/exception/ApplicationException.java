package com.sms.project.chatsystem.exception;

import com.sms.project.chatsystem.constant.ReturnCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApplicationException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();

        Map<String, Object> errors = new LinkedHashMap<>();
        List<FieldError> fieldErrorList = ex.getBindingResult().getFieldErrors();

        for(FieldError fieldError : fieldErrorList ) {
            System.out.println(fieldError.getField());
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        body.put("message", errors);
        body.put("returnCode", ReturnCode.FAILURE);
        body.put("status", status.value());
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<Object> invalidUserHandler(Exception ex) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("message", ex.getMessage());
        body.put("returnCode", ReturnCode.FAILURE);
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Object> unknownMailRequestHandler(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("message", "Please connect to the Internet");
        body.put("returnCode", ReturnCode.FAILURE);
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> jwtExceptionHandler(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("message", ex.getMessage());
        body.put("returnCode", ReturnCode.FAILURE);
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runtimeExceptionHandler(Exception ex) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("message", ex.getMessage());
        body.put("returnCode", ReturnCode.FAILURE);
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("timestamp", new Date());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
