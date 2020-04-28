package com.cerrealic.cerspilib;

import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Cerspi {
	public static void setContext(JavaPlugin plugin, Server server, Logger logger) {
		Context.plugin = plugin;
		Context.server = server;
		Context.logger = logger;
	}

	public static void registerCommand(CerspiCommand command) {
		String label = command.getLabel();
		PluginCommand pluginCommand = Context.plugin.getCommand(label);
		if (pluginCommand == null) {
			Context.logger.severe(String.format("Failed to register %s command!", label));
			disablePlugin();
			return;
		}

		pluginCommand.setExecutor(command);
		pluginCommand.setTabCompleter(command);
	}

	public static void disablePlugin() {
		Context.server.getPluginManager().disablePlugin(Context.plugin);
	}

	public static boolean isSpigotServer() {
		return Context.server.getVersion().contains("Spigot");
	}
}
