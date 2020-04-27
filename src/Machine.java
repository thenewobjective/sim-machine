//package SIM;

import java.io.BufferedWriter;
import java.io.File; // For serialize() & serialize()
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.json.*;

public final class Machine {

	/*
	 * public static void main(String args[]) { <For Testing> }
	 */

	// VARIABLES:
	// Final variables
	private static final int REGISTER_LOC_SIZE = 0xF;
	private static final int MEMORY_LOC_SIZE = 0xFF;

	// State variables
	private static int ir, pc;
	private static int[] registers = new int[REGISTER_LOC_SIZE + 1];
	private static int[] memory = new int[MEMORY_LOC_SIZE + 1];
	private static Timer timer;

	// PUBLIC INTERFACE FUNCTIONS:
	public static void run() {
		timer.schedule(new TimerTask() {
			public void run() {
				step();
			}
		}, 0, 1000);
	}

	public static void step() {
		fetch();
		decode().execute();
	}

	public static void halt() {
		timer.cancel();
		timer = null;
	}

	public static boolean isrunning() {
		return timer != null;
	}

	private static void fetch() {
		setIR(Integer.parseInt(Integer.toHexString(memory[getPC()]) + Integer.toHexString(memory[getPC() + 1]),16));
		setPC(getPC()+2);
		//update GUI stuff
		
		
		
//		int val1 = getPC();
//		setPC(getPC() + 1);
//		int val2 = getPC();
//
//		String value = Integer.toHexString(val1);
//		value += Integer.toHexString(val2);
//
//		int instruction = Integer.parseInt(value, 16);
//
//		setIR(instruction);
//
//		setPC(getPC() + 2);

	}

	private static int toOperand(int opcode) {
		if (opcode >= 0x1000 && opcode < 0x2000)
			return opcode - (0x1000);
		if (opcode >= 0x2000 && opcode < 0x3000)
			return opcode - (0x2000);
		if (opcode >= 0x3000 && opcode < 0x4000)
			return opcode - (0x3000);
		if (opcode >= 0x4000 && opcode < 0x5000)
			return opcode - (0x4000);
		if (opcode >= 0x5000 && opcode < 0x6000)
			return opcode - (0x5000);
		if (opcode >= 0x6000 && opcode < 0x7000)
			return opcode - (0x6000);
		if (opcode >= 0x7000 && opcode < 0x8000)
			return opcode - (0x7000);
		if (opcode >= 0x8000 && opcode < 0x9000)
			return opcode - (0x8000);
		if (opcode >= 0x9000 && opcode < 0xA000)
			return opcode - (0x9000);
		if (opcode >= 0xA000 && opcode < 0xB000)
			return opcode - (0xA000);
		if (opcode >= 0xB000 && opcode < 0xC000)
			return opcode - (0xB000);
		if (opcode == 0xC000)
			return 0;
		throw new IllegalArgumentException("Invalid opcode in IR");

	}

	// Decode
	// Class Stub is a stub for Instruction
	private static Instruction decode() {

		// Separate Operand and opcode to call the instructor

		if (0x1000 <= ir && ir <= 0x1FFF) return new Load(toOperand(ir));
		if (0x2000 <= ir && ir <= 0x2FFF) return new LoadImmediate(toOperand(ir));
		if (0x3000 <= ir && ir <= 0x3FFF) return new Store(toOperand(ir));
		if (0x4000 <= ir && ir <= 0x4FFF) return new Move(toOperand(ir));
		if (0x5000 <= ir && ir <= 0x5FFF) return new Add(toOperand(ir));
		if (0x6000 <= ir && ir <= 0x6FFF) //return new AddFloat(toOperand(ir)); too complicated for now
		if (0x7000 <= ir && ir <= 0x7FFF) return new Or(toOperand(ir));
		if (0x8000 <= ir && ir <= 0x8FFF) return new And(toOperand(ir));
		if (0x9000 <= ir && ir <= 0x9FFF) return new Xor(toOperand(ir));
		if (0xA000 <= ir && ir <= 0xAFFF) return new Rotate(toOperand(ir));
		if (0xB000 <= ir && ir <= 0xBFFF) return new Jump(toOperand(ir));
		if (0xC000 == ir)return new Halt();
		return null;
	}

	// Getters
	public static int getIR() {
		return ir;
	}

	public static int getPC() {
		return pc;
	}

	public static int getRegister(int loc) {
		return registers[loc];
	}

	public static int getMemoryCell(int loc) {
		return memory[loc];
	}

	// Setters
	public static void setIR(int newIR) { // Finished
		ir = newIR;
	}

	public static void setPC(int newPC) {
		pc = newPC;
	}

	public static void setRegister(int newReg, int loc) {
		registers[loc] = newReg;
	}

	public static void setMemoryCell(int newMem, int loc) {
		memory[loc] = newMem;
	}

	// Serialize and Deserialize (Non-Functional)
	public static void serialize(File fil) throws IOException {
		// saving

		JSONObject json = new JSONObject();
		BufferedWriter fileOut = new BufferedWriter(new FileWriter(fil));

		try {

			json.put("pc", pc);
			json.put("ir", ir);
			json.put("registers", registers);
			json.put("memory", memory);
			System.out.print(json.toString(5));
			fileOut.write(json.toString(5));
			fileOut.close();

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void deserialize(File fil) {
		
		try {
			Scanner fin = new Scanner(new FileReader(fil));
			String src = "";
			while(fin.hasNextLine()){
				src+= fin.nextLine();
			}
			JSONObject json = new JSONObject(src);
			fin.close();
			
			pc = json.getInt("pc");
			ir = json.getInt("ir");
			
		    JSONArray mem = (JSONArray) json.get("memory");
			for(int i = 0 ; i< memory.length; i++)
				memory[i] = mem.getInt(i);
			
			JSONArray reg = (JSONArray) json.get("registers");
			for(int i = 0; i< registers.length; i++)
				registers[i] = reg.getInt(i);
		
		} catch (Exception e) {System.out.println("Error: " + e.getMessage());}
		//update gui
	}

}