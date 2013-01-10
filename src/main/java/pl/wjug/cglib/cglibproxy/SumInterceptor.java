/**
 * 
 */
package pl.wjug.cglib.cglibproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Maciej Jankowski
 * 
 */
public class SumInterceptor implements MethodInterceptor {

	private int count = 0;

	public SumInterceptor() {
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {

		if (proxy.getSignature().getName().equals("getInterceptedCount")) {
			return count;
		}

		Object result = null;

		count++;
		result = proxy.invokeSuper(obj, args);

		return result;
	}

}
