package com.example.ChowNaijaMobile.controller;

import com.example.ChowNaijaMobile.dao.UserRepro;
import com.example.ChowNaijaMobile.model.User;
import com.example.ChowNaijaMobile.service.JwtService;
import com.example.ChowNaijaMobile.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserRepro userRepro;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try {
            if(userRepro.findByUsername(user.getUsername()) != null){
                return  ResponseEntity.status(HttpStatus.CONFLICT).body("Username ' " + user.getUsername()  + " ' isAlready Taken ");
            }

            if(userRepro.findFirstByEmail(user.getEmail()) != null){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username with " + user.getEmail() + "is Already Registerd");
            }
            User savedUser = userDetailsService.registerUser(user);
            return new ResponseEntity<>(savedUser,HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User dbUser = userRepro.findByUsername(user.getUsername());
            System.out.println(dbUser);
            if(dbUser == null){
                return new ResponseEntity<>("User not Found", HttpStatus.NOT_FOUND);
            }

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if(authentication.isAuthenticated()){
                return  ResponseEntity.ok(jwtService.generateToken(user.getUsername()));
            } else{
                return new ResponseEntity<>("Invalid Credentials", HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
