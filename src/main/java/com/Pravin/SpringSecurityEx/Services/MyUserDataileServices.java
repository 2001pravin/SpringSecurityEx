package com.Pravin.SpringSecurityEx.Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Pravin.SpringSecurityEx.Entity.User;
import com.Pravin.SpringSecurityEx.Entity.UserPrincipal;
import com.Pravin.SpringSecurityEx.repo.IUserRepo;
@Service
public class MyUserDataileServices  implements UserDetailsService {
@Autowired
     private IUserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     User user=    userRepo.findByUsername(username);
     if (user==null) {
        throw new UsernameNotFoundException("User not found");

     }
         return new UserPrincipal(user);
    }

}
