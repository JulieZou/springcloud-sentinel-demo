package com.ibicd.springbootdemo.web;


import com.ibicd.springbootdemo.feign.OrderFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OrderFeignService orderFeignService;

    @RequestMapping(value = "/findOrderByUserId/{id}")
    public String findOrderByUserId(@PathVariable("id") Integer id) {
        //openFeign调用
//        orderFeignService.findOrderByUserId(id);
        return "hello";
    }
}
