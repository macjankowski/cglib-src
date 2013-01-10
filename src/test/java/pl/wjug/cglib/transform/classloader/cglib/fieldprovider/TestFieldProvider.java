package pl.wjug.cglib.transform.classloader.cglib.fieldprovider;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.TransformingClassLoader;
import net.sf.cglib.transform.impl.FieldProvider;
import net.sf.cglib.transform.impl.FieldProviderTransformer;
import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestFieldProvider extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();

		FieldProvider p = (FieldProvider) u;
		p.setField("name", "def");

		System.out.println(u.name);
	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestFieldProvider.class.getClassLoader();

		// define filter
		ClassFilter filter = new ClassFilter() {

			public boolean accept(String className) {
				return className.contains("TestFieldProvider")
						|| className.contains("User");
			}
		};

		// define transformer factory
		ClassTransformerFactory factory = new ClassTransformerFactory() {

			public ClassTransformer newInstance() {
				return new FieldProviderTransformer();
			}

		};

		// define class loader
		ClassLoader loader = new TransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader.loadClass(TestFieldProvider.class.getName());

		return new TestSuite(testClass);
	}
}
