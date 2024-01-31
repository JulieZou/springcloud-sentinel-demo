package com.ibicd.springbootdemo.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 *  编写ExceptionUtil，注意如果指定了class，方法必须是static方法
 *
 *  - 指定处理方法必须是public static的
 * - 方法参数列表和受保护资源一致（blockHandler最后增加一个BlockException，fallback可选增加Throwable）
 * - 方法返回值必须和受保护资源相同
 */
public class ExceptionUtil {

    public static String handleException(BlockException ex){
        return "===被限流啦===";
    }

    public static String fallbackException(Throwable t){
        return "===被异常降级啦===";
    }


}
