package ru.otr.learn.mapper;

public interface Mapper<From, To> {
	To transform(From from);

	default To transform(From from, To to) {
		return to;
	}
}
