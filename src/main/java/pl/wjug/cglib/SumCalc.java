package pl.wjug.cglib;

import java.util.Random;

/**
 * @author Maciej Jankowski
 * 
 */
public class SumCalc implements SumInterface {

	private Random r = new Random();
	private double sum = 0;

	public void addRandom() {
		sum += r.nextDouble()+2*r.nextDouble()/4;
	}

	public double getSum() {
		return sum;
	}

	public int getInterceptedCount() {
		return 0;
	}
}
