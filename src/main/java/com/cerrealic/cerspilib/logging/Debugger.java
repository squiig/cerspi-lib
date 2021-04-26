package com.cerrealic.cerspilib.logging;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;

public class Debugger {
	public static final String PREFIX = "[DEBUG] ";
	private final Logger logger;
	private final boolean broadcastIfTargetNull;
	private boolean enabled;
	private Conversable target;

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

	public Debugger setTarget(Conversable target) {
		this.target = target;
		return this;
	}

	public Debugger info(Conversable target, String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		logger.setTarget(target)
				.log(new Formatter(message, logger.getColorSettings())
						.format(formatArgs)
						.stylizeDebug()
						.stripColorsIf(target instanceof ConsoleCommandSender)
						.toString(),
				broadcastIfTargetNull)
			.resetTarget();
		return this;
	}

	public Debugger info(String message, Object... formatArgs) {
		return info(target, message, formatArgs);
	}

	public Debugger error(Conversable target, String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		info(target, new Formatter(message, logger.getColorSettings())
				.format(formatArgs)
				.stylizeError()
				.toString());
		return this;
	}

	public Debugger error(String message, Object... formatArgs) {
		return error(target, message, formatArgs);
	}

	public Debugger success(Conversable target, String message, Object... formatArgs) {
		if (!enabled) {
			return this;
		}

		info(target, new Formatter(message, logger.getColorSettings())
				.format(formatArgs)
				.stylizeSuccess()
				.toString());
		return this;
	}

	public Debugger success(String message, Object... formatArgs) {
		return success(target, message, formatArgs);
	}

	public Conversable getTarget() {
		return target;
	}

	public Logger getLogger() {
		return logger;
	}

	public boolean isBroadcastIfTargetNull() {
		return broadcastIfTargetNull;
	}
}
