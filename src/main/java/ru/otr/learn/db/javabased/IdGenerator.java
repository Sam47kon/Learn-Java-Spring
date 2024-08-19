package ru.otr.learn.db.javabased;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IdGenerator {
	private final Map<String, Long> sequences = new HashMap<>();

	public long nextId(String sequence) {
		if (!sequences.containsKey(sequence)) {
			sequences.put(sequence, 1L);
			return 1L;
		}
		long lastValue = sequences.get(sequence) + 1;
		sequences.put(sequence, lastValue);
		return lastValue;
	}
}
