package pl.wjug.cglib.cglibproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Maciej Jankowski
 * 
 */
public class ProfileInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method m, Object[] args,
			MethodProxy proxy) throws Throwable {

		long start = System.nanoTime();
		Object result = proxy.invokeSuper(obj, args);
		long end = System.nanoTime();
		System.out.println(m.getName()+" took " + (end - start) + " nanoseconds");
		return result;
	}
}
