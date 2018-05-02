package com.lotus.database;

import com.lotus.model.dynamic.DynamicObject;
import com.lotus.orm.Filter;
import com.lotus.orm.Selector;

public interface QueryService {
	
	<T extends Object> T queryBean(String entity, Selector selctor, Filter filter);

	DynamicObject queryDynamic(String entity, Selector selctor, Filter filter);

}
