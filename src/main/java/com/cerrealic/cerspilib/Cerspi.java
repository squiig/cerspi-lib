package com.cerrealic.cerspilib;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class Cerspi extends PluginBase {
	public static void setContext(JavaPlugin plugin, Server server, Logger logger) {
		Context.plugin = plugin;
		Context.server = server;
		Context.logger = logger;
	}

	public static void checkForUpdates(int resourceId) {
		UpdateCheck
				.of(Context.plugin)
				.resourceId(resourceId)
				.handleResponse((versionResponse, version) -> {
					String msg;
					switch (versionResponse) {
						case FOUND_NEW:
							msg = "New version of the plugin was found: " + version;
							Context.logger.info(msg);
							Debug.info(msg);
							break;
						case LATEST:
							msg = "No updates found, this is the latest version.";
							Context.logger.info(msg);
							Debug.info(msg);
							break;
						case UNAVAILABLE:
							msg = "Unable to perform an update check.";
							Context.logger.info(msg);
							Debug.info(msg);
							break;
					}
				}).check();
	}

	public static void registerCommand(String label, CommandExecutor executor, TabCompleter completer) {
		PluginCommand pluginCommand = Context.plugin.getCommand(label);
		if (pluginCommand == null) {
			Context.logger.severe(String.format("Failed to register %s command!", label));
			disablePlugin();
			return;
		}

		pluginCommand.setExecutor(executor);
		pluginCommand.setTabCompleter(completer);
	}

	public static void registerCommand(String label, CommandExecutor executor) {
		registerCommand(label, executor, (TabCompleter) executor);
	}

	public static void disablePlugin() {
		Context.server.getPluginManager().disablePlugin(Context.plugin);
	}

	public static boolean isSpigotServer() {
		return Context.server.getVersion().contains("Spigot");
	}

	public static boolean isPaperServer() {
		return Context.server.getVersion().contains("Paper");
	}
}
