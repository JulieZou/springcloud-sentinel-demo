package com.ibicd.springbootdemo.convert;

import com.alibaba.cloud.sentinel.datasource.converter.JsonConverter;
import com.alibaba.cloud.sentinel.datasource.factorybean.NacosDataSourceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class FlowParamConverterBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private JsonConverter jsonParamFlowConverter;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("param-flow-rules-sentinel-nacos-datasource")) {
            NacosDataSourceFactoryBean nacosDataSourceFactoryBean = (NacosDataSourceFactoryBean) bean;
            nacosDataSourceFactoryBean.setConverter(jsonParamFlowConverter);
            return bean;
        }
        return bean;
    }
}
