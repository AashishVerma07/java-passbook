package com.passbook.sparkeighteen.peristence.POJO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Login request is for taking user credential (like email, password) for user login.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Pattern(regexp = "^([a-zA-Z0-9_\\.\\-]+)@([a-zA-Z0-9_\\-]+)\\.([a-zA-Z]{2,5})$", message = "email is not valid")
    private String email;

    @NotBlank
    private String password;
}
