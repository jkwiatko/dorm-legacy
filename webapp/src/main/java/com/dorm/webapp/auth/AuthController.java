package com.dorm.webapp.auth;

import com.dorm.webapp.auth.jwt.Credentials;
import com.dorm.webapp.auth.jwt.JwtProvider;
import com.dorm.webapp.auth.jwt.Token;
import com.dorm.webapp.data.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Credentials credentials) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        System.out.println(jwt);
        return ResponseEntity.ok(new Token(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Credentials credentials) {
        userService.addUser(credentials);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
