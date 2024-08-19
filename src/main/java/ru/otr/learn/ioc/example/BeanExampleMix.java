package ru.otr.learn.ioc.example;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BeanExampleMix implements InitializingBean, DisposableBean {
	final String stringField;
	final double doubleField;
	final List<Object> list;
	Map<String, Object> map;

	public BeanExampleMix(String stringField,
						  Double doubleField,
						  List<Object> list) {
		System.out.println("BeanExampleMix constructor");
		this.stringField = stringField;
		this.doubleField = doubleField;
		this.list = list;
	}

	public void setMap(Map<String, Object> map) {
		System.out.println("BeanExampleMix setMap");
		this.map = map;
	}

	private void init() {
		// до-инициализация бина
		System.out.println("BeanExampleMix init-method");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// до-инициализация бина
		System.out.println("BeanExampleMix afterPropertiesSet");
	}

	private void destroyFromXML() {
		System.out.println("BeanExampleMix destroyFromXML");
	}

	@Override
	public void destroy() throws Exception {
			System.out.println("BeanExampleMix destroy");
	}
}
