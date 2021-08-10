package com.liu.cachespringbootstarter.dto;

public class CacheKeyDto {
	private String key;
	private String hfield;

	public CacheKeyDto() {

	}

	public CacheKeyDto(String key, String hfield) {
		this.key = key;
		this.hfield = hfield;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHfield() {
		return hfield;
	}

	public void setHfield(String hfield) {
		this.hfield = hfield;
	}
}
