package pl.wjug.cglib.cglibproxy.lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maciej Jankowski
 * 
 */
public class HeavyParent extends Parent {

	private static int counter = 0;
	private List<Child> children = new ArrayList<Child>();

	public HeavyParent() {
		System.out.println("Created HeavyParent");
		counter++;

		// load children from network
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(10);
				children.add(new Child("child " + i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Child> getChildren() {
		return children;
	}

	public void printCounter() {
		System.out.println("counter = " + counter);
	}
}
