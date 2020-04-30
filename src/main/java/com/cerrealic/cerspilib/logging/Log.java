package com.cerrealic.cerspilib.logging;

import com.cerrealic.cerspilib.Cerspi;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;

public final class Log {
	public static Conversable target;
	public static String prefix = "[" + Cerspi.plugin.getDescription().getName() + "] ";

	private static void log(String message) {
		if (target == null) {
			Bukkit.broadcastMessage(prefix + message);
			return;
		}

		target.sendRawMessage(prefix + message);
	}

	public static void info(String message, Object... formatArgs) {
		log(Format.info(message, formatArgs));
	}

	public static void error(String message, Object... formatArgs) {
		log(Format.error(message, formatArgs));
	}

	public static void success(String message, Object... formatArgs) {
		log(Format.success(message, formatArgs));
	}
}

