package ru.otr.learn.bpp;

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
				// Отфильтруем только те поля, которые помечены аннотацией
				.filter(field -> field.isAnnotationPresent(InjectBean.class))
				// Пробежимся по этим полям (помеченным @InjectBean)
				.forEach(field -> {
					// Тип нашего поля
					Class<?> type = field.getType();
					// Искать будем не через getBean, т.к. в случае, если описаний несколько - будет ошибка,
					// а достанем все бины по типу
					Map<String, ?> beansOfType = applicationContext.getBeansOfType(type);
					if (beansOfType.size() == 1) {
						Object beanToInject = beansOfType.values().stream().findFirst().orElse(null);
						// то же самое, что и field.setAccessible(true)
						ReflectionUtils.makeAccessible(field);
						// то же самое, что и field.set(bean, beanToInject)
						ReflectionUtils.setField(field, bean, beanToInject);
					}
				});
		return bean;
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
