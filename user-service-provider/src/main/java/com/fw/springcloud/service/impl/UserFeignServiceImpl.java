package com.fw.springcloud.service.impl;

import com.fw.springcloud.pojo.User;
import com.fw.springcloud.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 根据Feign重新设置UserService实体类
 */
@Service("myUserService")
public class UserFeignServiceImpl implements UserService {

    private static HashMap<Integer,Object> resultMap = new HashMap<>();
    @Override
    public Boolean saveUser(User user) {
        return resultMap.put(user.getId(),user) == null;
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList(resultMap.values());
    }
}
