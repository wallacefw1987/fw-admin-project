package com.fw.springcloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import java.util.Collection;
import java.util.Collections;

/**
 * 使用代码实现
 */
public class MyHystrixCommad extends HystrixCommand<Object> {

    private String providerServiceName;

    private RestTemplate restTemplate;

    public MyHystrixCommad(String providerServiceName,RestTemplate restTemplate) {
        super(HystrixCommandGroupKey.Factory.asKey("service-provider-key"),200);
        this.providerServiceName = providerServiceName;
        this.restTemplate = restTemplate;
    }

    @Override
    protected Object run() throws Exception {
        System.out.println("---- run -------");

        return this.restTemplate.getForObject("http://"+providerServiceName+"/user/list", Collection.class);
    }

    @Override
    protected Object getFallback(){
        System.out.println("---------getFallback----------");
        return Collections.emptyList();
    }
}
