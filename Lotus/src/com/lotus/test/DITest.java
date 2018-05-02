package com.lotus.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.lotus.di.LotusContext;
import com.lotus.test.pojo.Teacher;
/**
 * 依赖注入特性测试
 * @author yalik_wang
 *
 */
public class DITest {
	
	@Test
	public void testSimpleDI() {
		//初始化配置信息
		LotusContext lotusContext = new LotusContext("E:\\JavaProject\\git\\Lotus\\src\\com\\lotus\\Lotus.xml");
		//获取Bean信息
		Teacher teacher = lotusContext.getBean("Teacher");
		assertEquals(teacher!=null, true);
		//查看结果
		teacher.teachKnowledge();
	}
}
