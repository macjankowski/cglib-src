package pl.wjug.cglib;

/**
 * @author Maciej Jankowski
 * 
 */
public class HelloClass implements HelloInterface {

	public HelloClass() {
//		System.out.println("Called HelloClass constructor");
	}

	public void printHelloWjug() {
		System.out.println("Hello Warszawa JUG");
	}

	public void printHelloWorld() {
		System.out.println("Hello World");
	}

	public final void callFinalMethod() {
		System.out.println("Final method");
	}
}
