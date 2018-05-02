package com.lotus.database.sql;

import java.sql.ResultSet;
import java.util.List;

import com.lotus.orm.Filter;
import com.lotus.orm.Mapper;
import com.lotus.orm.Selector;

public class DynamicQuerySql extends QuerySql {

	public DynamicQuerySql(Mapper mapper, Selector selectors, Filter filter) {
		super(mapper, selectors, filter);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> List<T> parse(ResultSet rs) {
		return null;
	}

}
