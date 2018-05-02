package com.lotus.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
/**
 * AOP动态代理
 * @author yalik_wang
 *
 */
public class AbstractProxy implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] params, MethodProxy arg3) throws Throwable {
		//根据obj的类型及method去读取配置
		//如果有方法执行前相关配置，则执行该配置
		Object result = null;
		try {
			result = method.invoke(obj, params);
		}catch(Throwable t) {
			//如果有相关配置，则执行
		}
		//如果操作后有相关配置，执行
		return result;
	}

}
