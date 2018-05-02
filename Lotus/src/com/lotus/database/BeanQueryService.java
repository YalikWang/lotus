package com.lotus.database;

import java.util.List;

import com.lotus.orm.Filter;
import com.lotus.orm.Selector;

public interface BeanQueryService {
	/**
	 * 
	 * @param entity
	 * @param selector 需要查询的字段，如果需要全部查询，则可以设置为*
	 * @param filter
	 * @return
	 */
	<T extends Object> List<T> query(String entity, Selector selector, Filter filter);
}
