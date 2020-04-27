public class Load extends Instruction {

	public Load(int x) {
		super(x);
	}

	@Override
	public void execute() {
		// Sets the Register at the address of operand 1 to the bit pattern of the second and third operands
		Machine.setRegister(Machine.getMemoryCell((operand[1]<<4) | operand[2]), operand[0]);
	}


}
