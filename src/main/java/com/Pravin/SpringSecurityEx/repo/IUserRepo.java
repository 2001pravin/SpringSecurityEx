package com.Pravin.SpringSecurityEx.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pravin.SpringSecurityEx.Entity.User;
@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {
   User findByUsername(String username);
}
