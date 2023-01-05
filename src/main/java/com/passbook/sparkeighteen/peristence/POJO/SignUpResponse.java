package com.passbook.sparkeighteen.peristence.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * When user successfully signUp then get response in response user get email and password.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {

    private Integer userID;
    private String message;

}
