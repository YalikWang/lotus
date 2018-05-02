package com.lotus.orm;

import java.util.HashMap;
import java.util.Map;

public abstract class MapperContext {
	
	private static Map<String, Mapper> context = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T extends Mapper> T getMapper(String entity) {
		//第一次请求需要进行配置扫描
		if (context.isEmpty()) {
			Map<String, Mapper> scanConfiguration = scanConfiguration();
			context.putAll(scanConfiguration);
		}
		Mapper mapper = context.get(entity.toLowerCase());
		if (mapper == null) {
			// 没有相关实体的配置信息
			throw new RuntimeException(String.format("系统中不存在【%s】的配置，请检查相关Java注解或者XML配置", entity));
		}
		return (T) mapper;
	}

	public abstract <T extends Object> T createEntity(String entity);
	
	public abstract Map<String,Mapper> scanConfiguration();
	
}
