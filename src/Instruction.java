public abstract class Instruction {

	protected int[] operand = new int[3];
	
	public Instruction(int x){
		//shifts
		operand[0] = (x>>>8) & 0xf;
		operand[1] = (x>>>4) & 0xf;
		operand[2] = x & 0xf;
	}
	
	public abstract void execute();
}
