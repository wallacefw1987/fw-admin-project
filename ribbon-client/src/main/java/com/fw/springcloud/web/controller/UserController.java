package com.fw.springcloud.web.controller;


import com.fw.springcloud.hystrix.MyHystrixCommad;
import com.fw.springcloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class UserController {

    /**
     * 负载均衡器客户端
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Value("${provider.service.name}")
    private String providerServiceName;
    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/")
    public String index() throws IOException{
        User user = new User();
        user.setId(10);
        user.setName("Luka");
        //选定需要的服务名
        ServiceInstance serviceInstance =  loadBalancerClient.choose(providerServiceName);
        return loadBalancerClient.execute(providerServiceName,serviceInstance,instance -> {
            //服务器实例，获取 主机名（IP） 和 端口
            String host = instance.getHost();
            int port = instance.getPort();
            String url = "http://" + host + ":" + port + "/user/save";
            RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(url, user, String.class);
        });
    }

    @GetMapping("/users")
    public Object getUsers(){
        return new MyHystrixCommad(providerServiceName,restTemplate).execute();
    }

}