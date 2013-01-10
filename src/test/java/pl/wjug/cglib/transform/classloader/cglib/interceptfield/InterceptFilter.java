package pl.wjug.cglib.transform.classloader.cglib.interceptfield;

import org.objectweb.asm.Type;
import net.sf.cglib.transform.impl.InterceptFieldFilter;

/**
 * @author Maciej Jankowski <maciej.jankowski@ser-solutions.pl>
 * 
 */
public class InterceptFilter implements InterceptFieldFilter {

	public boolean acceptRead(Type owner, String name) {
		return true;
	}

	public boolean acceptWrite(Type owner, String name) {
		return true;
	}

}
