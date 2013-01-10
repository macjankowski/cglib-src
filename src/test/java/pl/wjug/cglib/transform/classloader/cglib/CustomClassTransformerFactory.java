package pl.wjug.cglib.transform.classloader.cglib;

import net.sf.cglib.transform.ClassTransformer;
import net.sf.cglib.transform.ClassTransformerFactory;

import org.objectweb.asm.Opcodes;

/**
 * @author Maciej Jankowski
 * 
 */
public class CustomClassTransformerFactory implements ClassTransformerFactory {

	public ClassTransformer newInstance() {
		return new CustomTransformer(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL
				+ Opcodes.ACC_STATIC, "myField", "Ljava/lang/String;");
	}

}
