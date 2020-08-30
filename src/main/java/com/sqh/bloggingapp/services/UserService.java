package com.sqh.bloggingapp.services;

import com.sqh.bloggingapp.models.User;
import com.sqh.bloggingapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final String ROLE_USER = "ROLE_USER";
    private final String ROLE_ADMIN = "ROLE_ADMIN";

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user){
        user.setActive(false);
        user.setRoles(ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(int user_id){
        return userRepository.findById(user_id);
    }

    public List<User> findAllByRoles(String role){
        return userRepository.findAllByRoles(role);
    }

    public void saveAdminUser(User admin){
        admin.setActive(true);
        admin.setRoles(ROLE_ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        userRepository.saveAndFlush(admin);
    }

    public User approveUserAccount(User user){
        user.setActive(true);
        return userRepository.save(user);
    }

    public User deActivateUserAccount(User user){
        user.setActive(false);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
