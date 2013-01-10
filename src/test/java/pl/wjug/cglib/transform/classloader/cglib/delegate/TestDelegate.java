package pl.wjug.cglib.transform.classloader.cglib.delegate;

import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.TransformingClassLoader;
import net.sf.cglib.transform.impl.AddDelegateTransformer;
import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski <maciej.jankowski@ser-solutions.pl>
 * 
 */
public class TestDelegate extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();
		
		AdminActions admin = (AdminActions)u;
		admin.changePassword("changeit");
		admin.shutdownSystem();

	}

	private Method[] getMethods(Class clazz) {
		return clazz.getDeclaredMethods();
	}

	public void printMessage() {
		System.out.println("Message!!!!!!!!!!");
	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestDelegate.class.getClassLoader();

		// define filter
		ClassFilter filter = new ClassFilter() {

			public boolean accept(String className) {
				return className.contains("TestDelegate")
						|| className.contains("User");
			}
		};

		// define transformer factory
		ClassTransformerFactory factory = new ClassTransformerFactory() {

			public ClassTransformer newInstance() {
				return new AddDelegateTransformer(
						new Class[] { AdminActions.class }, AdminDelegate.class);
			}

		};

		// define class loader
		ClassLoader loader = new TransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader.loadClass(TestDelegate.class.getName());

		return new TestSuite(testClass);
	}
}
