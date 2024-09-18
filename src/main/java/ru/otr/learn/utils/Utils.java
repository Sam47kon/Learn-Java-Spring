package ru.otr.learn.utils;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class Utils {
	public static final LocalDate startDate = LocalDate.of(1975, 1, 1);
	public static final LocalDate endDate = LocalDate.of(2000, 12, 31);
	public static final long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);


	public static @NotNull LocalDate getRandomDate() {
		// Генерируем случайное количество дней
		long randomDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);

		// Возвращаем случайную дату
		return startDate.plusDays(randomDays);
	}

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
