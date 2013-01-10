package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import java.io.IOException;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.CodeGenerationException;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.transform.AbstractClassLoader;
import net.sf.cglib.transform.ClassFilter;
import net.sf.cglib.transform.ClassReaderGenerator;
import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;
import net.sf.cglib.transform.TransformingClassGenerator;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class MyClassLoader extends ClassLoader {
	private ClassFilter filter;
	private ClassLoader classPath;
	private static java.security.ProtectionDomain DOMAIN;

	static {

		DOMAIN = (java.security.ProtectionDomain) java.security.AccessController
				.doPrivileged(new java.security.PrivilegedAction() {
					public Object run() {
						return AbstractClassLoader.class.getProtectionDomain();
					}
				});
	}

	protected MyClassLoader(ClassLoader parent, ClassLoader classPath,
			ClassFilter filter) {
		super(parent);
		this.filter = filter;
		this.classPath = classPath;
	}

	private ClassTransformerFactory t;

	public MyClassLoader(ClassLoader parent, ClassFilter filter,
			ClassTransformerFactory t) {
		this(parent, parent, filter);
		this.t = t;
	}

	public Class loadClass(String name) throws ClassNotFoundException {

		Class loaded = findLoadedClass(name);

		if (loaded != null) {
			if (loaded.getClassLoader() == this) {
				return loaded;
			}// else reload with this class loader
		}

		if (!filter.accept(name)) {
			return super.loadClass(name);
		}
		ClassReader r;
		try {

			java.io.InputStream is = classPath.getResourceAsStream(name
					.replace('.', '/') + ".class");

			if (is == null) {

				throw new ClassNotFoundException(name);

			}
			try {

				r = new ClassReader(is);

			} finally {

				is.close();

			}
		} catch (IOException e) {
			throw new ClassNotFoundException(name + ":" + e.getMessage());
		}

		try {

			if (name.contains("TestUserClass")) {
				ClassWriter w = new DebuggingClassWriter(
						ClassWriter.COMPUTE_MAXS);
				r.accept(w, 0);
				byte[] b = w.toByteArray();
				return super.defineClass(name, b, 0, b.length, DOMAIN);
			} else {
				ClassWriter w = new DebuggingClassWriter(
						ClassWriter.COMPUTE_MAXS);
				getGenerator(r).generateClass(w);
				byte[] b = w.toByteArray();
				Class c = super.defineClass(name, b, 0, b.length, DOMAIN);
				postProcess(c);
				return c;
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Error e) {
			throw e;
		} catch (Exception e) {
			throw new CodeGenerationException(e);
		}
	}

	protected ClassGenerator getGenerator2(ClassReader r) {
		return new ClassReaderGenerator(r, attributes(), getFlags());
	}

	protected ClassGenerator getGenerator(ClassReader r) {
		ClassTransformer t2 = (ClassTransformer) t.newInstance();
		return new TransformingClassGenerator(getGenerator2(r), t2);
	}

	protected int getFlags() {
		return 0;
	}

	protected Attribute[] attributes() {
		return null;
	}

	protected void postProcess(Class c) {
	}
}
