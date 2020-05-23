package com.cerrealic.cerspilib.logging;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Debugger {
	private static final String PREFIX = "[DEBUG]";
	private Logger logger;
	private boolean enabled;
	private boolean broadcastIfTargetNull;

	public Debugger(Logger logger, boolean enabled, boolean broadcastIfTargetNull) {
		this.logger = logger;
		this.enabled = enabled;
		this.broadcastIfTargetNull = broadcastIfTargetNull;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void info(String message, Object... formatArgs) {
		if (!enabled) {
			return;
		}

		logger.log(ChatColor.LIGHT_PURPLE + PREFIX + new Formatter(message)
						.format(formatArgs)
						.stylizeDebug()
						.stripColorsIf(logger.getTarget() instanceof ConsoleCommandSender)
						.toString(),
				broadcastIfTargetNull);
	}

	public void error(String message, Object... formatArgs) {
		if (!enabled) {
			return;
		}

		info(new Formatter(message).format(formatArgs).stylizeError().toString());
	}

	public void success(String message, Object... formatArgs) {
		if (!enabled) {
			return;
		}

		info(new Formatter(message).format(formatArgs).stylizeSuccess().toString());
	}
}
