package com.lotus.di;
/**
 * 应用上下文
 * @author yalik_wang
 *
 */
public class LotusContext {
	public LotusContext(String configPath) {
		
	}
	/**
	 * 获取bean对象
	 * @param clazz
	 * @return
	 */
	public <T extends Object> T getBean(Class<T> clazz){
		return null;
	}
}
