package com.lotus.orm;

public class Filter {
	public static final String equals = " = ";
	public static final String not_equals = " != ";
	public static final String in = " in ";
	public static final String larger = " > ";
	public static final String less = " < ";
	private String name;
	private String operator;
	private Object value;
	public Filter(String name,String cp,Object value) {
		this.name = name;
		this.operator = cp;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
