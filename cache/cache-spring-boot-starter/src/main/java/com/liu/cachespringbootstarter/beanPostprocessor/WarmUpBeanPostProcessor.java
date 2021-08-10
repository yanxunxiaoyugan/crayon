package com.liu.cachespringbootstarter.beanPostprocessor;

import com.liu.cachespringbootstarter.warmup.IwarmUp;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class WarmUpBeanPostProcessor implements BeanPostProcessor {
    /**
     * 当bean 都完成初始化之后调用预热接口
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAssignableFrom(IwarmUp.class)) {
            IwarmUp iwarmUp = (IwarmUp) bean;
            iwarmUp.warmUp();
        }
        return bean;
    }
}
