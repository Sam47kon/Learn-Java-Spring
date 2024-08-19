package ru.otr.learn.ioc.example.bfpp;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.PriorityOrdered;

public class BeanFactoryPostProcessorExample implements BeanFactoryPostProcessor, PriorityOrdered {

	@Override
	public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
}
