package com.Pravin.SpringSecurityEx.Services;


import com.Pravin.SpringSecurityEx.Entity.User;

public interface IUserServices {

    public User register(User user);
    public String verify(User user);
}
