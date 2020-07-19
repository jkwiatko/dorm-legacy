package com.dorm.backend.auth;

import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

   private UserRepository repository;

   MyUserDetailsService(UserRepository repository){
       this.repository = repository;
   }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword());
    }

    public UserDetails loadUserById(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User of id: " + userId + " not found."));

        return new UserPrincipal(userId, user.getEmail(), user.getPassword());
    }
}
