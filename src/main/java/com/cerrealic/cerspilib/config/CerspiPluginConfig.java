package com.cerrealic.cerspilib.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;

public abstract class CerspiPluginConfig {
	private final FileConfiguration fileConfiguration;
	protected ConfigNode<Boolean> debugMode = new ConfigNode<>("debug", false);
	protected ConfigNode<Boolean> updateChecking = new ConfigNode<>("check-for-updates", false);
	private final HashSet<ConfigNode> definedNodes;
	private final JavaPlugin plugin;

	public CerspiPluginConfig(JavaPlugin plugin, FileConfiguration fileConfiguration) {
		this.plugin = plugin;
		this.fileConfiguration = fileConfiguration;
		this.definedNodes = getDefinedNodes();
		definedNodes.add(debugMode);
		definedNodes.add(updateChecking);
		loadFromFile();
		setFileDefaults();
	}

	public FileConfiguration getFileConfiguration() {
		return fileConfiguration;
	}

	private void setFileDefaults() {
		for (ConfigNode node : definedNodes) {
			fileConfiguration.addDefault(node.getPath(), node.getDefaultValue());
		}
	}

	protected abstract HashSet<ConfigNode> getDefinedNodes();

	protected void loadFromFile() {
		for (ConfigNode node : definedNodes) {
			setNodeValue(node, fileConfiguration.get(node.getPath(), node.getDefaultValue()));
		}
	}

	protected <T> void setNodeValue(ConfigNode<T> node, T value) {
		node.setValue(value);
		fileConfiguration.set(node.getPath(), value);
		saveToFile();
	}

	public void saveToFile() {
		File file = new File(plugin.getDataFolder(), "config.yml");
		try {
			fileConfiguration.save(file);
		} catch (IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, ex);
		}
	}

	public void reload() {
		loadFromFile();
	}

	public boolean isDebugMode() {
		return debugMode.getValue();
	}

	public void setDebugMode(boolean isDebugMode) {
		setNodeValue(debugMode, isDebugMode);
	}

	public boolean isUpdateChecking() {
		return updateChecking.getValue();
	}

	public void setUpdateChecking(boolean isUpdateChecking) {
		setNodeValue(updateChecking, isUpdateChecking);
	}
}
