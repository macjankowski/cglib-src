package pl.wjug.cglib.transform.classloader.second;

import pl.wjug.cglib.transform.user.cglib.User;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestTransformingClassLoader extends TestCase {

	public void testClassLoader() throws Throwable {

		User u = new User();
		Assert.assertEquals("Wrong classloader",
				"pl.wjug.cglib.transform.classloader.second.CustomTransformingClassLoader",
				u.getClass().getClassLoader().getClass().getName());
	}

	public static Test suite() throws Exception {

		CustomTransformingClassLoader classLoader = new CustomTransformingClassLoader(
				TestTransformingClassLoader.class.getClassLoader());
		Class<?> testClass = classLoader
				.loadClass(TestTransformingClassLoader.class.getName());

		return new TestSuite(testClass);
	}
}
