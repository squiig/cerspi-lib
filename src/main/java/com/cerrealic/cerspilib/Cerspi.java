package com.cerrealic.cerspilib;

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
		UpdateChecker checker = new UpdateChecker(Context.plugin, resourceId);
		checker.getVersion(latest -> {
			String current = Context.plugin.getDescription().getVersion();
			if (UpdateChecker.isUpToDate(current, latest)) {
				Context.logger.info(String.format("No updates found. %s is up-to-date and ready to go!", Context.plugin.getName()));
				return;
			}

			if (UpdateChecker.isMajorAvailable(current, latest)) {
				Context.logger.info("There is a major update available. Install it in order to get the newest features and bug fixes! Support for the currently installed version is not guaranteed.");
				return;
			}

			if (UpdateChecker.isMinorAvailable(current, latest)) {
				Context.logger.info("There is a minor update available. Update in order to get the newest features and bug fixes!");
				return;
			}

			if (UpdateChecker.isPatchAvailable(current, latest)) {
				Context.logger.info("There is a new patch available! It's strongly recommended to update in order to avoid unwanted technical or security problems or experience any kind of quality issues.");
				return;
			}

			Context.logger.info("Your version seems to be outdated, but the kind of update available cannot be determined. Please contact the plugin author about this.");
		});
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
