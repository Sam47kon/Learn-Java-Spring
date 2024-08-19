package ru.otr.learn.bpp;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j(topic = "ru.otr.learn.Performance")
@Component
public class PerformanceBeanPostProcessor implements BeanPostProcessor {
	private static final String LOG_FIELD_NAME = "log";

	private final Set<String> performanceBeans = new HashSet<>();

	@Override
	public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		if (Arrays.stream(bean.getClass().getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Performance.class))) {
			performanceBeans.add(beanName);
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		if (!performanceBeans.contains(beanName)) {
			return bean;
		}
		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new PerformanceAdvice());
		return proxyFactory.getProxy();
	}

	private static class PerformanceAdvice implements MethodInterceptor {

		@Nullable
		@Override
		public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
			if (!invocation.getMethod().isAnnotationPresent(Performance.class)) {
				return invocation.proceed();
			}

			Logger logger = null;
			if (null != invocation.getThis()) {
				Class<?> clazz = invocation.getThis().getClass();
				logger = Optional.ofNullable(ReflectionUtils.findField(clazz, LOG_FIELD_NAME, Logger.class))
						.map(logField -> {
							ReflectionUtils.makeAccessible(logField);
							return (Logger) ReflectionUtils.getField(logField, clazz);
						})
						.orElse(null);
			}
			if (null == logger) {
				logger = log;
			}

			long start = System.currentTimeMillis();
			Object result = invocation.proceed();
			long end = System.currentTimeMillis();
			logger.info("[PERFORMANCE] Время выполнения метода [{}]: {} ms", invocation.getMethod().getName(), end - start);
			return result;
		}
	}
}
