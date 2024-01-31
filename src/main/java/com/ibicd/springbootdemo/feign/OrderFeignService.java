package com.ibicd.springbootdemo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-order",path = "/order",fallbackFactory = OrderFeignServiceFactory.class)
public interface OrderFeignService {

    @RequestMapping("/findOrderByUserId/{userId}")
    public String findOrderByUserId(@PathVariable("userId") Integer userId);
}
