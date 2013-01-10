package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.impl.InterceptFieldEnabled;
import pl.wjug.cglib.transform.TraceTransformingClassLoader;
import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestInterceptFieldClassLoader extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();

		assertEquals("adam", u.name);
		InterceptFieldEnabled en = (InterceptFieldEnabled) u;
		en.setInterceptFieldCallback(new InterceptFieldCallbackImpl("janek"));
		
		assertEquals("Should return \"janek\"", "janek", u.name);
	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestInterceptFieldClassLoader.class
				.getClassLoader();

		// define filter
		ClassFilter filter = new InterceptFieldClassFilter();

		// define transformer factory
		ClassTransformerFactory factory = new InterceptTransformerFactory();

		// define class loader
		ClassLoader loader = new TraceTransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader.loadClass(TestInterceptFieldClassLoader.class
				.getName());

		return new TestSuite(testClass);
	}
}
