package com.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
},name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required!")
    @Size(min= 3, message = "Name must have atleast 3 characters!")
    @Size(max= 20, message = "Name can have have atmost 20 characters!")
    private String name;

    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Mobile number is required!")
    @Size(min = 10, max = 10, message = "Mobile number must have 10 characters!")
    @Pattern(regexp="^[0-9]*$", message = "Mobile number must contain only digits")
    private String mobileNo;

    @NotBlank(message = "Address is required !!")
    private String address;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private Set<Role> roles=new HashSet<>();

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password){
        this.password=password;
    }


}
