package com.ibicd.springbootdemo.web;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.ibicd.springbootdemo.config.ExceptionUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "HelloWorld";

    /**
     * 定义流控规则
     */
    @PostConstruct
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //设置受保护的资源
        rule.setResource(RESOURCE_NAME);
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);
        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }

    @RequestMapping(value = "/hello")
    public String hello() {// 对代码的侵入性比较大
        try (Entry entry = SphU.entry(RESOURCE_NAME)) {
            // 被保护的逻辑
            log.info("hello world");
            return "hello world";
        } catch (BlockException ex) {
            // 处理被流控的逻辑
            log.info("blocked!");
            return "被流控了";
        }
    }


    /**
     * 使用注意事项：
     * - 方法必须和被保护资源处于同一个类
     * - 方法参数列表和受保护资源一致（blockHandler最后增加一个BlockException，fallback可选增加Throwable）
     * - 方法返回值必须和受保护资源相同
     *
     * @return
     */
    @RequestMapping(value = "/hello1")
    @SentinelResource(value = "hello1",
            blockHandler = "handleException",fallback = "fallbackException"
    )
    public String hello1() {//注解方式埋点不支持 private 方法
        int i = 1 / 0;
        return "helloworld";
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原方法hello2一致.
    public String handleException(BlockException ex){
        return "被流控了";
    }

    // Fallback 异常处理函数，参数与原方法hello2一致或加一个 Throwable 类型的参数.
    public String fallbackException(Throwable t){
        return "被异常降级了";
    }

    @RequestMapping(value = "/hello2")
    @SentinelResource(value = "hello2",
            blockHandlerClass = ExceptionUtil.class,blockHandler = "handleException",
            fallbackClass = ExceptionUtil.class,fallback = "fallbackException"
    )
    public String hello2() {
        // 被保护的逻辑
        log.info("hello world");
        return "hello world";
    }


    @RequestMapping(value = "/hello3")
    public String hello3() {// mvc 方法自动埋点
       return "hello3";
    }

    @RequestMapping("/testWarmup")
    public String test() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "========test()========";
    }

}
