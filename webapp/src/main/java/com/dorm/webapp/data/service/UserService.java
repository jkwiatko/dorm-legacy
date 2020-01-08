package com.dorm.webapp.data.service;

import com.dorm.webapp.auth.UserPrincipal;
import com.dorm.webapp.auth.jwt.Credentials;
import com.dorm.webapp.data.entity.User;
import com.dorm.webapp.data.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    // TODO think about refactor
    public User getCurrentAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserPrincipal) {
            return getUser(((UserPrincipal) principal).getId());
        }
        return null;
    }

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public boolean addUser(Credentials credentials) {
        User userToAdd = modelMapper.map(credentials, User.class);
        userRepository.save(userToAdd);
        return true;
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void  deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
