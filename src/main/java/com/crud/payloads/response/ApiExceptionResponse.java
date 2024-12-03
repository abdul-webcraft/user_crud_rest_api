package com.crud.payloads.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiExceptionResponse {

    private String status;
    private String message;

}
