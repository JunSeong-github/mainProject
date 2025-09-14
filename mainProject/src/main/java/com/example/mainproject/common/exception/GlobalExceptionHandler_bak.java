//package com.example.mainproject.common.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.*;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler_bak {
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
//        String msg = e.getBindingResult().getAllErrors().stream()
//                .findFirst().map(err -> err.getDefaultMessage()).orElse("Validation error");
//        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(msg);
//    }
//}
