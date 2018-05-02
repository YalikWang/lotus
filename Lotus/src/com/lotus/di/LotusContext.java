package com.lotus.di;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 应用上下文
 * @author yalik_wang
 *
 */
public class LotusContext {
	List<Bean> beans =new ArrayList<>();
	public LotusContext(String configPath) {
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(configPath);
			initConfig(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void initConfig(Document document) {
		Element rootElement = document.getRootElement();
		List<Element> beans = rootElement.elements("Bean");
		for(Element e:beans) {
			String name = e.attributeValue("name");
			String path = e.attributeValue("class");
			Bean bean = new Bean();
			bean.setName(name);
			bean.setPath(path);
			this.beans.add(bean);
		}
	}
	/**
	 * 获取bean对象
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getBean(Class<T> clazz){
		String name = clazz.getName();
		for(Bean bean:beans) {
			if(name.equals(bean.getPath())) {
				try {
					try {
						return (T) Class.forName(bean.getPath()).newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T extends Object> T getBean(String beanName) {
		for(Bean bean:beans) {
			if(beanName.equals(bean.getName())) {
				try {
					return (T) Class.forName(bean.getPath()).newInstance();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
