package com.SmartContactManager.Config;

import com.SmartContactManager.dao.UserRepo;
import com.SmartContactManager.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usr=userRepo.getUserByUserName(username);
        if(usr==null){
            throw new UsernameNotFoundException("User name was not found!!");
        }
        CustomUserDetails customerUserDetails=new CustomUserDetails(usr);
        return customerUserDetails;
    }
}
