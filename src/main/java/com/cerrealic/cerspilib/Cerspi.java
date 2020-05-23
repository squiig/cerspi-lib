package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.logging.Debug;
import com.cerrealic.cerspilib.logging.Log;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Cerspi extends PluginBase {
	public static JavaPlugin plugin;
	public static Server server;

	public static void setContext(JavaPlugin plugin) {
		Cerspi.plugin = plugin;
		Cerspi.server = plugin.getServer();
	}

	public static boolean assertPermission(Player player, String permission) {
		if (player.hasPermission(permission)) {
			return true;
		}
		Log.error("You don't have permission to use that command.");
		return false;
	}

	public static boolean assertPermissions(Player player, String... permissions) {
		for (String p : permissions) {
			if (player.hasPermission(p)) {
				return true;
			}
		}
		Log.error("You don't have permission to use that command. BLA");
		return false;
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

	public static void registerListeners(Listener... listeners) {
		for (Listener listener : listeners) {
			server.getPluginManager().registerEvents(listener, plugin);
		}
	}

	public static void registerCommands(CerspiCommand... cerspiCommands) {
		for (CerspiCommand cerspiCommand : cerspiCommands) {
			PluginCommand pluginCommand = plugin.getCommand(cerspiCommand.getLabel());
			if (pluginCommand == null) {
				plugin.getLogger().severe(String.format("Failed to register /%s command!", cerspiCommand.getLabel()));
				disablePlugin();
				return;
			}

			pluginCommand.setExecutor(cerspiCommand);
			pluginCommand.setTabCompleter(cerspiCommand.getTabCompleter());
		}
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
