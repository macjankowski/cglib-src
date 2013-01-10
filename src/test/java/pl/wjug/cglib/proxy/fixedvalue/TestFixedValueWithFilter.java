package pl.wjug.cglib.proxy.fixedvalue;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import pl.wjug.cglib.cglibproxy.ClassWithGetters;

import com.google.caliper.Runner;
import com.google.caliper.SimpleBenchmark;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestFixedValueWithFilter extends SimpleBenchmark {

	public String timeCallbackFilterWithFixedValue(int reps) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ClassWithGetters.class);
		enhancer.setCallbackFilter(new CallbackFilter() {
			public int accept(Method method) {
				if ("getFirst".equals(method.getName())) {
					return 0;
				}

				if ("getSecond".equals(method.getName())) {
					return 1;
				}

				if ("getThird".equals(method.getName())) {
					return 2;
				}
				return 0;
			}
		});
		enhancer.setCallbacks(new Callback[] { new FixedValue() {
			public Object loadObject() throws Exception {
				return "a";
			}
		},

		new FixedValue() {
			public Object loadObject() throws Exception {
				return "b";
			}
		},

		new FixedValue() {
			public Object loadObject() throws Exception {
				return "c";
			}
		} });

		ClassWithGetters proxy = (ClassWithGetters) enhancer.create();

		String res1 = null, res2 = null, res3 = null;
		for (int i = 0; i < reps; ++i) {
			res1 = proxy.getFirst();
			res2 = proxy.getSecond();
			res3 = proxy.getThird();
		}

		return res1 + res2 + res3;

	}

	public String timeMethodInterceptor(int reps) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ClassWithGetters.class);
		enhancer.setCallback(new MethodInterceptor() {

			public Object intercept(Object obj, Method method, Object[] args,
					MethodProxy proxy) throws Throwable {

				if ("getFirst".equals(method.getName())) {
					return "a";
				}

				if ("getSecond".equals(method.getName())) {
					return "b";
				}

				if ("getThird".equals(method.getName())) {
					return "c";
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

	public static void main(String[] args) throws Exception {
		Runner.main(TestFixedValueWithFilter.class, args);
	}

}
