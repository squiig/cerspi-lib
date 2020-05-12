package com.cerrealic.cerspilib.config;

import com.cerrealic.cerspilib.Cerspi;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class CerspiPluginConfig {
	protected FileConfiguration fileConfiguration;
	protected ConfigNode<Boolean> debugMode = new ConfigNode<>("debug", false);
	protected ConfigNode<Boolean> updateChecking = new ConfigNode<>("check-for-updates", false);

	public CerspiPluginConfig(FileConfiguration fileConfiguration) {
		this.fileConfiguration = fileConfiguration;
		reload(fileConfiguration);
	}

	public void save() {
		File file = new File(Cerspi.plugin.getDataFolder(), "config.yml");
		try {
			fileConfiguration.save(file);
		} catch (IOException ex) {
			Cerspi.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
		}
	}

	public void reload(FileConfiguration fileConfiguration) {
		setDebugMode(fileConfiguration.getBoolean(debugMode.getPath(), debugMode.getDefaultValue()));
		setUpdateChecking(fileConfiguration.getBoolean(updateChecking.getPath(), updateChecking.getDefaultValue()));
	}

	public boolean isDebugMode() {
		return debugMode.getValue();
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode.setValue(debugMode);
		fileConfiguration.set(this.debugMode.getPath(), debugMode);
		save();
	}

	public boolean isUpdateChecking() {
		return updateChecking.getValue();
	}

	public void setUpdateChecking(boolean updateChecking) {
		this.updateChecking.setValue(updateChecking);
		fileConfiguration.set(this.updateChecking.getPath(), updateChecking);
		save();
	}
}
