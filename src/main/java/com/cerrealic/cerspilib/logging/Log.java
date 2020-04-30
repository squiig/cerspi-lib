package com.cerrealic.cerspilib.logging;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;

public final class Log {
	public static Conversable target;

	public static void info(String message, Object... formatArgs) {
		String formattedMessage = Format.info(message, formatArgs);
		if (target == null) {
			Bukkit.broadcastMessage(formattedMessage);
			return;
		}

		target.sendRawMessage(formattedMessage);
	}

	public static void error(String message, Object... formatArgs) {
		String formattedMessage = Format.error(message, formatArgs);
		if (target == null) {
			Bukkit.broadcastMessage(formattedMessage);
			return;
		}

		target.sendRawMessage(formattedMessage);
	}

	public static void success(String message, Object... formatArgs) {
		String formattedMessage = Format.success(message, formatArgs);
		if (target == null) {
			Bukkit.broadcastMessage(formattedMessage);
			return;
		}

		target.sendRawMessage(formattedMessage);
	}
}

