package com.mrunknown404.dvz.util;

import javax.annotation.Nonnull;

import net.minecraft.util.text.TextFormatting;

public class ColoredStringHelper {
	public static String setColors(@Nonnull String string) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == '&') {
				if (string.charAt(i) == string.length() - 1) {
				} else if (string.charAt(i + 1) == ' ') {
				} else if (string.charAt(i + 1) == '0') {
					string = string.replace("&0", TextFormatting.BLACK.toString());
				} else if (string.charAt(i + 1) == '1') {
					string = string.replace("&1", TextFormatting.DARK_BLUE.toString());
				} else if (string.charAt(i + 1) == '2') {
					string = string.replace("&2", TextFormatting.DARK_GREEN.toString());
				} else if (string.charAt(i + 1) == '3') {
					string = string.replace("&3", TextFormatting.DARK_AQUA.toString());
				} else if (string.charAt(i + 1) == '4') {
					string = string.replace("&4", TextFormatting.DARK_RED.toString());
				} else if (string.charAt(i + 1) == '5') {
					string = string.replace("&5", TextFormatting.DARK_PURPLE.toString());
				} else if (string.charAt(i + 1) == '6') {
					string = string.replace("&6", TextFormatting.GOLD.toString());
				} else if (string.charAt(i + 1) == '7') {
					string = string.replace("&7", TextFormatting.GRAY.toString());
				} else if (string.charAt(i + 1) == '8') {
					string = string.replace("&8", TextFormatting.DARK_GRAY.toString());
				} else if (string.charAt(i + 1) == '9') {
					string = string.replace("&9", TextFormatting.BLUE.toString());
				} else if (string.charAt(i + 1) == 'a') {
					string = string.replace("&a", TextFormatting.GREEN.toString());
				} else if (string.charAt(i + 1) == 'b') {
					string = string.replace("&b", TextFormatting.AQUA.toString());
				} else if (string.charAt(i + 1) == 'c') {
					string = string.replace("&c", TextFormatting.RED.toString());
				} else if (string.charAt(i + 1) == 'd') {
					string = string.replace("&d", TextFormatting.LIGHT_PURPLE.toString());
				} else if (string.charAt(i + 1) == 'e') {
					string = string.replace("&e", TextFormatting.YELLOW.toString());
				} else if (string.charAt(i + 1) == 'f') {
					string = string.replace("&f", TextFormatting.WHITE.toString());
				} else if (string.charAt(i + 1) == 'r') {
					string = string.replace("&r", TextFormatting.RESET.toString());
				}
			}
		}
		return string;
	}
}
