package pl.wjug.cglib.transform;

import java.io.PrintWriter;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.transform.ClassTransformer;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 * @author Maciej Jankowski
 * 
 */
public class TraceTransformingClassGenerator implements ClassGenerator {

	private ClassGenerator gen;
	private ClassTransformer t;

	public TraceTransformingClassGenerator(ClassGenerator gen,
			ClassTransformer t) {
		this.gen = gen;
		this.t = t;
	}

	public void generateClass(ClassVisitor v) throws Exception {
		t.setTarget(new TraceClassVisitor(v, new PrintWriter(System.out)));
		gen.generateClass(t);
	}

}
