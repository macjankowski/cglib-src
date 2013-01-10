package pl.wjug.cglib.transform.classloader.cglib.delegate;

/**
 * @author Maciej Jankowski
 * 
 */
public class AdminDelegate {

	private Object target;

	public AdminDelegate(Object target) {
		this.target = target;
	}

	public void shutdownSystem() {
		System.out.println("System is going to shut down");
	}

	public void changePassword(String newPassword) {
		System.out.println("Password changed");
	}
}
