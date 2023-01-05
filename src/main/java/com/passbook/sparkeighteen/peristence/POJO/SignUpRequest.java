package com.passbook.sparkeighteen.peristence.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * Sign up request is request for user credential to user signUp and create profile.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "First name cannot be left Blank")
    private String firstname;

    @NotBlank(message = "Last name cannot be left Blank")
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @Pattern(regexp = "^([a-zA-Z0-9_\\.\\-]+)@([a-zA-Z0-9_\\-]+)\\.([a-zA-Z]{2,5})$", message = "email is not valid")
    private String email;
}
