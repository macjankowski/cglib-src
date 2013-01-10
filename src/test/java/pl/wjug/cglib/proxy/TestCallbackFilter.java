package pl.wjug.cglib.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;

import org.junit.Test;

import pl.wjug.cglib.HelloClass;
import pl.wjug.cglib.cglibproxy.InfoInterceptor;
import pl.wjug.cglib.cglibproxy.ProfileInterceptor;

/**
 * @author Maciej Jankowski
 *
 */
public class TestCallbackFilter {

	@Test
	public void testCallbackFilter() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloClass.class);
		
		enhancer.setCallbackFilter(new CallbackFilter() {
			
			public int accept(Method method) {
				if("printHelloWjug".equals(method.getName())){
					return 0;
				}
				return 1;
			}
		});
		enhancer.setCallbacks(new Callback[]{
				new InfoInterceptor(), 
				new ProfileInterceptor()
		});
		

		HelloClass proxy = (HelloClass) enhancer.create();
		proxy.printHelloWjug();
		proxy.printHelloWorld();

	}
}
