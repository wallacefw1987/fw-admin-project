package com.fw.springcloud.web.controller;

import com.fw.springcloud.service.UserService;
import com.fw.springcloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserClientController implements UserService {

    @Autowired
    UserService userService;
    @Override
    public Boolean saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @Override
    public List<User> getUserList() {
        return userService.getUserList();
    }
}
