package pl.wjug.asm;

import static org.objectweb.asm.Opcodes.ACC_ABSTRACT;
import static org.objectweb.asm.Opcodes.ACC_FINAL;
import static org.objectweb.asm.Opcodes.ACC_INTERFACE;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.V1_5;

import java.io.PrintWriter;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

import pl.wjug.cglib.transform.ChangeVersionAdapter;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestClassVisitor {

	@Test
	public void testPrinter() throws Throwable {
		ClassReader reader = new ClassReader(
				"pl.wjug.cglib.cglibproxy.lazy.HeavyParent");
		printClass(reader);
	}

	@Test
	public void testWriter() throws Throwable {
		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
				"pkg/Comparable", null, "java/lang/Object",
				new String[] { "java/util/List" });
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null,
				new Integer(-1)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null,
				new Integer(0)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
				null, new Integer(1)).visitEnd();
		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
				"(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		byte[] b = cw.toByteArray();

		ClassReader reader = new ClassReader(b);
		printClass(reader);

		MyClassLoader myClassLoader = new MyClassLoader();
		Class clazz = myClassLoader.defineClass("pkg.Comparable", b);
		System.out.println(clazz.getName());
	}

	private void printClass(ClassReader reader) {
		TraceClassVisitor printer = new TraceClassVisitor(new PrintWriter(System.out));
		reader.accept(printer, 0);
	}

	@Test
	public void testTransformClass() throws Throwable {

		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
				"pkg/Comparable", null, "java/lang/Object",
				new String[] { "java/util/List" });
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null,
				new Integer(-1)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null,
				new Integer(0)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
				null, new Integer(1)).visitEnd();
		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
				"(Ljava/lang/Object;)I", null, null).visitEnd();
		cw.visitEnd();
		byte[] b = cw.toByteArray();

		System.out.println("\n\nBefore transformation\n\n");
		ClassReader reader = new ClassReader(b);
		printClass(reader);

		// cv forwards all events to cw
		cw = new ClassWriter(0);
		ClassVisitor cv = new ChangeVersionAdapter(cw);
		ClassReader cr = new ClassReader(b);
		cr.accept(cv, 0);
		byte[] b2 = cw.toByteArray(); // b2 represents the same class as b1

		System.out.println("\n\nAfter transformation\n\n");
		reader = new ClassReader(b2);
		printClass(reader);
	}
}

class MyClassLoader extends ClassLoader {
	public Class defineClass(String name, byte[] b) {
		return defineClass(name, b, 0, b.length);
	}
}
