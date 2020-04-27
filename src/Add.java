public class Add extends Instruction {
	
	public Add(int x) {
		super(x);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Machine.setRegister((Machine.getRegister(operand[1]) + Machine.getRegister(operand[2])) & 0xff, operand[0]);
	}

}
