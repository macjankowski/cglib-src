package pl.wjug.cglib.proxy.fixedvalue;

import junit.framework.Assert;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;

import org.junit.Test;

import pl.wjug.cglib.cglibproxy.ClassWithGetters;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestFixedValue {

	@Test
	public void testCallbackFilterWithFixedValue() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(ClassWithGetters.class);
		enhancer.setCallback(new FixedValue() {
			public Object loadObject() throws Exception {
				return "a";
			}
		});

		ClassWithGetters proxy = (ClassWithGetters) enhancer.create();

		Assert.assertEquals("Should return \"a\"", "a", proxy.getFirst());
		Assert.assertEquals("Should return \"a\"", "a", proxy.getSecond());
		Assert.assertEquals("Should return \"a\"", "a", proxy.getThird());
	}
}
