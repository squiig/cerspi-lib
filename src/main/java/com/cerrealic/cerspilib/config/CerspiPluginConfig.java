package com.cerrealic.cerspilib.config;

import org.bukkit.configuration.file.FileConfiguration;

public class CerspiPluginConfig {
	protected ConfigNode<Boolean> debug = new ConfigNode<>("debug", false);

	public CerspiPluginConfig(boolean debug) {
		this.debug.setValue(debug);
	}

	public CerspiPluginConfig(FileConfiguration fileConfiguration) {
		this.debug.setValue(fileConfiguration.getBoolean(debug.getPath(), debug.getDefaultValue()));
	}

	public ConfigNode<Boolean> getDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug.setValue(debug);
	}
}
