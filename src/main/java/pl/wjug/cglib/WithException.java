package pl.wjug.cglib;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

/**
 * @author Maciej Jankowski
 * 
 */
public class WithException {

	public void throwsUndeclaredException() throws JAXBException{
		System.out.println("Declares JAXBException");
	}

	public void throwsDeclaredException() throws FileNotFoundException {
		System.out.println("Declares FileNotFoundException");
	}
}
