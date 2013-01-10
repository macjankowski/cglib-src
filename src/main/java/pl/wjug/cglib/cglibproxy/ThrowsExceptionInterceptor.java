package pl.wjug.cglib.cglibproxy;

import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Maciej Jankowski
 * 
 */
public class ThrowsExceptionInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method m, Object[] args,
			MethodProxy proxy) throws Throwable {

		throw new FileNotFoundException();
	}
}