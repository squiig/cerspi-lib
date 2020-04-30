package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.logging.Debug;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Cerspi extends PluginBase {
	static JavaPlugin plugin;
	static Server server;

	public static void setContext(JavaPlugin plugin, Server server) {
		Cerspi.plugin = plugin;
		Cerspi.server = server;
	}

	public static void checkForUpdates(int resourceId) {
		UpdateCheck
				.of(plugin)
				.resourceId(resourceId)
				.handleResponse((versionResponse, version) -> {
					String msg;
					switch (versionResponse) {
						case FOUND_NEW:
							msg = "New version of the plugin was found: " + version;
							plugin.getLogger().info(msg);
							Debug.info(msg);
							break;
						case LATEST:
							msg = "No updates found, this is the latest version.";
							plugin.getLogger().info(msg);
							Debug.info(msg);
							break;
						case UNAVAILABLE:
							msg = "Unable to perform an update check.";
							plugin.getLogger().info(msg);
							Debug.info(msg);
							break;
					}
				}).check();
	}

	public static void registerCommand(String label, CommandExecutor executor, TabCompleter completer) {
		PluginCommand pluginCommand = plugin.getCommand(label);
		if (pluginCommand == null) {
			plugin.getLogger().severe(String.format("Failed to register %s command!", label));
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
		server.getPluginManager().disablePlugin(plugin);
	}

	public static boolean isSpigotServer() {
		return server.getVersion().contains("Spigot");
	}

	public static boolean isPaperServer() {
		return server.getVersion().contains("Paper");
	}
}
