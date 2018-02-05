package com.sunhao.graduate_project.repository;

import com.sunhao.graduate_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
