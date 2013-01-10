package pl.wjug.cglib.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Maciej Jankowski
 * 
 */
public class HelloInvocationHandler implements InvocationHandler {

	public HelloInvocationHandler(Object realSubject) {
		this.realSubject = realSubject;
	}

	public Object invoke(Object proxy, Method m, Object[] args) {
		Object result = null;
		System.out.println("Before Calling " + m.getName());
		try {
			result = m.invoke(realSubject, args);
		} catch (Exception ex) {
			System.exit(1);
		}
		System.out.println("After Calling " + m.getName());
		return result;
	}

	private Object realSubject = null;
}
