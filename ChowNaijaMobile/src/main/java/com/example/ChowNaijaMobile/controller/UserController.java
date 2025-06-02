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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (userRepro.findByUsername(user.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Username '" + user.getUsername() + "' is already taken");
            }

            if (userRepro.findFirstByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Email " + user.getEmail() + " is already registered");
            }

            User savedUser = userDetailsService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User dbUser = userRepro.findByUsername(user.getUsername());
            if (dbUser == null) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));


            if(dbUser.getAccountFlaged()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access Denied!! Your Account is Flagged");
            }
            if (authentication.isAuthenticated() && !dbUser.getAccountFlaged()) {
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", jwtService.generateAccessToken(user.getUsername()));
                tokens.put("refresh_token", jwtService.generateRefreshToken(user.getUsername()));
                return ResponseEntity.ok(tokens);
            } else {
                return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refresh_token");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh token is required");
        }

        try {
            // Extract username from refresh token (allows expired tokens)
            String username = jwtService.extractUsernameFromExpiredToken(refreshToken);

            // Verify user still exists
            User user = userRepro.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            // Verify refresh token is valid (not expired)
            if (jwtService.isTokenExpired(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired");
            }

            // Generate new access token
            String newAccessToken = jwtService.generateAccessToken(username);

            // Optionally generate new refresh token (rotation)
            String newRefreshToken = jwtService.generateRefreshToken(username);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", newAccessToken);
            tokens.put("refresh_token", newRefreshToken);

            return ResponseEntity.ok(tokens);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<?> editUser (@PathVariable int id, @RequestBody User user){
        try {
            User editedUser = userDetailsService.editUser(id,user.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body(editedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser (@PathVariable int id){
        try {
            User foundUser = userDetailsService.getUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundUser);
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe(Authentication authentication){
        try {
            String userName = authentication.getName();
            User user = userRepro.findByUsername(userName);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }


    }

    @PostMapping("/flagUser/{id}")
    public ResponseEntity<?> flagUser (@PathVariable int id){
        try {
            userDetailsService.flagUser(id);
            return ResponseEntity.status(HttpStatus.GONE).body("UserFlagged For the Next 30Days");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/unFlagUser")
    public ResponseEntity<?> unFlagUser (@RequestBody User user){
        try {
          User dbUser = userRepro.findByUsername(user.getUsername());
          if(dbUser == null){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(user.getUsername() + "Not Found In Our Database");
          }

          Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

          if(authentication.isAuthenticated()){
              userDetailsService.unflagUser(user.getUsername());
              return ResponseEntity.status(HttpStatus.OK).body("User Unflagged");
          } else {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Authorized to Perform This Action");
          }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}