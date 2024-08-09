package edu.s3.jqmood.utils;

import com.google.common.base.Strings;

public class LoggerUtils {

	public static String separator = "------------------------------------------------------------------------";

	/**
	 * Private Constructor will prevent the instantiation of this class directly
	 */
	private LoggerUtils() {
		throw new UnsupportedOperationException("This class cannot be instantiated");
	}

	public static String title(String title) {

		int total = separator.length();
		int size = title.length();
		int leftSize = (total - size) / 2;
		int rightSize = (total - size) / 2;

		if (size % 2 == 1) {
			rightSize--;
		}

		String before = Strings.repeat("-", leftSize);
		String after = Strings.repeat("-", rightSize);

		return "%s%s%s".formatted(before, title, after);
	}
}
