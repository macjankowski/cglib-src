package pl.wjug.cglib.transform.classloader.cglib.delegate;

/**
 * @author Maciej Jankowski
 * 
 */
public interface AdminActions {

	public void shutdownSystem();

	public void changePassword(String newPassword);

}
