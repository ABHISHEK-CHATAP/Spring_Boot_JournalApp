package com.JournalApp.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.JournalApp.entity.User;
import com.JournalApp.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements  UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDB = userRepository.findByUsername(username);
        if(userInDB != null){
            return org.springframework.security.core.userdetails.User.builder()
                .username(userInDB.getUsername())
                .password(userInDB.getPassword())
                .roles(userInDB.getRoles().toArray(new String[0]))
                .build();
        }
        throw new UsernameNotFoundException("User not found with username " + userInDB.getUsername());
    }

    

}
