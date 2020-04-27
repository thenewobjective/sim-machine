public class LoadImmediate extends Instruction {

        public LoadImmediate(int x) {
        	super(x);
	}

	@Override
	public void execute() {
        Machine.setRegister((operand[1]<<4) | operand[2], operand[0]);
	}


}
