package pl.wjug.cglib.cglibproxy.lazy;

import java.util.List;

/**
 * @author Maciej Jankowski
 * 
 */
public abstract class Parent {

	protected Long id = 0l;
	protected String name = "parent";

	public abstract List<Child> getChildren();

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public abstract void printCounter();
}
