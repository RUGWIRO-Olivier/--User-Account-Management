package com.userProfileManagementApi.controller;

import com.userProfileManagementApi.exceptions.domain.*;
import com.userProfileManagementApi.models.HttpResponse;
import com.userProfileManagementApi.models.SignupResponse;
import com.userProfileManagementApi.models.User;
import com.userProfileManagementApi.models.UserPrincipal;
import com.userProfileManagementApi.exceptions.ExceptionHandling;
import com.userProfileManagementApi.service.UserService;
import com.userProfileManagementApi.util.JWTTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import static com.userProfileManagementApi.constant.FileConstant.*;
import static com.userProfileManagementApi.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping(path = { "/user"})
public class UserController extends ExceptionHandling {
    public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    public static final String PASSWORD_CHANGED_SUCCESSFULLY = "Password Changed successfully";
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Login to account")
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @Operation(summary = "Register an account")
    @PostMapping("/register")
    public ResponseEntity<SignupResponse> register(@RequestBody @Valid User user) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException {
        SignupResponse response = SignupResponse.builder()
                .user(userService.register(user.getNid(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getDob(),user.getAge(), user.getGender(), user.getMartialStatus(), user.getNationality()))
                .message("Sign Up Complete!")
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    @Operation(summary = "Update Information")
    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("nid") String nid,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("dob") String dob,
                                       @RequestParam("age") Integer age,
                                       @RequestParam("gender") String gender,
                                       @RequestParam("martialStatus") String martialStatus,
                                       @RequestParam("nationality") String nationality,
                                       @RequestParam("role") String role,
                                       @RequestParam("status") String status,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User updatedUser = userService.updateUser(currentUsername,nid, firstName, lastName, username, email, dob,age, gender,martialStatus, nationality, role, status,Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }

    @Operation(summary = "Find by username")
    @GetMapping("/find/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserbyId(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return new ResponseEntity<>(user, OK);
    }

    @Operation(summary = "List all users")
    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @Operation(summary = "Change Password")
    @PostMapping("/change-password")
    public ResponseEntity<HttpResponse> changePassword( @RequestParam("currentUsername") String currentUsername,
                                                       @RequestParam("oldPassword") String oldPassword,
                                                        @RequestParam("newPassword") String  newPassword) throws MessagingException, UserNotFoundException, IncorrectPasswordException {
        userService.changePassword(currentUsername, oldPassword, newPassword);
        return response(OK, PASSWORD_CHANGED_SUCCESSFULLY);
    }

    @Operation(summary = "Reset account Password by Using Email")
    @GetMapping("/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException, IOException {
        userService.resetPassword(email);
        return response(OK, EMAIL_SENT + email);
    }

    @Operation(summary = "Delete by using username")
    @DeleteMapping("/delete/{username}")
//    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        userService.deleteUser(username);
        return response(OK, USER_DELETED_SUCCESSFULLY);
    }

    @Operation(summary = "Update profile image")
    @PostMapping("/updateProfileImage")
    public ResponseEntity<User> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException {
        User user = userService.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @Operation(summary = "Get a profile image")
    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }


    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SignupResponse handlePasswordValidationException(MethodArgumentNotValidException e) {

        //Returning password error message as a response.
        return SignupResponse.builder()
                .message(String.join(",", e.getBindingResult().getFieldError().getDefaultMessage()))
                .timestamp(Instant.now())
                .build();

    }
}
