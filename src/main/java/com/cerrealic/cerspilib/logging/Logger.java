package com.cerrealic.cerspilib.logging;

import com.cerrealic.cerspilib.CerspiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Logger {
	private final CerspiPlugin plugin;
	private final Conversable originalTarget;
	private Conversable target;
	private final String prefix;
	private final ColorSettings colorSettings;

	public Logger(CerspiPlugin plugin, Conversable target, String prefix, ColorSettings colorSettings) {
		this.plugin = plugin;
		this.originalTarget = target;
		this.target = target;
		this.prefix = new Formatter(prefix, colorSettings).colorize().toString();
		this.colorSettings = colorSettings;
	}

	public Logger(CerspiPlugin plugin, Conversable target, String prefix) {
		this(plugin, target, prefix, null);
	}

	public ColorSettings getColorSettings() {
		return colorSettings;
	}

	public Conversable getTarget() {
		return target;
	}

	public Logger setTarget(Conversable target) {
		this.target = target;
		return this;
	}

	public Logger resetTarget() {
		this.target = originalTarget;
		return this;
	}

	public String getPrefix() {
		return prefix;
	}

	public Logger log(Conversable target, String message, boolean broadcastIfTargetNull) {
		if (target == null) {
			if (broadcastIfTargetNull) {
				Bukkit.broadcastMessage(prefix + message);
				return this;
			}

			plugin.getLogger().info(message);
			return this;
		}

		target.sendRawMessage((target instanceof Player) ? prefix + message : message);
		return this;
	}

	public Logger log(String message, boolean broadcastIfTargetNull) {
		log(target, message, broadcastIfTargetNull);
		return this;
	}

	public Logger logInfo(String message, boolean broadcastIfTargetNull) {
		log(new Formatter(message, colorSettings).stylizeInfo().toString(), broadcastIfTargetNull);
		return this;
	}

	public Logger logInfo(String message) {
		logInfo(message, false);
		return this;
	}

	public Logger logError(String message, boolean broadcastIfTargetNull) {
		log(new Formatter(message, colorSettings).stylizeError().toString(), broadcastIfTargetNull);
		return this;
	}

	public Logger logError(String message) {
		logError(message, false);
		return this;
	}

	public Logger logSuccess(String message, boolean broadcastIfTargetNull) {
		log(new Formatter(message, colorSettings).stylizeSuccess().toString(), broadcastIfTargetNull);
		return this;
	}

	public Logger logSuccess(String message) {
		logSuccess(message, false);
		return this;
	}
}

