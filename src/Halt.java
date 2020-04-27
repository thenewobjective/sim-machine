public class Halt extends Instruction {

        public Halt() {
		super(0);
	}

	@Override
	public void execute() {
		Machine.halt();
	}


}
