package LLVMObjects;

import java.util.ArrayList;

public class LLVMToSPARC {
	public static void convertLLVM(ArrayList<LLVMLine> funcdecs, ArrayList<LLVMLine> compiledCode) {
		ArrayList<LLVMLine> program = (ArrayList<LLVMLine>) funcdecs.clone();
		program.addAll(compiledCode);
		
		LLVMLine currentLine;
		String SPARCcode;
		for(int lineNumber = 0; lineNumber < program.size(); lineNumber++) {
			SPARCcode = "";
			currentLine = program.get(lineNumber);
			if(currentLine.getOperation() == null) {
				// we currently expect this to be a label (which is a clunky assumption; my bad)
			}
			else if(currentLine.getOperation().equals("add")) {
				// ADD (op+op->%reg)
				// MOV (%reg->%reg)
				// MOV (const->%reg)
				
				if(currentLine.getNumConstants() == 0) {
					if(currentLine.getNumRegistersUsed() == 0) {
						// then we have two registers to add
						currentLine.setOperation("add"); // redundant, but here for consistency
						SPARCcode += "\tadd\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + ", " + currentLine.getRegisterDefined();
					} else {
						// pack your bags cause we've got a move yay
						currentLine.setOperation("mov");
						SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterDefined();
					}
				} else if(currentLine.getNumConstants() == 1) {
					// then we have no registers involved
					currentLine.setOperation("mov");
					SPARCcode += "\tset\t" + currentLine.getConstantUsed(0) + ", " + currentLine.getRegisterDefined();
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
				SPARCcode += "! bitcast removed by the LLVM->SPARC translation";
			} else if(currentLine.getOperation().equals("malloc")) {
				
			} else if(currentLine.getOperation().equals("")) {
				
			}
			
			currentLine.setSPARCTranslation(SPARCcode);
		}
		
		for(LLVMLine line : program)
		{
			System.out.println(line.getSPARCTranslation());
		}
	}
}
