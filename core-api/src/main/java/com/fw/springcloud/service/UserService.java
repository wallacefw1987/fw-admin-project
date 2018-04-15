package com.fw.springcloud.service;

import com.fw.springcloud.fallback.UserServiceFallBack;
import com.fw.springcloud.pojo.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "${user.service.name}",fallback = UserServiceFallBack.class)
public interface UserService {

    @PostMapping(value = "/user/save")
    Boolean saveUser(User user);

    @GetMapping("/users")
    List<User> getUserList();
}
