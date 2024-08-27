package ru.otr.learn.utils;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Slf4j
public class Utils {
	public static Joiner JOINER_LINE = Joiner.on(System.lineSeparator() + '\t');

	public static <T> @NotNull String prettyList(List<T> list) {
		return JOINER_LINE.join(list);
	}

	public static void executeNoThrows(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			log.error("Ошибка: {}", e.getMessage());
		}
	}
}
