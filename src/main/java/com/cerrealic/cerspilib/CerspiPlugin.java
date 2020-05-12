package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.cerspilib.logging.Debug;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CerspiPlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		Cerspi.setContext(this);

		if (!checkDependencies()) {
			return;
		}

		this.saveDefaultConfig();
		CerspiPluginConfig pluginConfig = initConfig();
		if (pluginConfig != null) {
			applyConfig(pluginConfig);
		}
	}

	public Integer getResourceId() {
		return null;
	}

	protected abstract CerspiPluginConfig initConfig();

	public void applyConfig(CerspiPluginConfig pluginConfig) {
		Debug.enabled = pluginConfig.isDebugMode();
		if (Debug.enabled) {
			getLogger().info("Debug mode is enabled.");
		}

		Integer resourceId = getResourceId();
		if (pluginConfig.isUpdateChecking() && resourceId != null) {
			Cerspi.checkForUpdates(resourceId);
		}
	}

	protected boolean checkDependencies() {
		if (!Cerspi.isSpigotServer() && !Cerspi.isPaperServer()) {
			getLogger().severe("You're probably running a CraftBukkit server. For this to plugin to work you need to switch to Spigot or PaperMC.");
			Cerspi.disablePlugin();
			return false;
		}

		return true;
	}
}
