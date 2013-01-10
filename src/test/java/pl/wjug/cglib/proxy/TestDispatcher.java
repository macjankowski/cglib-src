package pl.wjug.cglib.proxy;

import junit.framework.Assert;
import net.sf.cglib.proxy.Dispatcher;
import net.sf.cglib.proxy.Enhancer;

import org.junit.Test;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestDispatcher {

	private MySession session = new MySession();

	@Test
	public void testDispatcherCglib() throws Throwable {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(MySession.class);

		enhancer.setCallback(new Dispatcher() {

			public Object loadObject() throws Exception {
				// if session expired
				if (!session.isValid()) {
					session = new MySession();
				}
				return session;
			}
		});
		ISession proxy = (ISession) enhancer.create();

		String first = proxy.getSessionId();

		Assert.assertEquals(first, proxy.getSessionId());
		Assert.assertEquals(first, proxy.getSessionId());
		Assert.assertEquals(first, proxy.getSessionId());

		session.setValid(false);

		Assert.assertNotSame("session id should have changed", first,
				proxy.getSessionId());

	}
}
