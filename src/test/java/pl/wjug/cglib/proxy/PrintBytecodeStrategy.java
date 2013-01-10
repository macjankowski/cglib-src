package pl.wjug.cglib.proxy;

import java.io.PrintWriter;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * @author Maciej Jankowski
 * 
 */
public class PrintBytecodeStrategy extends DefaultGeneratorStrategy {

	@Override
	protected byte[] transform(byte[] b) throws Exception {
		ClassReader classReader = new ClassReader(b);
		ClassVisitor cVisitor = new TraceClassVisitor(new PrintWriter(
				System.out));
		cVisitor = new DebuggingClassWriter(ClassWriter.COMPUTE_MAXS);
		classReader.accept(cVisitor, ClassReader.SKIP_DEBUG);
		return b;
	}
}
