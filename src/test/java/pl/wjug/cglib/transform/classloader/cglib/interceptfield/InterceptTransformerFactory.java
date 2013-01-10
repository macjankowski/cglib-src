package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.impl.InterceptFieldTransformer;

/**
 * @author Maciej Jankowski
 * 
 */
public class InterceptTransformerFactory implements ClassTransformerFactory {

	public ClassTransformer newInstance() {
		return new InterceptFieldTransformer(new InterceptFilter());
	}

}