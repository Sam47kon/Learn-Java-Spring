package ru.otr.learn.ioc.example;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BeanExampleConstructor {
	String stringField;
	double doubleField;
	List<Object> list;
	Map<String, Object> map;

	public BeanExampleConstructor(String stringField,
								  Double doubleField,
								  List<Object> list,
								  Map<String, Object> map) {
		this.stringField = stringField;
		this.doubleField = doubleField;
		this.list = list;
		this.map = map;
	}

	@Override
	public String toString() {
		return "BeanExampleConstructor{" +
				"stringField='" + stringField + '\'' +
				", doubleField=" + doubleField +
				", list=" + list +
				", map=" + map +
				'}';
	}
}
