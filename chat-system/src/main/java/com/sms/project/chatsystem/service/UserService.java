package com.sms.project.chatsystem.service;

import com.sms.project.chatsystem.entity.User;
import com.sms.project.chatsystem.exception.UserExistException;
import com.sms.project.chatsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;


    public void addAccount(User user) {
        if(isUserExist(user.getUsername()))
                throw new UserExistException("Username already exist");
        userRepository.save(user);
    }

    public boolean isUserExist(String username) {
        Optional<User> isUser = userRepository.findById(username);
        return isUser.isPresent();
    }

    public void removeUser(String userId) {
        userRepository.deleteById(userId);
    }

}