package pl.wjug.cglib.transform.classloader.cglib;

import net.sf.cglib.transform.ClassFilter;

/**
 * @author Maciej Jankowski
 * 
 */
public class CustomClassFilter implements ClassFilter {

	public boolean accept(String className) {
		return className.contains("TestCGLibClassLoader")
				|| className.contains("User");
	}

}
