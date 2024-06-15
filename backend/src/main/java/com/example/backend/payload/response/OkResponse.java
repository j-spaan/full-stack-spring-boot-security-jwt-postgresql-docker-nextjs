package com.example.backend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OkResponse {

    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;
}