package com.vmuguerza.tienda_api.exepciones;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class ManejadorErrores {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> argumentoInvalido(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
    
}
