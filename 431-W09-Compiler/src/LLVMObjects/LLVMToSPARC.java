package LLVMObjects;

import java.util.ArrayList;

public class LLVMToSPARC {
	public static void convertLLVM(ArrayList<LLVMLine> funcdecs, ArrayList<LLVMLine> compiledCode) {
		ArrayList<LLVMLine> program = (ArrayList<LLVMLine>) funcdecs.clone();
		program.addAll(compiledCode);
		
		for(LLVMLine line : program)
		{
			// System.out.println(line.toString());
		}
		
		LLVMLine currentLine;
		String SPARCcode;
		ArrayList<BasicBlock> blocksList = new ArrayList<BasicBlock>();
		BasicBlock currentBlock = new BasicBlock("STARTING_BLOCK");
		
		for(int lineNumber = 0; lineNumber < program.size(); lineNumber++) {
			
			SPARCcode = "";
			currentLine = program.get(lineNumber);
			if(currentLine.getOperation() == "label") {
				/* any time we see a new label we want to start a new basic block */
				
				blocksList.add(currentBlock);
				currentBlock = new BasicBlock(currentLine.getLabel());
				SPARCcode += currentLine.getLabel() + ":";
			}
			else if(currentLine.getOperation().equals("add")) {
				// ADD (op+op->%reg)
				// MOV (%reg->%reg)
				// MOV (const->%reg)
				
				if(currentLine.getNumConstants() == 0) {
					if(currentLine.getNumRegistersUsed() == 2) {
						// then we have two registers to add
						currentLine.setOperation("add"); // redundant, but here for consistency
						SPARCcode += "\tadd\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + ", " + currentLine.getRegisterDefined();
					} else {
						// pack your bags cause we've got a move yay
						currentLine.setOperation("mov");
						SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterDefined();
					}
				} else { // we have at least one constant
					if(currentLine.getNumRegistersUsed() == 1) { 
						currentLine.setOperation("add");
						SPARCcode += "\tadd\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getConstantUsed(0) + ", " + currentLine.getRegisterDefined();
					} else { // we have no registers involved and 1 or 2 constants
						currentLine.setOperation("set");
						SPARCcode += "\tset\t" + currentLine.getConstantSum() + ", " + currentLine.getRegisterDefined();
					}
				}
			} else if(currentLine.getOperation().equals("sub")) {
				// SUB (op-op->%reg)
				// as far as I can tell we will only ever subtract two regs! constants get loaded and shifted before use
				
				currentLine.setOperation("sub"); // redundant, but here for consistency
				SPARCcode += "\tsub\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("mul")) {
				// SMUL (op-op->%reg), probably SPARC v8-specific, doubt this works for floats
				
				currentLine.setOperation("smul");
				SPARCcode += "\tsmul\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("udiv")) {
				// SDIV (op-op->%reg), probably SPARC v8-specific, doubt this works for floats
				
				currentLine.setOperation("sdiv");
				SPARCcode += "\tsdiv\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("shl")) {
				// SLL (%reg-shift->%reg)
				
				currentLine.setOperation("sll");
				SPARCcode += "\tsll\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getConstantUsed(0) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("lshr")) {
				// SRL (%reg-shift->%reg)
				
				currentLine.setOperation("srl");
				SPARCcode += "\tsrl\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getConstantUsed(0) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("getelementptr")) {
				// ADD (op+op->%reg) -- this just moves the pointer for store; very simplistic solution, wastes an instruction
				
				currentLine.setOperation("add");
				SPARCcode += "\tadd\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getConstantSum() + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("store")) {
				// ST from, [to]
				
				currentLine.setOperation("st");
				SPARCcode += "\tst\t" + currentLine.getRegisterUsed(0) + ", [" + currentLine.getRegisterUsed(1) + "]";
			} else if(currentLine.getOperation().equals("load")) {
				// LD [from], to
				
				currentLine.setOperation("ld");
				SPARCcode += "\tld\t[" + currentLine.getRegisterUsed(0) + "], " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("bitcast")) {
				// unnecessary in SPARC
				
				SPARCcode += "! bitcast removed by the LLVM->SPARC translation";
			} else if(currentLine.getOperation().equals("inttoptr")) {
				// unnecessary in SPARC
				
				SPARCcode += "! inttoptr removed by the LLVM->SPARC translation";
			} else if(currentLine.getOperation().equals("malloc")) {
				// set %o0 to bytes wanted, call malloc, nop, mov return val %o0 to "defined" register
				
				currentLine.setOperation("malloc");
				SPARCcode += "\tset\t" + currentLine.getConstantSum() + ", " + "%o0" + "\n";
				SPARCcode += "\tcall\t" + "malloc" + "\n";
				SPARCcode += "\tnop"+ "\n";
				SPARCcode += "\tmov\t" + "%o0" + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("ret")) {
				// move register used to %i0, ret, restore
				
				currentLine.setOperation("ret");
				SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + "%i0" + "\n";
				SPARCcode += "\tret" + "\n";
				SPARCcode += "\trestore"; 
			} else if(currentLine.getOperation().equals("call")) {
				// move arguments into "output" registers %o0 -> %o5 (?... 1-5?)
				// call the label with the correct name
				
				currentLine.setOperation("call");
				currentBlock.addTargetBlock(currentLine.getLabel());
				SPARCcode += "\tcall\t" + currentLine.getLabel() + "\n";
				SPARCcode += "\tnop";
			} else if(currentLine.getOperation().equals("br")) {
				// branch unconditionally to a target label
				
				currentLine.setOperation("ba");
				currentBlock.addTargetBlock(currentLine.getLabel());
				SPARCcode += "\tba\t" + currentLine.getLabel();
			} else if(currentLine.getOperation().equals("br i1")) {
				// branch to one of two target labels depending on the value in the first register "0"
				// beware of the bad hack going on here--registers "1" and "2" are being used to store the label names
				// planning on using subcc, brz, ba; remember nops
				
				currentLine.setOperation("ba");
				currentBlock.addTargetBlock(currentLine.getRegisterUsed(1));
				currentBlock.addTargetBlock(currentLine.getRegisterUsed(2));
			} else {
				// don't have a rule for this yet; mark it conspicuously
				
				SPARCcode += "\t???\t" + "[" + currentLine.getCode() + "]";
				SPARCcode = SPARCcode.replaceAll("\n", "");
			}
			
			/*	ba{,a} label branch always
				bn{,a} label branch never
				bne{,a} label branch on not equal
				be{,a} label branch on equal
				bg{,a} label branch on greater
				ble{,a} label branch on less or equal
				bge{,a} label branch on greater or equal
				bl{,a} label branch on less
				bz
			*/
			
			currentLine.setSPARCTranslation(SPARCcode);
			currentBlock.addLine(currentLine);
		}
		
		blocksList.add(currentBlock);
		
		for(LLVMLine line : program) {
			// System.out.println(line.getSPARCTranslation());
		}
		
		for(BasicBlock block : blocksList) {
			System.out.println(block.toString());
		}
	}
	
	public static void mapRegisters(ArrayList<BasicBlock> program) {
		for(BasicBlock block : program)
			System.out.println(block.toString());
	}
}
