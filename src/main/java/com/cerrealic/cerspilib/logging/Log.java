package com.cerrealic.cerspilib.logging;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;

public final class Log {
	public static Conversable target;
	public static String prefix = "";

	private static void log(String message, boolean broadcastIfTargetNull) {
		if (target == null && broadcastIfTargetNull) {
			Bukkit.broadcastMessage(prefix + message);
			return;
		}

		target.sendRawMessage(prefix + message);
	}

	public static void info(String message, boolean broadcastIfTargetNull, Object... formatArgs) {
		log(Format.info(message, formatArgs), broadcastIfTargetNull);
	}

	public static void error(String message, boolean broadcastIfTargetNull, Object... formatArgs) {
		log(Format.error(message, formatArgs), broadcastIfTargetNull);
	}

	public static void success(String message, boolean broadcastIfTargetNull, Object... formatArgs) {
		log(Format.success(message, formatArgs), broadcastIfTargetNull);
	}
}

