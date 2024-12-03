package com.crud.payloads.request;

import com.crud.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is required!")
    @Size(min= 3, message = "Name must have atleast 3 characters!")
    @Size(max= 20, message = "Name can have have atmost 20 characters!")
    private String name;

    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Mobile number is required!")
    @Size(min = 10, max = 10, message = "Mobile number must have 10 characters!")
    @Pattern(regexp="^[0-9]*$", message = "Mobile number must contain only digits")
    private String mobileNo;

    @NotBlank(message = "Address is required !!")
    private String address;


}
