package com.example.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OkResponse {

    private String type = "/ok";
    private String title = HttpStatus.OK.getReasonPhrase();
    private int status = HttpStatus.OK.value();
    private String detail;
    private String instance;
}