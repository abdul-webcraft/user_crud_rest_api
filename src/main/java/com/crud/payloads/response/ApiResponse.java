package com.crud.payloads.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T> {

    private String status;
    private T response;

}
