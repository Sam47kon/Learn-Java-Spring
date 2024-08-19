package ru.otr.learn.bpp;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class PerformanceBeanPostProcessor implements BeanPostProcessor {

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
			long start = System.currentTimeMillis();
			Object result = invocation.proceed();
			long end = System.currentTimeMillis();
			System.out.printf("[PERFORMANCE] Время выполнения метода [%s]: %d ms%n", invocation.getMethod().getName(), end - start);
			return result;
		}
	}
}
