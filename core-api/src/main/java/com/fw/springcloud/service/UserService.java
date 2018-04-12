package com.fw.springcloud.service;

import com.fw.springcloud.pojo.User;

import java.util.List;

public interface UserService {

    Boolean saveUser(User user);

    List<User> getUserList();
}
