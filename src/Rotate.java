public class Rotate extends Instruction {

	public Rotate(int x) {
		super(x);
	}

	@Override
	public void execute() {
		// Number of bits to rotate right
		int rotateNum = (operand[2] % 8);
		// Byte to be operated on
		int srcRegVal = Machine.getRegister(operand[1]) & 0xff;

		// Save ls bits from being shifted away
		// Shift displaced bits left to become ms bits
		int savedBits = srcRegVal<<(8-rotateNum) & 0xff;

		// Perform bit shift
		int result = srcRegVal>>>rotateNum;

		// Add ls bits back in
		result = result | savedBits;

		// Write to destination register
		Machine.setRegister(result, operand[0]);
	}


}
