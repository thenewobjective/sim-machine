public class Jump extends Instruction {

	public Jump(int x) {
		super(x);
	}

	@Override
	public void execute() {
		// sets the PC to the bit pattern equal to the second and third operands 
		// only if the bit pattern in Register 0 is equal to the pattern in 
		// the Register at the address indicated by the first operand.
		if (Machine.getRegister(operand[0]) == Machine.getRegister(0))
			Machine.setPC((operand[1]<<4) | operand[2]);
	}

}
