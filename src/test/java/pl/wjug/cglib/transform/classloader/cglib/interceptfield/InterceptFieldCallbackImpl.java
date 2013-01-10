package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import net.sf.cglib.transform.impl.AbstractInterceptFieldCallback;

/**
 * @author Maciej Jankowski
 * 
 */
public class InterceptFieldCallbackImpl extends AbstractInterceptFieldCallback {

	private String returnedName;

	public InterceptFieldCallbackImpl(String returnedName) {
		this.returnedName = returnedName;
	}

	@Override
	public Object readObject(Object obj, String name, Object oldValue) {
		System.out.println("Intercepted reading field \"" + name
				+ "\" of class \"" + obj.getClass().getSimpleName() + "\"");
		return returnedName;
	}
}