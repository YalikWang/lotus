package com.lotus.model;

import java.lang.reflect.Field;

public class BeanProperty extends Property {
	private Field field;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(Object obj) {
		try {
			return (T) field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
}
