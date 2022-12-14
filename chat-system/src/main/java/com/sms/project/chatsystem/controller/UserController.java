package com.sms.project.chatsystem.controller;

import com.sms.project.chatsystem.constant.ReturnCode;
import com.sms.project.chatsystem.entity.User;
import com.sms.project.chatsystem.exception.UserExistException;
import com.sms.project.chatsystem.service.NotificationService;
import com.sms.project.chatsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*",  maxAge = 3600)
@RestController
@RequestMapping("/login")
public class UserController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

        //notificationService.sendEmailMessage(user);

        if(userService.isUserExist(user.getUsername()))
                throw new UserExistException("User already exist");

        HttpHeaders headers = new HttpHeaders();
        headers.add("OTP", notificationService.getOTP_NUMPER());

        HashMap<String, Object> map = new HashMap<>();
        map.put("message", "OTP sent to your mail ID");
        map.put("returnCode", ReturnCode.SUCCESS);

        return new ResponseEntity<>(map, headers, HttpStatus.ACCEPTED);
    }

    @PostMapping("/submit")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        userService.addAccount(user);

        HashMap<String, Object> map = new HashMap<>();
        map.put("message", user);
        map.put("returnCode", ReturnCode.SUCCESS);

        return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
    }



}
