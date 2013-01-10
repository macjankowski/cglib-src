package pl.wjug.cglib.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;
import pl.wjug.cglib.cglibproxy.ClassWithGetters;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestNoOp extends SimpleBenchmark {

	public String timeMethodInterceptor(int reps) throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ClassWithGetters.class);
		enhancer.setCallback(new MethodInterceptor() {

			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {

				if ("getFirst".equals(method.getName())) {
					return "a";
				}

				return proxy.invokeSuper(obj, args);
			}
		});

		ClassWithGetters proxy = (ClassWithGetters) enhancer.create();

		String res1 = null, res2 = null, res3 = null;
		for (int i = 0; i < reps; ++i) {
			res1 = proxy.getFirst();
			res2 = proxy.getSecond();
			res3 = proxy.getThird();
		}
		return res1 + res2 + res3;
	}

	public String timeNoOpCglib(int reps) throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ClassWithGetters.class);
		enhancer.setCallbackFilter(new CallbackFilter() {
			public int accept(Method method) {
				if ("getFirst".equals(method.getName())) {
					return 0;
				}

				return 1;
			}
		});
		enhancer.setCallbacks(new Callback[] { new FixedValue() {
			public Object loadObject() throws Exception {
				return "a";
			}
		},

		NoOp.INSTANCE });

		ClassWithGetters proxy = (ClassWithGetters) enhancer.create();

		String res1 = null, res2 = null, res3 = null;
		for (int i = 0; i < reps; ++i) {
			res1 = proxy.getFirst();
			res2 = proxy.getSecond();
			res3 = proxy.getThird();
		}
		return res1 + res2 + res3;
	}

	public static void main(String[] args) throws Exception {
		Runner.main(TestNoOp.class, args);
	}
}
