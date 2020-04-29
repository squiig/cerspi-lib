package com.cerrealic.cerspilib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {
	private Plugin plugin;
	private int resourceId;

	public UpdateChecker(Plugin plugin, int resourceId) {
		this.plugin = plugin;
		this.resourceId = resourceId;
	}

	void getVersion(final Consumer<String> consumer) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
				if (scanner.hasNext()) {
					consumer.accept(scanner.next());
				}
			} catch (IOException exception) {
				this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
			}
		});
	}

	public static boolean isUpToDate(String currentVersion, String latestVersion) {
		return currentVersion.equalsIgnoreCase(latestVersion);
	}

	public static boolean isMajorAvailable(String currentVersion, String latestVersion) {
		String currentMajor = currentVersion.substring(0, currentVersion.indexOf('.'));
		String latestMajor = latestVersion.substring(0, latestVersion.indexOf('.'));
		return currentMajor.equals(latestMajor);
	}

	public static boolean isMinorAvailable(String currentVersion, String latestVersion) {
		String currentMinor = currentVersion.substring(currentVersion.indexOf('.'), currentVersion.lastIndexOf('.'));
		String latestMinor = latestVersion.substring(latestVersion.indexOf('.'), latestVersion.lastIndexOf('.'));
		return currentMinor.equals(latestMinor);
	}

	public static boolean isPatchAvailable(String currentVersion, String latestVersion) {
		String currentPatch = currentVersion.substring(currentVersion.lastIndexOf('.'));
		String latestPatch = latestVersion.substring(latestVersion.lastIndexOf('.'));
		return currentPatch.equals(latestPatch);
	}
}
