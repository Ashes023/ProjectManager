package com.ashahar.projectmanagementsystem.repo;

import com.ashahar.projectmanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByEmail(String email); 
}
