
public class Or extends Instruction {

	public Or(int x) {
		super(x);
	}

	@Override
	public void execute() {
		Machine.setRegister(Machine.getRegister(operand[1]) | Machine.getRegister(operand[2]), operand[0]);
	}

}
