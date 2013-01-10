package pl.wjug.cglib.proxy;

import java.lang.reflect.Proxy;

import junit.framework.Assert;

import net.sf.cglib.proxy.Enhancer;
import pl.wjug.cglib.SumCalc;
import pl.wjug.cglib.SumInterface;
import pl.wjug.cglib.cglibproxy.SumInterceptor;
import pl.wjug.cglib.jdkproxy.SumInvocationHandler;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestProxyPerformanceCaliper extends SimpleBenchmark {

	public double timeWithoutProxy(int reps) {

		SumCalc realSubject = new SumCalc();

		for (int i = 0; i < reps; ++i) {
			realSubject.addRandom();
		}

		return realSubject.getInterceptedCount();
	}

	public double timeJdkProxySum(int reps) {

		SumInterface realSubject = new SumCalc();
		SumInterface proxy = (SumInterface) Proxy.newProxyInstance(realSubject
				.getClass().getClassLoader(), realSubject.getClass()
				.getInterfaces(), new SumInvocationHandler(realSubject));

		for (int i = 0; i < reps; ++i) {
			proxy.addRandom();
		}

		Assert.assertEquals("Intercepted count should equal reps", reps,
				proxy.getInterceptedCount());

		return proxy.getInterceptedCount();
	}

	public double timeCGLibProxySum(int reps) {

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(SumCalc.class);
		enhancer.setCallback(new SumInterceptor());

		SumInterface proxy = (SumInterface) enhancer.create();

		for (int i = 0; i < reps; ++i) {
			proxy.addRandom();
		}

		Assert.assertEquals("Intercepted count should equal reps", reps,
				proxy.getInterceptedCount());
		return proxy.getInterceptedCount();
	}

	public static void main(String[] args) throws Exception {
		Runner.main(TestProxyPerformanceCaliper.class, args);
	}
}
