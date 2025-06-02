package com.example.ChowNaijaMobile.service;

import com.example.ChowNaijaMobile.dao.UserPrincipal;
import com.example.ChowNaijaMobile.dao.UserRepro;
import com.example.ChowNaijaMobile.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    // Fetching a Single User
    public User getUser(int id){
        return userRepro.findById(id).orElse(null);
    }

    // Edit User Algorithm
    // First Find the user by Id
    // Check if it does exist
    // Try to retrive the userName of the found User
    // Set the found user to the username of the submitted User
    // Save to DataBase **** WOW This is Rubbish


    // Edit User Algorithm
    // Fetch Single user from DataBase
    // Check for Null
    // Update the user by Setter Method
    // Save the update User Object
    // Return User

    public User editUser(int id, String newUserName){
        User user = userRepro.findById(id).orElse(null);
        if(user != null){
            user.setUsername(newUserName);
            userRepro.save(user);
        }
        return user;
    }



    public String flagUser(int id){
        User user = userRepro.findById(id).orElse(null);
        if(user != null){
            user.setAccountFlaged(true);
            user.setFlagDate(LocalDateTime.now());

            userRepro.save(user);
        }

        return "User Flagged for deletion in 30 days";
    }

    public String unflagUser (String userName){
        User user= userRepro.findByUsername(userName);
        if(user != null){
            user.setAccountFlaged(false);
            user.setFlagDate(null);

            userRepro.save(user);
        }
        return "User Unflagged, Account Recovered";
    }





    // DeleteFlagged Users
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFlaggedUsersAfter30Days(){
        LocalDateTime thirdDaysAgo = LocalDateTime.now().minusDays(30);
        List<User> usersToDelete = (List<User>) userRepro.findByIsAccountFlagedTrueAndFlagDateBefore(thirdDaysAgo);

        usersToDelete.forEach(userTodelete -> userRepro.delete(userTodelete));

    }


}
