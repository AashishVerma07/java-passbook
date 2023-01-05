package com.passbook.sparkeighteen.service;

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
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

/**
 * user service have all business logic, service get operations like ( signup, login, updateProfile, deleteProfile) from controller and perform it then give values return to controller.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    /**
     * user service get operation from user controller and perform then return response.
     * @param userRepository    the user repository is communicate to database for any operation like signup and login.
     * @param profileRepository the profile repository is communicate to database for any operation for user profile update.
     */
    public UserService(final UserRepository userRepository, final ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    /**
     * Usr login get operation from userController then perform the operation then get values helps to loginRequest and get back response helps to loginResponse.
     * @param userLogin to get the credentials of the user from the repository.
     * @return the login response of the action performed.
     * @throws Exception the exception gives error for wrong input or bad request.
     */
    public LoginResponse login(final LoginRequest userLogin) throws Exception {
        Optional<UserEntity> optionalUser = userRepository
                .findByEmail(userLogin.getEmail());
        if (optionalUser.isEmpty()) {
            return LoginResponse.builder()
                    .message("User not registered with provided email")
                    .build();
        } else {
            UserEntity userEntity = optionalUser.get();
            if (!userEntity.getPassword().equals(userLogin.getPassword())) {
                return LoginResponse.builder()
                        .message("Enter valid password to continue")
                        .build();
            }

        }
        UserEntity user = optionalUser.get();
        Optional<ProfileEntity> optionalProfile = profileRepository.findByUser(user);

        if (optionalProfile.isEmpty())
            return LoginResponse.builder()
                    .userID(user.getId())
                    .message("Profile for UserID doesn't exist. Make sure User is registered.")
                    .build();

        ProfileEntity profile = optionalProfile.get();

        return LoginResponse.builder()
                .userID(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .age(calculateAge(user.getDob()))
                .aadhar(profile.getAadhar())
                .pan(profile.getPan())
                .gender(user.getGender())
                .message("User login successful")
                .build();
    }

    /**
     * Usr SigunUp get operation from userSignUpController then perform the operation then get values helps to signUpRequest and get back response helps to sigunUpResponse.
     * @param request is for get user credential to create profile.
     * @return the sign up response of the action performed.
     */
    public SignUpResponse signUp(@NonNull final SignUpRequest request) {
        final Optional<UserEntity> user =
                userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            return SignUpResponse.builder()
                    .message("Email already exists.")
                    .build();
        }

        UserEntity savedUser = userRepository.save(UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .gender(request.getGender())
                .dob(request.getDob())
                .email(request.getEmail())
                .password(request.getPassword())
                .build());

        ProfileEntity profile = profileRepository.save(ProfileEntity.builder()
                .user(savedUser)
                .age(calculateAge(savedUser.getDob()))
                .build());

        return SignUpResponse.builder()
                .userID(profile.getUser().getId())
                .message("Sign-up is successful.")
                .build();

    }

    private Integer calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    /**
     * Usr updateProfile get operation from updateProfileController then perform the operation then get values helps to profileRequest and get back response helps to profileResponse.
     * @param userID  the user id help us to update specific user.
     * @param request the request for user details to update there profile.
     * @return the profile response is user profile update successfully or userID is missing.
     */
    public ProfileResponse updateProfile(Integer userID, ProfileRequest request) {
        Optional<UserEntity> optionalUser = userRepository.findById(userID);
        if (optionalUser.isEmpty()) {
            return ProfileResponse.builder()
                    .message("User ID is missing. Retry with registered user")
                    .userID(userID)
                    .build();
        }

        final UserEntity user = optionalUser.get();
        Optional<ProfileEntity> optionalProfile = profileRepository.findByUser(user);
        if (optionalProfile.isEmpty()) {
            return ProfileResponse.builder()
                    .message(String.format("Profile for user %s with UserID: %d is missing", user.getFirstname(), user.getId()))
                    .build();
        }

        ProfileEntity profile = optionalProfile.get();
        profile.setAadhar(request.getAadhar() != null ? request.getAadhar() : profile.getAadhar());
        profile.setPan(request.getPan() != null ? request.getPan() : profile.getPan());
        profile.setMobileNumber(request.getMobileNumber() != null ? request.getMobileNumber() : profile.getMobileNumber());
        profile.setAddress(request.getAddress() != null ? request.getAddress() : profile.getAddress());
        profileRepository.save(profile);

        return ProfileResponse.builder()
                .userID(profile.getUser().getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .age(profile.getAge())
                .gender(profile.getUser().getGender())
                .mobileNumber(profile.getMobileNumber())
                .aadhar(profile.getAadhar())
                .pan(profile.getPan())
                .address(profile.getAddress())
                .message("Profile Updated Successfully")
                .build();
    }

    /**
     * @param userId to delete that specific user profile.
     * @return user deleted or user id is not found.
     */
    public String deleteProfile(Integer userId) {
        final Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isPresent()) {
            userRepository.deleteById(userId);
            return "user deleted " + userId;
        }
        return "user id is not found";
    }

}
