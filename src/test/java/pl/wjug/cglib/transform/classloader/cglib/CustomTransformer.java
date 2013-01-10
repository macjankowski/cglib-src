package pl.wjug.cglib.transform.classloader.cglib;

import net.sf.cglib.transform.ClassEmitterTransformer;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Maciej Jankowski
 * 
 */
public class CustomTransformer extends ClassEmitterTransformer {

	private int fAcc;
	private String fName;
	private String fDesc;
	private boolean isFieldPresent;

	public CustomTransformer(int fAcc, String fName, String fDesc) {
		this.fAcc = fAcc;
		this.fName = fName;
		this.fDesc = fDesc;
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
		cv.visit(Opcodes.V1_6, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		if (name.equals(fName)) {
			isFieldPresent = true;
		}
		return cv.visitField(access, name, desc, signature, value);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
		if (!isFieldPresent) {
			FieldVisitor fv = cv.visitField(fAcc, fName, fDesc, null, null);
			if (fv != null) {
				fv.visitEnd();
			}
		}
		cv.visitEnd();
	}
}
