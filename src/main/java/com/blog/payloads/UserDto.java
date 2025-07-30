package com.blog.payloads;

import com.blog.entities.Comment;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;
    @Email(message = "Email addersss is not valid")
    private String email;
    @NotEmpty
 
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{6,}$" ,message = "Requires Min length of 6 chars, a uppercase, a lowercase,a number, and a special charector")
    private String password;
    @NotBlank
    private String about;


}
