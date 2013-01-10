package pl.wjug.cglib.proxy;

import java.lang.reflect.Proxy;
import java.util.Date;

import net.sf.cglib.proxy.Enhancer;

import org.junit.Test;

import pl.wjug.cglib.HelloClass;
import pl.wjug.cglib.HelloInterface;
import pl.wjug.cglib.cglibproxy.InfoInterceptor;
import pl.wjug.cglib.jdkproxy.HelloInvocationHandler;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestProxy {

	@Test
	public void testJdkProxy() throws Throwable {
		HelloInterface realSubject = new HelloClass();

		HelloInterface proxy = (HelloInterface) Proxy.newProxyInstance(
				realSubject.getClass().getClassLoader(), realSubject.getClass()
						.getInterfaces(), new HelloInvocationHandler(
						realSubject));

		proxy.printHelloWjug();
		
	}

	@Test
	public void testCglibProxy() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloClass.class);
		enhancer.setCallback(new InfoInterceptor());
		enhancer.setStrategy(new PrintBytecodeStrategy());

		HelloClass proxy = (HelloClass) enhancer.create();
		proxy.printHelloWjug();
	}
	
	@Test
	public void testDateAsCglibProxy() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Date.class);
		enhancer.setCallback(new InfoInterceptor());
//		enhancer.setStrategy(new PrintBytecodeStrategy());

		Date proxy = (Date) enhancer.create();
		System.out.println(proxy.getTime());
	}
	
	@Test
	public void testFinalMethod() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloClass.class);
		enhancer.setCallback(new InfoInterceptor());
		enhancer.setStrategy(new PrintBytecodeStrategy());

		HelloClass proxy = (HelloClass) enhancer.create();
		
		//won't be intercepted
		proxy.callFinalMethod();
	}
	
//	@Test
//	public void testCglibProxyInterface() throws Throwable {
//		Enhancer enhancer = new Enhancer();
//		enhancer.setSuperclass(HelloInterface.class);
//		enhancer.setCallback(new InfoInterceptor());
//
//		HelloInterface proxy = (HelloInterface) enhancer.create();
//		proxy.printHelloWjug();
//
//	}

}
