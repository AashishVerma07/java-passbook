package com.passbook.sparkeighteen.controller;

import com.passbook.sparkeighteen.peristence.POJO.LoginRequest;
import com.passbook.sparkeighteen.peristence.POJO.LoginResponse;
import com.passbook.sparkeighteen.peristence.POJO.ProfileRequest;
import com.passbook.sparkeighteen.peristence.POJO.ProfileResponse;
import com.passbook.sparkeighteen.peristence.POJO.SignUpRequest;
import com.passbook.sparkeighteen.peristence.POJO.SignUpResponse;
import com.passbook.sparkeighteen.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


/**
 * User controller test have all operation test case success and error.
 */
@ExtendWith({MockitoExtension.class})
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    /**
     * when user set valid credential(email, password) then successful signUp.
     */
    @Test
    public void userSignUp_validUserDetail_signUpSuccessfull() throws Exception {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        Mockito.when(userService.signUp(any())).thenReturn(SignUpResponse.builder()
                .message("ok")
                .userID(1)
                .build());

        ResponseEntity<SignUpResponse> response = userController.signUp(signUpRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * when user set wrong credential(email, password) then unable to signUp.
     */
    @Test
    public void userSignUp_inValidUserDetail_signUpUnSuccessfull() throws Exception {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test@gmail.com")
                .password("")
                .build();

        Mockito.when(userService.signUp(any())).thenReturn(SignUpResponse.builder()
                .message("OK")
                .userID(1)
                .build());
        ResponseEntity<SignUpResponse> response = userController.signUp(signUpRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * when user set right email and password then user login successful.
     */
    @Test
    public void userLogin_validUserDetail_loginSuccessfull() throws Exception {

        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        Mockito.when(userService.login(any())).thenReturn(LoginResponse.builder()
                .message("ok")
                .userID(1)
                .build());

        ResponseEntity<LoginResponse> response = userController.login(loginRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * when user set wrong email and password then user unable to login.
     */
    @Test
    public void userLogin_inValidUserDetail_loginUnSuccessfull() throws Exception {

        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@gmail.com")
                .password("password")
                .build();

        Mockito.when(userService.login(any())).thenReturn(LoginResponse.builder()
                .message("ok")
                .userID(1)
                .build());

        ResponseEntity<LoginResponse> response = userController.login(loginRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * when user set right or valid credential(aadhar, PAN, Address, mobileNumber) then user update their profile.
     */
    @Test
    void userProfileUpdate_validUserDetail_userProfileUpdateSuccessfull() throws Exception {
        ProfileRequest profileRequest = ProfileRequest.builder()
                .aadhar("123456554445")
                .pan("asdfghjdf")
                .address("koparkhairne")
                .mobileNumber("9022068607")
                .build();
        ResponseEntity<ProfileResponse> response = userController.updateProfile(1,profileRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * when user set wrong or invalid credential(aadhar, PAN, Address, mobileNumber) then user unable to update their profile.
     */
    @Test
    void userProfileUpdate_inValidUserDetail_userProfileupdateUnSuccessfull() throws Exception {
        ProfileRequest profileRequest = ProfileRequest.builder()
                .aadhar("123456554445")
                .pan("asdfghjdf")
                .address("koparkhairne")
                .mobileNumber("9022068607")
                .build();
        ResponseEntity<ProfileResponse> response = userController.updateProfile(null,profileRequest);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    /**
     * @throws Exception user deleted with userId.
     */
    @Test
    public void deleteProfile_validUserId_deleteProfileSuccessful() throws Exception {

        Integer userID = 1;

        Mockito.when(userService.deleteProfile(any())).thenReturn("user deleted " + userID);

        ResponseEntity<String> response = userController.deleteUser(userID);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }

    /**
     * @throws Exception user id is not found.
     */
    @Test
    public void deleteProfile_userIdNotExist_deleteProfileUnsuccessful() throws Exception {

        Integer userID = 1;

        Mockito.when(userService.deleteProfile(any())).thenReturn("user id is not found");

        ResponseEntity<String> response = userController.deleteUser(userID);
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}