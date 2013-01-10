package pl.wjug.cglib.transform;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.transform.AbstractClassLoader;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;

import org.objectweb.asm.ClassReader;

/**
 * @author Maciej Jankowski
 * 
 */
public class TraceTransformingClassLoader extends AbstractClassLoader {
	private ClassTransformerFactory t;

	public TraceTransformingClassLoader(ClassLoader parent, ClassFilter filter,
			ClassTransformerFactory t) {
		super(parent, parent, filter);
		this.t = t;
	}

	protected ClassGenerator getGenerator(ClassReader r) {
		ClassTransformer t2 = (ClassTransformer) t.newInstance();
		return new TraceTransformingClassGenerator(super.getGenerator(r), t2);
	}

}
