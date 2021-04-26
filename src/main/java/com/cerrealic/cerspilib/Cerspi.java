package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.logging.Formatter;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginBase;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Cerspi extends PluginBase {
	public static boolean assertPermissions(CerspiPlugin plugin, Player player, boolean requireAll, String... permissions) {
		String noPermissionMessage = new Formatter("You don't have permission to do that.").stylizeError().toString();

		for (String p : permissions) {
			if (requireAll) {
				if (!player.hasPermission(p)) {
					plugin.getCerspiLogger().log(player, noPermissionMessage, false);
					return false;
				}
			} else {
				if (player.hasPermission(p)) {
					return true;
				}
			}
		}

		if (requireAll)
			return true;

		plugin.getCerspiLogger().log(player, noPermissionMessage, false);
		return false;
	}

	public static void checkForUpdates(CerspiPlugin plugin) {
		if (plugin.getResourceId() == null) {
			plugin.getLogger().info(String.format("Update check failed, %s does not have a resource ID.", plugin.getName()));
			return;
		}

		UpdateCheck
				.of(plugin)
				.resourceId(plugin.getResourceId())
				.handleResponse((versionResponse, version) -> {
					String msg;
					switch (versionResponse) {
						case FOUND_NEW:
							msg = "New version of the plugin was found: " + version;
							plugin.getLogger().info(msg);
							plugin.getDebugger().info(msg);
							break;
						case LATEST:
							msg = "No updates found, this is the latest version.";
							plugin.getLogger().info(msg);
							plugin.getDebugger().info(msg);
							break;
						case UNAVAILABLE:
							msg = "Unable to perform an update check.";
							plugin.getLogger().info(msg);
							plugin.getDebugger().info(msg);
							break;
					}
				}).check();
	}

	public static void registerListeners(JavaPlugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			plugin.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}

	public static void registerCommands(JavaPlugin plugin, boolean failSeverely, CerspiCommand... cerspiCommands) {
		for (CerspiCommand cerspiCommand : cerspiCommands) {
			PluginCommand pluginCommand = plugin.getCommand(cerspiCommand.getLabel());
			if (pluginCommand == null) {
				if (failSeverely) {
					plugin.getLogger().severe(String.format("Failed to register /%s command!", cerspiCommand.getLabel()));
					disablePlugin(plugin);
				}
				else {
					plugin.getLogger().warning(String.format("Failed to register /%s command!", cerspiCommand.getLabel()));
				}
				return;
			}

			pluginCommand.setExecutor(cerspiCommand);
			pluginCommand.setTabCompleter(cerspiCommand.getTabCompleter());
		}
	}

	public static void disablePlugin(JavaPlugin plugin) {
		plugin.getServer().getPluginManager().disablePlugin(plugin);
	}

	public static boolean isSpigotServer(Server server) {
		return server.getVersion().contains("Spigot");
	}

	public static boolean isPaperServer(Server server) {
		return server.getVersion().contains("Paper");
	}
}
