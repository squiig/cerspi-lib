package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.cerspilib.logging.Debug;
import com.cerrealic.cerspilib.logging.Log;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CerspiPlugin extends JavaPlugin {
	private CerspiPluginConfig cerspiPluginConfig;

	@Override
	public void onEnable() {
		if (!checkDependencies()) {
			return;
		}

		this.saveDefaultConfig();
		cerspiPluginConfig = initConfig();
		if (cerspiPluginConfig != null) {
			applyConfig(cerspiPluginConfig);
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

		if (pluginConfig.isUpdateChecking()) {
			Cerspi.checkForUpdates(this);
		}
	}

	protected boolean checkDependencies() {
		if (!Cerspi.isSpigotServer(getServer()) && !Cerspi.isPaperServer(getServer())) {
			getLogger().severe("You're probably running a CraftBukkit server. For this to plugin to work you need to switch to Spigot or PaperMC.");
			Cerspi.disablePlugin(this);
			return false;
		}

		return true;
	}

	public void setDebugMode(boolean enabled) {
		Debug.enabled = enabled;
		cerspiPluginConfig.setDebugMode(enabled);
		Log.success("Debug " + (enabled ? "enabled" : "disabled") + ".", false);
	}
}
