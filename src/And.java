public class And extends Instruction {

        public And(int x) {
		super(x);
	}

	@Override
	public void execute() {
		Machine.setRegister((Machine.getRegister(operand[1]) & Machine.getRegister(operand[2])) & 0xff, operand[0]);
	}


}
