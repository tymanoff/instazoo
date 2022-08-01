package com.example.demo.services;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.emums.ERole;
import com.example.demo.exceptions.UserExistException;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.payload.request.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignupRequest userId){
        User user = new User();
        user.setEmail(userId.getEmail());
        user.setName(userId.getFirstname());
        user.setLastname(userId.getLastname());
        user.setUsername(userId.getUsername());
        user.setPassword(passwordEncoder.encode(userId.getPassword()));
        user.getRoles().add(ERole.ROLE_USER);

        try{
            LOG.info("Saving User {}", userId.getEmail());
            return userRepository.save(user);
        } catch (Exception e){
            LOG.error("Error during registration. {}", e.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTD, Principal principal){
        User user = getUserByPrincipal(principal);
        user.setName(userDTD.getFirstname());
        user.setLastname(userDTD.getLastname());
        user.setBio(userDTD.getBio());

        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

}
