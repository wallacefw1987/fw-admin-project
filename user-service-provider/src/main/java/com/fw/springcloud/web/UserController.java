package com.fw.springcloud.web;

import com.fw.springcloud.pojo.User;
import com.fw.springcloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private static final Random radom = new Random();

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/user/save")
    public boolean user(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/user/list")
    @HystrixCommand( //配置
         commandProperties = {
                 //设置超时时间，name的报错可以查找地址：https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.thread.timeoutInMilliseconds 中有相关数据
                 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
         },
          fallbackMethod = "fallbackForGetUser"
    )
    public Collection<User> getUserList() throws InterruptedException {
        long currentTime = radom.nextInt(200);
        logger.info(this.getClass().getMethods()[0].getName() + "-- sleeping :\t"+currentTime+" ms");
        Thread.sleep(currentTime);
        return userService.getUserList();
    }

    Collection<User> fallbackForGetUser(){
        return Collections.emptyList();
    }
}
