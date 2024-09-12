package ru.otr.learn.mapper;

public interface Mapper<From, To> {
	To map(From from);

	default To map(From from, To to) {
		return to;
	}
}
