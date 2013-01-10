package pl.wjug.cglib.proxy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.xml.bind.JAXBException;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.transform.AbstractClassTransformer;
import net.sf.cglib.transform.TransformingClassGenerator;
import net.sf.cglib.transform.impl.AddPropertyTransformer;
import net.sf.cglib.transform.impl.UndeclaredThrowableStrategy;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import pl.wjug.cglib.HelloClass;
import pl.wjug.cglib.WithException;
import pl.wjug.cglib.cglibproxy.InfoInterceptor;
import pl.wjug.cglib.cglibproxy.ProfileInterceptor;
import pl.wjug.cglib.cglibproxy.ThrowsExceptionInterceptor;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestStrategy {

	@Test
	public void testStrategy() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new ProfileInterceptor());
		enhancer.setSuperclass(HelloClass.class);
		enhancer.setStrategy(new DefaultGeneratorStrategy() {

			@Override
			protected byte[] transform(byte[] b) throws Exception {
				ClassReader classReader = new ClassReader(b);
				ClassVisitor cVisitor = new TraceClassVisitor(new PrintWriter(
						System.out));
				classReader.accept(cVisitor, ClassReader.SKIP_DEBUG);
				return b;
			}

			protected ClassGenerator transform(ClassGenerator cg) {

				return new TransformingClassGenerator(cg,
						new AddPropertyTransformer(
								new String[] { "foo", "boo" }, new Type[] {
										Type.INT_TYPE, Type.FLOAT_TYPE }));
			}
		});
		HelloClass proxy = (HelloClass) enhancer.create();
		proxy.printHelloWjug();

	}

	@Test(expected = JAXBException.class)
	public void testUndeclaredThrowableStrategyIntercepted() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new ThrowsExceptionInterceptor());
		enhancer.setSuperclass(WithException.class);
		enhancer.setStrategy(new UndeclaredThrowableStrategy(
				JAXBException.class));
		WithException proxy = (WithException) enhancer.create();

		proxy.throwsUndeclaredException();
	}

	@Test(expected = FileNotFoundException.class)
	public void testUndeclaredThrowableStrategy() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setCallback(new ThrowsExceptionInterceptor());
		enhancer.setSuperclass(WithException.class);
		enhancer.setStrategy(new UndeclaredThrowableStrategy(
				RuntimeException.class));
		WithException proxy = (WithException) enhancer.create();

		proxy.throwsDeclaredException();
	}

}
