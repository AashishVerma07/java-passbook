package com.passbook.sparkeighteen.peristence.POJO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Profile request is for taking user credential(like mobileNumber, address, PAN, aadhar) to update userprofile.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {

    @Pattern(regexp = "^([+]\\d{2})?\\d{10}$", message = "mobile number is not valid")
    private String mobileNumber;

    @NotBlank(message = "address is mandatory")
    private String address;

    @Pattern(regexp = "[A-Z]{3}[ABCFGHLJPTF]{1}[A-Z]{1}[0-9]{4}[A-Z]{1}$", message = "PAN number is not valid")
    private String pan;

    @Pattern(regexp = "^\\d{4}\\s\\d{4}\\s\\d{4}$", message = "aadhar number is not valid")
    private String aadhar;
}
