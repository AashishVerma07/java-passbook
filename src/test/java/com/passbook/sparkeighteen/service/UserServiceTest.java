package com.passbook.sparkeighteen.service;

import com.passbook.sparkeighteen.peristence.POJO.Gender;
import com.passbook.sparkeighteen.peristence.POJO.LoginRequest;
import com.passbook.sparkeighteen.peristence.POJO.LoginResponse;
import com.passbook.sparkeighteen.peristence.POJO.ProfileRequest;
import com.passbook.sparkeighteen.peristence.POJO.ProfileResponse;
import com.passbook.sparkeighteen.peristence.POJO.SignUpRequest;
import com.passbook.sparkeighteen.peristence.POJO.SignUpResponse;
import com.passbook.sparkeighteen.peristence.entity.ProfileEntity;
import com.passbook.sparkeighteen.peristence.entity.UserEntity;
import com.passbook.sparkeighteen.peristence.repository.ProfileRepository;
import com.passbook.sparkeighteen.peristence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * The type User service test.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private UserService userService;

    /**
     * Check whether the request is valid and having successfull response then the user signUp is successfull.
     */
    @Test
    public void userSignUp_successResponse_signUpSuccessfull() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .firstname("Ketan")
                .lastname("Shinde")
                .build();

        UserEntity ketan = UserEntity.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .lastname("Shinde")
                .firstname("Ketan")
                .build();

        ProfileEntity ketansProfile = ProfileEntity.builder()
                .user(ketan)
                .age(Period.between(ketan.getDob(), LocalDate.now()).getYears())
                .build();
        Mockito.when(userRepository.save(any())).thenReturn(ketan);
        Mockito.when(userRepository.findByEmail("ketan@gmail.com")).thenReturn(Optional.empty());
        Mockito.when(profileRepository.save(any())).thenReturn(ketansProfile);

        SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        assertEquals("Sign-up is successful.", signUpResponse.getMessage());

    }

    /**
     * check whether request is valid and having error response then the Signup is SignupunSuccessful
     */
    @Test
    public void userSignUp_errorResponse_signUpUnSuccessfull() throws Exception {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .firstname("Ketan")
                .lastname("Shinde")
                .build();

        UserEntity ketan = UserEntity.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .lastname("Shinde")
                .firstname("Ketan")
                .build();

        when(userRepository.findByEmail("ketan@gmail.com")).thenReturn(Optional.of(ketan));

        SignUpResponse signUpResponse = userService.signUp(signUpRequest);
        assertEquals("Email already exists.", signUpResponse.getMessage());

    }

    /**
     * when request is valid and having successful response then the login is successfull.
     */
    @Test
    public void userLogin_successResponse_loginSuccessfull() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ketan@gmail.com")
                .password("password")
                .build();

        UserEntity ketan = UserEntity.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .lastname("Shinde")
                .firstname("Ketan")
                .build();

        ProfileEntity ketansProfile = ProfileEntity.builder()
                .user(ketan)
                .age(Period.between(ketan.getDob(), LocalDate.now()).getYears())
                .build();

        Mockito.when(userRepository.findByEmail("ketan@gmail.com")).thenReturn(Optional.of(ketan));
        Mockito.when(profileRepository.findByUser(ketan)).thenReturn(Optional.ofNullable(ketansProfile));

        LoginResponse response = userService.login(request);
        assertEquals("User login successful", response.getMessage());

    }

    /**
     * when request is valid and having error response then the login is unsuccessfull.
     */
    @Test
    public void userLogin_userNotExists_loginUnSuccessfull() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ketan@gmail.com")
                .password("password")
                .build();

        when(userRepository.findByEmail("ketan@gmail.com")).thenReturn(Optional.empty());

        LoginResponse response = userService.login(request);
        assertEquals("User not registered with provided email", response.getMessage());

    }

    /**
     * when request is valid and having error response then the login is unsuccessfull.
     */
    @Test
    public void userLogin_passwordMismatch_loginUnSuccessfull() throws Exception {
        LoginRequest request = LoginRequest.builder()
                .email("ketan@gmail.com")
                .password("something")
                .build();

        UserEntity ketan = UserEntity.builder()
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .lastname("Shinde")
                .firstname("Ketan")
                .build();

        when(userRepository.findByEmail("ketan@gmail.com")).thenReturn(Optional.ofNullable(ketan));

        LoginResponse response = userService.login(request);
        assertEquals("Enter valid password to continue", response.getMessage());

    }

    /**
     * When request is getting null then it throws error.
     */
    @Test
    void userProfileUpdtae_errorResponse_userProfileUpdateUnSuccessfull() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .aadhar("123456554445")
                .pan("asdfghjdf")
                .address("koparkhairne")
                .mobileNumber("9022068607")
                .build();

        ProfileResponse response = userService.updateProfile(1, request);
        assertEquals("User ID is missing. Retry with registered user", response.getMessage());
    }

    /**
     * @throws Exception user id is not found.
     */
    @Test
    public void deleteProfile_userIdNotFound_deleteProfileUnsuccessful() throws Exception {
        Integer userID = 1;

        when(userRepository.findById(userID)).thenReturn(Optional.empty());

        String response = userService.deleteProfile(userID);
        assertEquals("user id is not found", response);

    }

    /**
     * In this part we check the request is valid and getting successfull response then the Profile is Updated successfully.
     */
    @Test
    void userProfileUpdate_successResponse_userProfileUpdateSuccessfull() throws Exception {
        ProfileRequest request = ProfileRequest.builder()
                .aadhar("23456789875")
                .pan("567456765")
                .address("Vashi")
                .mobileNumber("9922567842")
                .build();

        UserEntity ketan = UserEntity.builder()
                .id(1)
                .email("ketan@gmail.com")
                .dob(LocalDate.of(2000, 10, 6))
                .password("password")
                .gender(Gender.MALE)
                .lastname("Shinde")
                .firstname("Ketan")
                .build();

        ProfileEntity ketansProfile = ProfileEntity.builder()
                .user(ketan)
                .age(Period.between(ketan.getDob(), LocalDate.now()).getYears())
                .build();

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(ketan));
        Mockito.when(profileRepository.findByUser(ketan)).thenReturn(Optional.ofNullable(ketansProfile));
        ProfileResponse profileResponse = userService.updateProfile(1, request);
        assertEquals("Profile Updated Successfully", profileResponse.getMessage());
    }

    /**
     * @throws Exception user deleted with userId.
     */
    @Test
    public void deleteProfile_userIdExist_deleteProfileSuccessful() throws Exception {
        Integer userID = 1;

        UserEntity user = UserEntity.builder()
                .email("hrishi@gmail.com")
                .dob(LocalDate.of(2000, 2, 25))
                .password("qwerty")
                .gender(Gender.MALE)
                .lastname("Shedge")
                .firstname("Hrishikesh")
                .build();
        when(userRepository.findById(userID)).thenReturn(Optional.ofNullable(user));

        String response = userService.deleteProfile(userID);
        assertEquals("user deleted " + userID, response);

    }

}