package com.dorm.backend.shared.service;

import com.dorm.backend.auth.UserPrincipal;
import com.dorm.backend.auth.jwt.Credentials;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.repo.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    UserService(
            ModelMapper modelMapper,
            UserRepository userRepository
    ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public User getCurrentAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof UserPrincipal)) {
            throw new InsufficientAuthenticationException("Couldn't not retrieve currently logged in user");
        }
        return getUser(((UserPrincipal) principal).getId());
    }

    public void addUser(Credentials credentials) {
        User userToAdd = modelMapper.map(credentials, User.class);
        userRepository.save(userToAdd);
    }

    public User getUser(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User of id: %s not found",id)));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
