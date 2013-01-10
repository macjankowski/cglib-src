package pl.wjug.cglib.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Maciej Jankowski
 * 
 */
public class SumInvocationHandler implements InvocationHandler {

	private Object realSubject = null;
	private int count = 0;

	public SumInvocationHandler(Object realSubject) {
		this.realSubject = realSubject;
	}

	public Object invoke(Object proxy, Method m, Object[] args) {

		if (m.getName().equals("getInterceptedCount")) {
			return count;
		}

		Object result = null;
		try {
			count++;
			result = m.invoke(realSubject, args);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return result;
	}
}
