package com.fw.springcloud.web;

import com.fw.springcloud.pojo.User;
import com.fw.springcloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class UserController implements UserService {

    //新版本 -- 使用myUserService
    @Autowired
    @Qualifier("myUserService")
    private UserService userService;

    private static final Random radom = new Random();

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public Boolean saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @Override
    @HystrixCommand( //配置
            commandProperties = {
                    //设置超时时间，name的报错可以查找地址：https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.thread.timeoutInMilliseconds 中有相关数据
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
            },
            fallbackMethod = "fallbackForGetUser"
    )
    public List<User> getUserList() {
        sleep();
        return userService.getUserList();
    }

    private void sleep(){
        Integer sleepTime = new Random().nextInt(200);
        try {
            logger.info("--- sleep time :"+sleepTime);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("user/list")
    @HystrixCommand( //配置
         commandProperties = {
                 //设置超时时间，name的报错可以查找地址：https://github.com/Netflix/Hystrix/wiki/Configuration#execution.isolation.thread.timeoutInMilliseconds 中有相关数据
                 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
         },
          fallbackMethod = "fallbackForGetUser"
    )
    public List<User> getMyList() {
        return userService.getUserList();
    }

    List<User> fallbackForGetUser(){
        return Collections.emptyList();
    }
}
