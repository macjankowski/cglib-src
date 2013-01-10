package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import net.sf.cglib.transform.ClassFilter;

/**
 * @author Maciej Jankowski
 * 
 */
public class InterceptFieldClassFilter implements ClassFilter {

	public boolean accept(String className) {
		return className.contains("TestInterceptFieldFailed")
				|| className.contains("TestInterceptFieldClassLoader")
				|| className.contains("User");
	}

}
