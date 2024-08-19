package ru.otr.learn.bfpp.xmlbased;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {
	@Override
	public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// В дебаге
		System.out.println("LogBeanFactoryPostProcessor postProcessBeanFactory");
	}

	@Override
	public int getOrder() {
		// Чем меньше, тем приоритет выше
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
