package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import pl.wjug.cglib.transform.user.cglib.User;

/**
 * @author Maciej Jankowski
 * 
 */
public class TestUserClass {

	public String getUserName(User u) {
		return u.name;
	}
}
