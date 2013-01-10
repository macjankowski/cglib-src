package pl.wjug.cglib.transform.classloader.first;

import java.io.IOException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import net.sf.cglib.core.CodeGenerationException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author Maciej Jankowski
 * 
 */
public class CustomClassLoader extends ClassLoader {

	private ClassLoader parent;

	protected CustomClassLoader(ClassLoader parent) {
		this.parent = parent;
	}

	public Class loadClass(String name) throws ClassNotFoundException {

		Class loaded = findLoadedClass(name);

		if (loaded != null) {
			if (loaded.getClassLoader() == this) {
				return loaded;
			}// else reload with this class loader
		}

		if (!name.contains("TestSimpleClassLoader") && !name.contains("User")) {
			return super.loadClass(name);
		}
		ClassReader r;
		try {

			java.io.InputStream is = parent.getResourceAsStream(name.replace(
					'.', '/') + ".class");

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
			ClassWriter w = new ClassWriter(0);
			r.accept(w, 0);
			byte[] b = w.toByteArray();
			return super.defineClass(name, b, 0, b.length, getDefaultDomain());
		} catch (RuntimeException e) {
			throw e;
		} catch (Error e) {
			throw e;
		} catch (Exception e) {
			throw new CodeGenerationException(e);
		}
	}

	private ProtectionDomain defaultDomain = null;

	private synchronized ProtectionDomain getDefaultDomain() {
		if (defaultDomain == null) {
			CodeSource cs = new CodeSource(null,
					(java.security.cert.Certificate[]) null);
			defaultDomain = new ProtectionDomain(cs, null, this, null);
		}
		return defaultDomain;
	}

}
