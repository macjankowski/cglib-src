package pl.wjug.cglib.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.LazyLoader;
import net.sf.cglib.proxy.NoOp;

import org.junit.Test;

import pl.wjug.cglib.cglibproxy.lazy.Child;
import pl.wjug.cglib.cglibproxy.lazy.HeavyParent;
import pl.wjug.cglib.cglibproxy.lazy.Parent;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestLazyLoader {

	@Test
	public void testLazyLoaderCGLib() throws Throwable {

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Parent.class);

		enhancer.setCallbackFilter(new CallbackFilter() {
			public int accept(Method method) {
				if ("getChildren".equals(method.getName())) {
					return 0;
				}

				return 1;
			}
		});
		enhancer.setCallbacks(new Callback[] { new LazyLoader() {
			public Object loadObject() throws Exception {
				return new HeavyParent();
			}
		},

		NoOp.INSTANCE });

		Parent proxy = (Parent) enhancer.create();
		System.out.println(proxy.getName());
		System.out.println(proxy.getId());

		List<Child> children = proxy.getChildren();
		for (Child child : children) {
			System.out.println(child.getName());
		}

	}
}
