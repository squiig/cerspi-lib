package com.cerrealic.cerspilib.logging;

import org.bukkit.command.ConsoleCommandSender;

public class Debugger {
	public static final String PREFIX = "[DEBUG] ";
	private final Logger logger;
	private boolean enabled;
	private final boolean broadcastIfTargetNull;

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

	public Debugger info(String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		logger.log(new Formatter(message)
						.format(formatArgs)
						.stylizeDebug()
						.stripColorsIf(logger.getTarget() instanceof ConsoleCommandSender)
						.toString(),
				broadcastIfTargetNull);
		return this;
	}

	public Debugger error(String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		info(new Formatter(message).format(formatArgs).stylizeError().toString());
		return this;
	}

	public Debugger success(String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		info(new Formatter(message).format(formatArgs).stylizeSuccess().toString());
		return this;
	}
}
