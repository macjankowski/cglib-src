package pl.wjug.cglib.cglibproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Maciej Jankowski
 * 
 */
public class InfoInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method m, Object[] args,
			MethodProxy proxy) throws Throwable {

		System.out.println("Before Calling " + m.getName());
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("After Calling " + m.getName());

		return result;
	}

}
