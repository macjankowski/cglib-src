package pl.wjug.cglib.proxy;

/**
 * @author Maciej Jankowski
 * 
 */
public class MySession implements ISession {

	private boolean valid = true;

	@Override
	public String getSessionId() {
		return hashCode() + "";
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

}
