package com.liu.cachespringbootstarter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
@ConfigurationProperties(prefix = MyCacheProperties.PREFIX)
public class MyCacheProperties {
    public  static final String PREFIX = "mycache";
    /**
     * 是否默认开始cache
     */
    private boolean enable = true;
    /**
     * cache注解的优先级
     */
    private int order;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
