package com.liu.cachespringbootstarter.dto;

public class CacheKeyDto {
	private String key;
	private String hfield;
	private Object cacheObject;

	public CacheKeyDto() {

	}

	public CacheKeyDto(String key,Object cacheObject) {
		this.key = key;
		this.cacheObject = cacheObject;
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

	public Object getCacheObject() {
		return cacheObject;
	}

	public void setCacheObject(Object cacheObject) {
		this.cacheObject = cacheObject;
	}
}
