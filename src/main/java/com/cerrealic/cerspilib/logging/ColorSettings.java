package com.cerrealic.cerspilib.logging;

import org.bukkit.ChatColor;

public class ColorSettings {
	private final ChatColor infoColor, errorColor, successColor, amountColor, moneyColor, materialColor;

	public ColorSettings(ChatColor infoColor, ChatColor errorColor, ChatColor successColor, ChatColor amountColor, ChatColor moneyColor, ChatColor materialColor) {
		this.infoColor = infoColor;
		this.errorColor = errorColor;
		this.successColor = successColor;
		this.amountColor = amountColor;
		this.moneyColor = moneyColor;
		this.materialColor = materialColor;
	}

	public ChatColor getInfoColor() {
		return infoColor;
	}

	public ChatColor getErrorColor() {
		return errorColor;
	}

	public ChatColor getSuccessColor() {
		return successColor;
	}

	public ChatColor getAmountColor() {
		return amountColor;
	}

	public ChatColor getMoneyColor() {
		return moneyColor;
	}

	public ChatColor getMaterialColor() {
		return materialColor;
	}
}
