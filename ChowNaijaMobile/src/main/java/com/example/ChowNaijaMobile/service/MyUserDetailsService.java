package com.example.ChowNaijaMobile.service;

import com.example.ChowNaijaMobile.dao.UserPrincipal;
import com.example.ChowNaijaMobile.dao.UserRepro;
import com.example.ChowNaijaMobile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepro userRepro;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepro.findByUsername(username);
//        System.out.println(user);
        if(user == null){
            throw new UsernameNotFoundException("404 User not Found");

        }
        return new UserPrincipal(user);
    }

   private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public User registerUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepro.save(user);
    }

}
