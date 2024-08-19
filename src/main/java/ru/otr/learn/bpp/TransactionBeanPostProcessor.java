package ru.otr.learn.bpp;

import org.jetbrains.annotations.NotNull;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class TransactionBeanPostProcessor implements BeanPostProcessor {

	private final Set<String> transactionBeans = new HashSet<>();

	@Override
	public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		if (Arrays.stream(bean.getClass().getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(Transaction.class))) {
			transactionBeans.add(beanName);
		}
		return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
	}

	@Override
	public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
		if (!transactionBeans.contains(beanName)) {
			return bean;
		}
		ProxyFactory proxyFactory = new ProxyFactory(bean);
		proxyFactory.setProxyTargetClass(true);
		proxyFactory.addAdvice(new TransactionAdvice());
		return proxyFactory.getProxy();
	}

	private static class TransactionAdvice implements MethodBeforeAdvice, AfterReturningAdvice {

		@Override
		public void before(@NotNull Method method, Object @NotNull [] args, Object target) throws Throwable {
			if (!method.isAnnotationPresent(Transaction.class)) {
				// Метод не помечен аннотацией
				return;
			}
			System.out.println("Открыли транзакцию для: " + method.getName());
		}

		@Override
		public void afterReturning(Object returnValue, @NotNull Method method, Object @NotNull [] args, Object target) throws Throwable {
			if (!method.isAnnotationPresent(Transaction.class)) {
				// Метод не помечен аннотацией
				return;
			}
			System.out.println("Закрыли транзакцию для: " + method.getName());
		}
	}
}
