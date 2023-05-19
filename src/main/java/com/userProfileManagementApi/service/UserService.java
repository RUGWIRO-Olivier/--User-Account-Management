package com.userProfileManagementApi.service;

import com.userProfileManagementApi.exceptions.domain.*;
import com.userProfileManagementApi.models.User;
import org.springframework.web.multipart.MultipartFile;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface UserService {

    User register(String nid, String firstName, String lastName, String username, String password, String email, String dob, Integer age, String gender , String martialStatus, String nationality) throws UserNotFoundException, UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User getUser(Long id);

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User updateUser(String currentUsername, String newNid,String newFirstName, String newLastName, String newUsername, String newEmail, String newDob, Integer newAge, String newGender, String newMartialStatus,String newNationality , String role, String status ,boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;

    void changePassword(String username, String oldPassword, String newPassword) throws MessagingException, UserNotFoundException, IncorrectPasswordException;

    void resetPassword(String email) throws MessagingException, EmailNotFoundException, IOException;

    User updateProfileImage(String username, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;
}
