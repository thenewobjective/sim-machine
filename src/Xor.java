public class Xor extends Instruction {

	public Xor(int x) {
		super(x);
	}

	@Override
	public void execute() {
		// Directly perform Java XOR and mask to 8 bits
		Machine.setRegister((Machine.getRegister(operand[1])^
				Machine.getRegister(operand[2]))&0xff,operand[0]);
	}
	
}
