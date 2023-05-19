package com.userProfileManagementApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.userProfileManagementApi.anotations.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String nid;
    @NotEmpty(message = "First Name is Required")
    private String firstName;
    @NotEmpty(message = "Last Name is Required")
    private String lastName;
    @NotEmpty(message = "Username is Required")
    private String username;
    @Password
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Email(message = "Invalid Email Address",regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @NotEmpty(message = "Date of Birth is Required")
    private  String dob;
    private Integer age;
    @NotEmpty(message = "Gender is Required")
    private String gender;
    @NotEmpty(message = "Martial Status is Required")
    private String martialStatus;
    @NotEmpty(message = "Nationality is Required")
    private String nationality;  //This can be SINGLE, MARRIED, DIVORCED or WIDOWED
    private String account_status="PENDING VERIFICATION"; //This can be UNVERIFIED, PENDING VERIFICATION, VERIFIED
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String role;
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;

}
