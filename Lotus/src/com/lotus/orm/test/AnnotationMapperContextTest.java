package com.lotus.orm.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.lotus.orm.AnnotationMapperContext;
import com.lotus.orm.Mapper;

public class AnnotationMapperContextTest {
	AnnotationMapperContext mapperContext = new AnnotationMapperContext();
	@Test
	public void testCreateEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testScanConfiguration() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntityMapper() {
		Mapper mapper = mapperContext.getMapper("teapot");
		assertEquals(mapper.getTableName(), "teapot");
		assertEquals(mapper.getEntityName(), "teapot");
		assertEquals(mapper.getAllProperties().size()>0, true);
		assertEquals(mapper.getProperty("isJavaBean"), null);
	}

}
