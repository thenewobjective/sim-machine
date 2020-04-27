public class Move extends Instruction {

        public Move(int x) {
		super(x);
	}

	@Override
	public void execute() {
		Machine.setRegister(Machine.getRegister(operand[1]), operand[2]);
	}


}
