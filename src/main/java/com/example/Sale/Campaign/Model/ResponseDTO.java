package com.example.Sale.Campaign.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO<T> {
    private T body;
    private String message;
    private HttpStatus status;


    public ResponseDTO(T body, HttpStatus status, String message) {
        this.body = body;
        this.message = message;
        this.status = status;
    }
}

