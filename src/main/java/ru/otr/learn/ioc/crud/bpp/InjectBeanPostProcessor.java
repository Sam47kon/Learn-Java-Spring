package ru.otr.learn.ioc.crud.bpp;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.Map;

public class InjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		Arrays.stream(bean.getClass().getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(InjectBean.class))
				.forEach(field -> {
					Map<String, ?> beansOfType = applicationContext.getBeansOfType(field.getType());
					if (beansOfType.size() == 1) {
						Object beanToInject = beansOfType.values().stream().findFirst().orElse(null);
						ReflectionUtils.makeAccessible(field);
						ReflectionUtils.setField(field, bean, beanToInject);
					}
				});
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	@Override
	public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
