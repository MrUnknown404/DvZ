package com.mrunknown404.dvz.util;

import java.util.Arrays;

public class GetEnumNames {
	public static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}
}
