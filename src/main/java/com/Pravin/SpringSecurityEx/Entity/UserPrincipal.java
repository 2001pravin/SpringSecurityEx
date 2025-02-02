package com.Pravin.SpringSecurityEx.Entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private User user;

  public UserPrincipal(User user){
      this.user= user;
   }
   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
   return Collections.singleton( new SimpleGrantedAuthority("User"));
    }

    @Override
    public String getPassword() {
        // System.out.println("pop "+getPassword());
        return user.getPassword();
        
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
