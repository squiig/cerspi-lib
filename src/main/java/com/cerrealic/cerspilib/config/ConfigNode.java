package com.cerrealic.cerspilib.config;

public class ConfigNode<T> {
	private String path;
	private T defaultValue;
	private T value;

	public ConfigNode(String path, T defaultValue) {
		this.path = path;
		this.defaultValue = defaultValue;
	}

	public ConfigNode(String path, T defaultValue, T value) {
		this(path, defaultValue);
		setValue(value);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public T getDefaultValue() {
		return defaultValue;
	}
}
