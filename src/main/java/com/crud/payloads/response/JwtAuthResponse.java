package com.crud.payloads.response;

import com.crud.model.User;
import com.crud.payloads.request.UserRequest;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse<T> {

    private String token;
    private T response;
}
