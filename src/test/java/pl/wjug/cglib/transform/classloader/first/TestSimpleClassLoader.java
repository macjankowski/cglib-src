package pl.wjug.cglib.transform.classloader.first;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestSimpleClassLoader extends TestCase {

	public void testClassLoader() throws Throwable {

		User u = new User();
		Assert.assertEquals("Wrong classloader",
				"pl.wjug.cglib.transform.classloader.first.CustomClassLoader",
				u.getClass().getClassLoader().getClass().getName());
	}

	public static Test suite() throws Exception {

		CustomClassLoader classLoader = new CustomClassLoader(
				TestSimpleClassLoader.class.getClassLoader());

		Class<?> testClass = classLoader.loadClass(TestSimpleClassLoader.class
				.getName());

		return new TestSuite(testClass);
	}

}
