package vm;

import java.nio.ByteBuffer;
import java.lang.instrument.Instrumentation;
import tokens.ParserException;

public class VirtualMachine {

	private final static int STACK_SIZE = 32 * 1024;
	private ByteBuffer stack;
	private int stackPointer;

	public VirtualMachine() {
		stack = ByteBuffer.allocate(STACK_SIZE);
		stack.clear();
		stackPointer = 0;
		
	}

	void pushd(double d) {
		if (stack.remaining() < 8)
			throw new ParserException("NO STACK FOR DOUBLE");
		stack.putDouble(d);
		stackPointer += 8;

	}

	double popd() {
		stackPointer -= 8;
		if (stackPointer < 0)
			throw new ParserException("CAN'T POP Double, no such thing in stack");
		stack = (ByteBuffer) stack.position(stackPointer);
		stackPointer+=8;
		return stack.getDouble();
	}
	
	
	void pusha(VMObject a)
	{
		
	}

}
