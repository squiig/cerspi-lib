package com.cerrealic.cerspilib;

import com.cerrealic.cerspilib.config.CerspiPluginConfig;
import com.cerrealic.cerspilib.logging.Debugger;
import com.cerrealic.cerspilib.logging.Formatter;
import com.cerrealic.cerspilib.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CerspiPlugin extends JavaPlugin {
	private CerspiPluginConfig cerspiPluginConfig;
	private Logger logger;
	private Debugger debugger;

	public Logger getCerspiLogger() {
		return logger == null ? new Logger(this, null, "[" + getName() + "] ") : logger;
	}

	public Debugger getDebugger() {
		return debugger == null ? new Debugger(logger, false, false) : debugger;
	}

	@Override
	public void onEnable() {
		if (!checkDependencies()) {
			return;
		}

		logger = getCerspiLogger();
		debugger = getDebugger();

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
		debugger.setEnabled(pluginConfig.isDebugMode());
		if (debugger.isEnabled()) {
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
		debugger.setEnabled(enabled);
		cerspiPluginConfig.setDebugMode(enabled);

		logger.log(new Formatter("Debug " + (enabled ? "enabled" : "disabled") + ".").stylizeSuccess().toString(), false);
	}
}
