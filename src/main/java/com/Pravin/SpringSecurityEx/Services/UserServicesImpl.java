package com.Pravin.SpringSecurityEx.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Pravin.SpringSecurityEx.Entity.User;
import com.Pravin.SpringSecurityEx.repo.IUserRepo;
@Service
public class UserServicesImpl implements IUserServices {
@Autowired
   private IUserRepo iUserRepo;
@Autowired
    AuthenticationManager authenticationManager;
@Autowired
   public JwtImp jwtImp;

   private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();

    @Override
    public User register(User user) {
        System.out.println(encoder.encode(user.getPassword()));
        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return iUserRepo.save(user);
    }

    public String verify(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
   if (authentication.isAuthenticated()) {
         return jwtImp.generateToken(user.getUsername())  ;
        } else {
            return "fail";
        }
    }

}
