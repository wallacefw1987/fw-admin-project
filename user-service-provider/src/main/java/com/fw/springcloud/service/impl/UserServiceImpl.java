package com.fw.springcloud.service.impl;

import com.fw.springcloud.pojo.User;
import com.fw.springcloud.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final HashMap<Integer,User> userMap = new HashMap<>();
    @Override
    public Boolean saveUser(User user) {
        return (userMap.put(user.getId(),user) == null);
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<User>(userMap.values());
    }
}
