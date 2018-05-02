package com.lotus.database;

import com.lotus.model.dynamic.DynamicObject;
import com.lotus.orm.Filter;
import com.lotus.orm.Selector;

public interface DynamicQueryService {
	DynamicObject query(String entity, Selector selector, Filter filter);
}
