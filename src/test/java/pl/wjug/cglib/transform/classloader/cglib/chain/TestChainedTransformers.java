package pl.wjug.cglib.transform.classloader.cglib.chain;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerChain;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.TransformingClassLoader;
import net.sf.cglib.transform.impl.AddDelegateTransformer;
import net.sf.cglib.transform.impl.AddStaticInitTransformer;
import pl.wjug.cglib.transform.classloader.cglib.addinit.TestAddInit;
import pl.wjug.cglib.transform.classloader.cglib.delegate.AdminActions;
import pl.wjug.cglib.transform.classloader.cglib.delegate.AdminDelegate;
import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestChainedTransformers extends TestCase {

	public void testClassLoader() throws Throwable {
		User u = new User();
		Assert.assertEquals("Timeout should have been modified by static hook",
				60, u.timeout);

		AdminActions admin = (AdminActions) u;
		admin.changePassword("changeit");
		admin.shutdownSystem();
	}

	public static Test suite() throws Exception {

		// parent class loader
		ClassLoader parentCL = TestChainedTransformers.class.getClassLoader();

		// define filter
		ClassFilter filter = new ClassFilter() {

			public boolean accept(String className) {
				return className.contains("TestChainedTransformers")
						|| className.contains("User");
			}
		};

		// define transformer factory
		ClassTransformerFactory factory = new ClassTransformerFactory() {

			public ClassTransformer newInstance() {
				ClassTransformer t1 = new AddDelegateTransformer(
						new Class[] { AdminActions.class }, AdminDelegate.class);

				ClassTransformer t2;
				try {
					t2 = new AddStaticInitTransformer(
							TestAddInit.class.getMethod("init", Class.class));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				return new ClassTransformerChain(new ClassTransformer[] { t1,
						t2 });
			}

		};

		// define class loader
		ClassLoader loader = new TransformingClassLoader(parentCL, filter,
				factory);

		Class testClass = loader.loadClass(TestChainedTransformers.class
				.getName());

		return new TestSuite(testClass);
	}

}
