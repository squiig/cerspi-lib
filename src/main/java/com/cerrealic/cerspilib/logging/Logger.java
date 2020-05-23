package com.cerrealic.cerspilib.logging;

import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;

public class Logger {
	private Conversable target;
	private String prefix;

	public Logger(Conversable target, String prefix) {
		this.target = target;
		this.prefix = prefix;
	}

	public Conversable getTarget() {
		return target;
	}

	public void setTarget(Conversable target) {
		this.target = target;
	}

	public String getPrefix() {
		return prefix;
	}

	public void log(String message, boolean broadcastIfTargetNull) {
		if (target == null) {
			if (broadcastIfTargetNull) {
				Bukkit.broadcastMessage(prefix + message);
			}
			return;
		}

		target.sendRawMessage(prefix + message);
	}
}

