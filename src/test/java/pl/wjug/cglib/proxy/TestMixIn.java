package pl.wjug.cglib.proxy;

import junit.framework.TestCase;
import net.sf.cglib.proxy.Mixin;

import org.junit.Test;

import pl.wjug.cglib.mixin.CanDrive;
import pl.wjug.cglib.mixin.CanFloat;
import pl.wjug.cglib.mixin.Car;
import pl.wjug.cglib.mixin.Motorboat;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestMixIn {

	@Test
	public void testMixin() throws Exception {

		CanDrive car = new Car();
		CanFloat motorboat = new Motorboat();

		Mixin amphibian = Mixin.create(new Object[] { car, motorboat });
		
		TestCase.assertEquals("bzzz bzzz bzzz ...", ((CanFloat) amphibian)._float());
		TestCase.assertEquals("pyr pyr pyr pyr ...", ((CanDrive) amphibian).drive());
	}
}
