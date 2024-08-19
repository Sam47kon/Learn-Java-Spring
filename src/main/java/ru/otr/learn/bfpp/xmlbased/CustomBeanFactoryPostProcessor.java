package ru.otr.learn.bfpp.xmlbased;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
	@Override
	public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("BeanFactoryPostProcessor postProcessBeanFactory");
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
