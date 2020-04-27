public class Store extends Instruction {

        public Store(int x) {
		super(x);
	}

	@Override
	public void execute() {
		Machine.setMemoryCell(Machine.getRegister(operand[0]), (operand[1]<<4) | operand[2]);
	}


}
