package pl.wjug.cglib.transform.classloader.cglib.addinit;

import java.lang.reflect.Field;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.TransformingClassLoader;
import net.sf.cglib.transform.impl.AddStaticInitTransformer;
import pl.wjug.cglib.transform.user.cglib.User;

public class TestAddInit extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();
		Assert.assertEquals("Timeout should have been modified by static hook",
				60, u.timeout);
	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestAddInit.class.getClassLoader();

		// define filter
		ClassFilter filter = new ClassFilter() {

			public boolean accept(String className) {
				return className.contains("TestAddInit")
						|| className.contains("User");
			}
		};

		// define transformer factory
		ClassTransformerFactory factory = new ClassTransformerFactory() {

			public ClassTransformer newInstance() {
				try {
					return new AddStaticInitTransformer(
							TestAddInit.class.getMethod("init", Class.class));
				} catch (SecurityException e) {
					throw new RuntimeException(e);
				} catch (NoSuchMethodException e) {
					throw new RuntimeException(e);
				}
			}

		};

		// define class loader
		ClassLoader loader = new TransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader.loadClass(TestAddInit.class.getName());

		return new TestSuite(testClass);
	}

	public static void init(Class clazz) {

		try {
			if (clazz.getName().endsWith("User")) {
				Field field = clazz.getField("timeout");
				field.setInt(null, 60);
			} else {
			}
		} catch (Exception ex) {

		}
	}

}
