package com.cerrealic.cerspilib.logging;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class Formatter {
	private static final String UNDEFINED = "&4&lNULL&r";
	private static final char DEFAULT_COLOR_CHAR = '&';
	private static final String DEFAULT_CURRENCY_UNIT = "$";
	private static final ColorSettings DEFAULT_COLORS = new ColorSettings(ChatColor.GOLD, ChatColor.RED,
			ChatColor.GREEN, ChatColor.AQUA, ChatColor.GREEN, ChatColor.YELLOW);

	private String text;
	private final char colorChar;
	private final String currencyUnit;
	private final ColorSettings colorSettings;

	public Formatter(String text, char colorChar, String currencyUnit, ColorSettings colorSettings) {
		this.text = text;
		this.colorChar = colorChar;
		this.currencyUnit = currencyUnit;
		this.colorSettings = colorSettings == null ? DEFAULT_COLORS : colorSettings;
	}

	public Formatter(String text, ColorSettings colorSettings) {
		this(text, DEFAULT_COLOR_CHAR, DEFAULT_CURRENCY_UNIT, colorSettings);
	}

	public Formatter(String text) {
		this(text, DEFAULT_COLOR_CHAR, DEFAULT_CURRENCY_UNIT, DEFAULT_COLORS);
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
		text = colorSettings.getInfoColor() + text;
		return colorize();
	}

	public Formatter stylizeDebug() {
		text = ChatColor.LIGHT_PURPLE + Debugger.PREFIX + ChatColor.DARK_PURPLE + text;
		return colorize();
	}

	public Formatter stylizeError() {
		text = colorSettings.getErrorColor() + text;
		return colorize();
	}

	public Formatter stylizeSuccess() {
		text = colorSettings.getSuccessColor() + text;
		return colorize();
	}

	public String stylizeAmount(int amount) {
		return colorSettings.getAmountColor() + Integer.toString(amount);
	}

	public String stylizeMoney(double amount, String currencyUnit) {
		return colorSettings.getMoneyColor() + currencyUnit + String.format("%.2f", amount);
	}

	public String stylizeMoney(double amount) {
		return stylizeMoney(amount, currencyUnit);
	}

	public String stylizeMoney(BigDecimal amount, String currencyUnit) {
		if (amount == null) {
			return "NULL";
		}

		return stylizeMoney(amount.doubleValue(), currencyUnit);
	}

	public String stylizeMaterial(Material material) {
		if (material == null) {
			return "NULL";
		}

		return colorSettings.getMaterialColor() + StringUtils.capitalize(material.name().toLowerCase().replace('_', ' '));
	}

	public String stylizeStack(ItemStack stack) {
		if (stack == null) {
			return "NULL";
		}

		return stylizeAmount(stack.getAmount()) + "x" + stylizeMaterial(stack.getType());
	}
}
