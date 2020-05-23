package com.cerrealic.cerspilib.logging;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class Formatter {
	private static final String UNDEFINED = "&4&lNULL&r";
	private String text;
	private char colorChar;
	private String currencyUnit;

	public Formatter(String text, char colorChar, String currencyUnit) {
		this.text = text;
		this.colorChar = colorChar;
		this.currencyUnit = currencyUnit;
	}

	public Formatter(String text) {
		this.text = text;
		this.colorChar = '&';
		this.currencyUnit = "$";
	}

	@Override
	public String toString() {
		return text;
	}

	public Formatter format(Object... formatArgs) {
		text = String.format(text.replace("NULL", Formatter.UNDEFINED), formatArgs) + ChatColor.RESET;
		return this;
	}

	public Formatter stripColors() {
		text = ChatColor.stripColor(text);
		return this;
	}

	public Formatter stripColorsIf(boolean assertion) {
		if (assertion) {
			return stripColors();
		}
		return this;
	}

	public Formatter colorize() {
		text = ChatColor.translateAlternateColorCodes(colorChar, text);
		return this;
	}

	public Formatter stylizeInfo() {
		text = ChatColor.GOLD + text;
		return colorize();
	}

	public Formatter stylizeDebug() {
		text = ChatColor.LIGHT_PURPLE + "[DEBUG] " + ChatColor.DARK_PURPLE + text;
		return colorize();
	}

	public Formatter stylizeError() {
		text = ChatColor.RED + text;
		return colorize();
	}

	public Formatter stylizeSuccess() {
		text = ChatColor.GREEN + text;
		return colorize();
	}

	public static String stylizeAmount(int amount) {
		return ChatColor.AQUA + Integer.toString(amount);
	}

	public static String stylizeMoney(String currencyUnit, double amount) {
		return ChatColor.GREEN + currencyUnit + String.format("%.2f", amount);
	}

	public static String stylizeMoney(String currencyUnit, BigDecimal amount) {
		if (amount == null) {
			return "NULL";
		}

		return stylizeMoney(currencyUnit, amount.doubleValue());
	}

	public static String stylizeMaterial(Material material) {
		if (material == null) {
			return "NULL";
		}

		return ChatColor.YELLOW + StringUtils.capitalize(material.name().toLowerCase().replace('_', ' '));
	}

	public static String stylizeStack(ItemStack stack) {
		if (stack == null) {
			return "NULL";
		}

		return stylizeAmount(stack.getAmount()) + "x" + stylizeMaterial(stack.getType());
	}
}
