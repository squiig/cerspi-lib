package com.cerrealic.cerspilib;

import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Cerspi {
	public static void setContext(JavaPlugin plugin, Server server, Logger logger) {
		Context.plugin = plugin;
		Context.server = server;
		Context.logger = logger;
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
}
