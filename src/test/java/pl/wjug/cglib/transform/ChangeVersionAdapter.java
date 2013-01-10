package pl.wjug.cglib.transform;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Maciej Jankowski
 * 
 */
public class ChangeVersionAdapter extends ClassAdapter {
	public ChangeVersionAdapter(ClassVisitor cv) {
		super(cv);
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		cv.visit(Opcodes.V1_6, access, name, signature, superName, interfaces);
	}
	
	
}
