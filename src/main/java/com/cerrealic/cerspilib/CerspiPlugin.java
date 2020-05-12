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
		loadConfig();
	}

	protected abstract CerspiPluginConfig initConfig();

	public void loadConfig() {
		CerspiPluginConfig pluginConfig = initConfig();
		if (pluginConfig == null) {
			return;
		}

		Debug.enabled = pluginConfig.getDebug().getValue();
		if (Debug.enabled) {
			getLogger().info("Debug mode is enabled.");
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
