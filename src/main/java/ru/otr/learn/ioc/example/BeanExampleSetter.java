package ru.otr.learn.ioc.example;

import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;


@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BeanExampleSetter {
	String stringField;
	double doubleField;
	List<Object> list;
	Map<String, Object> map;

	public BeanExampleSetter() {
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public void setDoubleField(double doubleField) {
		this.doubleField = doubleField;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
