package com.cerrealic.cerspilib.logging;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public final class Format {
	public static boolean stripColors;

	public static String colorize(String text, Object... formatArgs) {
		String formattedText = String.format(text.replace("NULL", Format.undefined()), formatArgs) + ChatColor.RESET;
		String result = ChatColor.translateAlternateColorCodes('&', formattedText);
		return stripColors ? ChatColor.stripColor(result) : result;
	}

	public static String info(String text, Object... formatArgs) {
		return colorize(ChatColor.GOLD + text, formatArgs);
	}

	public static String debug(String text, Object... formatArgs) {
		return colorize(ChatColor.LIGHT_PURPLE + "[DEBUG] " + ChatColor.DARK_PURPLE + text, formatArgs);
	}

	public static String error(String text, Object... formatArgs) {
		return colorize(ChatColor.RED + text, formatArgs);
	}

	public static String success(String text, Object... formatArgs) {
		return colorize(ChatColor.GREEN + text, formatArgs);
	}

	public static String amount(int amount) {
		return colorize(ChatColor.AQUA + Integer.toString(amount));
	}

	public static String money(double amount) {
		return colorize(ChatColor.GREEN + "$" + String.format("%.2f", amount));
	}

	public static String money(BigDecimal amount) {
		if (amount == null) {
			return "NULL";
		}

		return money(amount.doubleValue());
	}

	public static String material(Material material) {
		if (material == null) {
			return "NULL";
		}
		return colorize(ChatColor.YELLOW + StringUtils.capitalize(material.name().toLowerCase().replace('_', ' ')));
	}

	public static String stack(ItemStack stack) {
		if (stack == null) {
			return colorize("NULL");
		}
		return colorize("%sx&e%s", amount(stack.getAmount()), stack.getType().name());
	}

	private static String undefined() {
		return "&4&lNULL&r";
	}
}
