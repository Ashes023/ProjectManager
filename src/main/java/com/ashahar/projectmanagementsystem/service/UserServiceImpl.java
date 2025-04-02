package com.ashahar.projectmanagementsystem.service;

import com.ashahar.projectmanagementsystem.config.JwtProvider;
import com.ashahar.projectmanagementsystem.model.User;
import com.ashahar.projectmanagementsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {

        String email = JwtProvider.getEmailFromToken(jwt);

        return findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepo.findByEmail(email);
        if(user == null)
            throw new Exception("User not found");

        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {

        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty())
            throw new Exception("User not found");

        return user.get();
    }

    @Override
    public User updatesUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepo.save(user);
    }
}
