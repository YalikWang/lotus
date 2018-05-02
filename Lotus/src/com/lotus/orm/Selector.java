package com.lotus.orm;

import java.util.ArrayList;
import java.util.List;

public class Selector {

	private List<String> selectors = new ArrayList<>();

	public Selector(String... selctors) {
		for (String s : selctors) {
			selectors.add(s);
		}
	}

	public void add(String s) {
		selectors.add(s);
	}

	public List<String> getSelectors() {
		return selectors;
	}

	public void setSelectors(List<String> selectors) {
		this.selectors = selectors;
	}

}
