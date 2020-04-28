package com.cerrealic.cerspilib;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;

public final class Debug {
	public static boolean enabled;
	public static Conversable target;

	public static void info(String message, Object... formatArgs) {
		if (!enabled || target == null) {
			return;
		}

		Format.stripColors = target instanceof ConsoleCommandSender;
		target.sendRawMessage(Format.debug(message, formatArgs));
	}

	public static void error(String message, Object... formatArgs) {
		if (!enabled) {
			return;
		}
		info(Format.error(message, formatArgs));
	}

	public static void success(String message, Object... formatArgs) {
		if (!enabled) {
			return;
		}
		info(Format.success(message, formatArgs));
	}
}
