package com.passbook.sparkeighteen.peristence.POJO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login response is get back values(userProfile) in response when user successfully login.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Integer userID;
    private String firstname;
    private String lastname;
    private String message;
    private Integer age;
    private String mobileNumber;
    private String pan;
    private String aadhar;
    private Gender gender;
    private String address;

}
