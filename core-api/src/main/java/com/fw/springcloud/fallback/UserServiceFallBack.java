package com.fw.springcloud.fallback;

import com.fw.springcloud.pojo.User;
import com.fw.springcloud.service.UserService;
import java.util.ArrayList;
import java.util.List;

public class UserServiceFallBack implements UserService {
    @Override
    public Boolean saveUser(User user) {
        return Boolean.FALSE;
    }

    @Override
    public List<User> getUserList() {
        return new ArrayList<User>();
    }
}
