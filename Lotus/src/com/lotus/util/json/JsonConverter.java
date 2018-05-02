package com.lotus.util.json;

public interface JsonConverter {
	
	String toJson(Object obj);
	
	<T extends Object> T fromJson(String json,Class<?> T);
}
