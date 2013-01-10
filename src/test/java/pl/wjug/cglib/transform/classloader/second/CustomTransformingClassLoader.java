package pl.wjug.cglib.transform.classloader.second;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.CodeSource;
import java.security.ProtectionDomain;

import net.sf.cglib.core.CodeGenerationException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import pl.wjug.cglib.transform.AddFieldAdapter;
import pl.wjug.cglib.transform.ChangeVersionAdapter;

/**
 * @author Maciej Jankowski
 * 
 */
public class CustomTransformingClassLoader extends ClassLoader {

	private ClassLoader parent;

	protected CustomTransformingClassLoader(ClassLoader parent) {
		this.parent = parent;
	}

	public Class loadClass(String name) throws ClassNotFoundException {

		Class loaded = findLoadedClass(name);

		if (loaded != null) {
			if (loaded.getClassLoader() == this) {
				return loaded;
			}// else reload with this class loader
		}

		if (!name.contains("TestTransformingClassLoader")
				&& !name.contains("User")) {
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
			ClassVisitor v = new AddFieldAdapter(w, Opcodes.ACC_PUBLIC
					+ Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,
					"myAddedField", "Ljava/lang/String;");

			v = new ChangeVersionAdapter(v);
			r.accept(v, 0);

			byte[] b = w.toByteArray();

			
			if (name.contains("User")) {
				ClassReader cr = new ClassReader(b);
				ClassWriter cw = new ClassWriter(0);
				ClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(
						System.out));
				cr.accept(cv, 0);
			}

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
