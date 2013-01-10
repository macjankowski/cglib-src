package pl.wjug.cglib.transform.classloader.cglib;

import java.lang.reflect.Field;

import pl.wjug.cglib.transform.user.cglib.User;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.TransformingClassLoader;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestCGLibClassLoader extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();
		Class<User> uClazz = (Class<User>) u.getClass();
		Field[] fields = uClazz.getFields();
		for (Field field : fields) {
			System.out.println(field.getName());
		}

	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestCGLibClassLoader.class.getClassLoader();

		// define filter
		ClassFilter filter = new CustomClassFilter();

		// define transformer factory
		CustomClassTransformerFactory factory = new CustomClassTransformerFactory();

		// define class loader
		ClassLoader loader = new TransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader
				.loadClass(TestCGLibClassLoader.class.getName());

		return new TestSuite(testClass);
	}

}
