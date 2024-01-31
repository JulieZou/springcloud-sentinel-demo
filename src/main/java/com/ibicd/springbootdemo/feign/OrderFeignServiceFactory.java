package com.ibicd.springbootdemo.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderFeignServiceFactory implements FallbackFactory<OrderFeignService> {
    @Override
    public OrderFeignService create(Throwable cause) {
        return new OrderFeignService() {
            @Override
            public String findOrderByUserId(Integer userId) {
                return "=======服务降级了========";
            }
        };
    }
}
