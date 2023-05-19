package com.userProfileManagementApi.serviceImpl;

import com.userProfileManagementApi.models.User;
import com.userProfileManagementApi.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Date;
import static com.userProfileManagementApi.enumeration.Role.ROLE_ADMIN;

@Component
public class StartUp {
    private final Logger Log = LoggerFactory.getLogger(StartUp.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @PostConstruct
    public void init() {
        System.out.println("-----------> Initializing variables");
        initAdminCreation();
        System.out.println("-----------> Completion of Admin Creation Initialization");
    }


    private void initAdminCreation() {
        User user = new User(0L, "1111111111111111",  "Admin", "","admin",encodePassword("admin"),"admin@admin.com",null, 0,"",null,null,"VERIFIED", "/user/image/profile/admin",new Date(), new Date(), new Date(),ROLE_ADMIN.name(),ROLE_ADMIN.getAuthorities(),true,true);

     try {
         if (userRepository.findUserByUsername("admin")==null){
             userRepository.save(user);}
                else{
             System.out.println("User Admin Already Exist");}

        } catch (Exception e) {
            Log.info(e.getMessage());
            e.printStackTrace();
        }

    }




}
