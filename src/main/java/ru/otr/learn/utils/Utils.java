package ru.otr.learn.utils;

import com.google.common.base.Joiner;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {
	public static Joiner JOINER_LINE = Joiner.on(System.lineSeparator() + '\t');

	public static <T> @NotNull String prettyList(List<T> list) {
		return JOINER_LINE.join(list);
	}
}
