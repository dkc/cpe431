package LLVMObjects;

import java.util.ArrayList;
import java.util.Collections;

import com.sun.org.apache.xpath.internal.axes.ReverseAxesWalker;

public class LLVMToSPARC {

	protected static ArrayList<Mapping> mappings = new ArrayList<Mapping>();
	
	public static void convertLLVM(ArrayList<LLVMLine> funcdecs, ArrayList<LLVMLine> compiledCode) {
		ArrayList<LLVMLine> program = (ArrayList<LLVMLine>) funcdecs.clone();
		program.addAll(compiledCode);
		
		for(LLVMLine line : program)
		{
			// System.out.println(line.toString());
		}
		
		LLVMLine currentLine;
		boolean newBlock;
		int unlabeledBlockCounter = 0;
		int comparisonBranchCounter = 0;
		String SPARCcode;
		ArrayList<BasicBlock> blocksList = new ArrayList<BasicBlock>();
		BasicBlock currentBlock = new BasicBlock("STARTING_BLOCK");

		for(int lineNumber = 0; lineNumber < program.size(); lineNumber++) {
			
			SPARCcode = "";
			newBlock = false;
			currentLine = program.get(lineNumber);
			if(currentLine.getLabel() != null)
			{
				// currentLine.setLabel(currentLine.getLabel().replaceAll("%", ""));
			}
			
			if(currentLine.getOperation() == null) {
				// some LLVM syntax-related code, ignore this
				
			} else if(currentLine.getOperation().equals("label")) {
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
			} else if(	currentLine.getOperation().equals("icmp eq") ||
						currentLine.getOperation().equals("icmp slt")) {
				// CMP (op, op)

				SPARCcode += "\tmov\t" + "0" + ", " + currentLine.getRegisterDefined() + "\n";
				if(currentLine.getNumConstants() > 0)
					SPARCcode += "\tcmp\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getConstantUsed(0) + "\n";
				else
					SPARCcode += "\tcmp\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterUsed(1) + "\n";
				
				if(currentLine.getOperation().equals("icmp eq")) // stays zero if not equal
					SPARCcode += "\tbne\t";
				else if(currentLine.getOperation().equals("icmp slt")) // stays zero if greater than or equal
					SPARCcode += "\tbge\t";
				else if(currentLine.getOperation().equals("icmp sgt")) // stays zero if less than or equal
					SPARCcode += "\tble\t";
				else if(currentLine.getOperation().equals("icmp sle")) // stays zero if greater than
					SPARCcode += "\tbg\t";
				else if(currentLine.getOperation().equals("icmp sge")) // stays zero if less than
					SPARCcode += "\tbl\t";
				
				SPARCcode += "cmpbranch_" + comparisonBranchCounter + "\n";
				SPARCcode += "\tnop" + "\n";
				SPARCcode += "\tmov\t" + "1" + ", " + currentLine.getRegisterDefined() + "\n";
				SPARCcode += "cmpbranch_" + (comparisonBranchCounter++) + ":";
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
				
				SPARCcode += "! bitcast removed by the LLVM->SPARC translation; replaced with move" + "\n";
				SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterDefined();
			} else if(currentLine.getOperation().equals("inttoptr")) {
				// unnecessary in SPARC
				
				SPARCcode += "! inttoptr removed by the LLVM->SPARC translation";
			} else if (currentLine.getOperation().equals("zext")) {
				
				SPARCcode += "! zext removed by the LLVM->SPARC translation" + "\n";
				SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + currentLine.getRegisterDefined();
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
				if(currentLine.getNumConstants() > 0)
					SPARCcode += "\tmov\t" + currentLine.getConstantUsed(0) + ", " + "%i0" + "\n";
				else
					SPARCcode += "\tmov\t" + currentLine.getRegisterUsed(0) + ", " + "%i0" + "\n";
				SPARCcode += "\tret" + "\n";
				SPARCcode += "\trestore";
			} else if(currentLine.getOperation().equals("call")) {
				// move arguments into "output" registers %o0 -> %o5 (?... 1-5?)
				// call the label with the correct name
				// move the result from %o0 back into the register defined, if applicable
				
				currentLine.setOperation("call");
				currentBlock.addTargetBlock(currentLine.getLabel());
				SPARCcode += "\tcall\t" + currentLine.getLabel() + "\n";
				SPARCcode += "\tnop";
				if(currentLine.getRegisterDefined() != null)
					SPARCcode += "\n" + "\tmov\t" + "%o0" + ", " + currentLine.getRegisterDefined();
				
				newBlock = true;
				SPARCcode += "\n";
			} else if(currentLine.getOperation().equals("br")) {
				// branch unconditionally to a target label
				
				currentLine.setOperation("ba");
				currentBlock.addTargetBlock(currentLine.getLabel());
				SPARCcode += "\tba\t" + currentLine.getLabel() + "\n";
				SPARCcode += "\tnop";
				
				newBlock = true;
				SPARCcode += "\n";
			} else if(currentLine.getOperation().equals("br i1")) {
				// branch to one of two target labels depending on the value in the first register "0"
				// beware of the bad hack going on here--registers "1" and "2" are being used to store the label names
				// planning on using subcc, brz, ba; remember nops
				
				currentLine.setOperation("ba");
				currentBlock.addTargetBlock(currentLine.getRegisterUsed(1));
				currentBlock.addTargetBlock(currentLine.getRegisterUsed(2));
				SPARCcode += "\taddcc\t" + currentLine.getRegisterUsed(0) + ", " + "0" + ", " + "%g0" + "\n";
				SPARCcode += "\tbz\t" + currentLine.getRegisterUsed(1) + "\n";
				SPARCcode += "\tnop\t" + "\n";
				SPARCcode += "\tba\t" + currentLine.getRegisterUsed(2) + "\n";
				SPARCcode += "\tnop\t";
				
				newBlock = true;
				SPARCcode += "\n";
			} else if(currentLine.getOperation().equals("fundec")) {
				/* drop a label matching the function name and map all of the live on entry stuff
				 * to the input registers we expect them in */				

				blocksList.add(currentBlock);
				currentBlock = new BasicBlock(currentLine.getLabel());
				
				currentLine.setOperation("define");
				SPARCcode += currentLine.getLabel() + ":" + "\n";
				SPARCcode += "\tsave\t" + "%sp" + ", " + "-96" + ", " + "%sp";
				
				int mapInputs = 0;
				for(String reg : currentLine.registersUsed) {
					if(!mappings.contains(reg))
						mappings.add(new Mapping(reg, "%i" + mapInputs++));
					else
					{
						System.err.println("attempting to remap a register name! most curious!!!");
						System.exit(1);
					}
				}
				
			} else {
				// don't have a rule for this yet (though we may not want one); mark it conspicuously
				
				SPARCcode += "\t???\t" + "[" + currentLine.getCode() + "]";
				SPARCcode = SPARCcode.replaceAll("\n", "");
			}
			
			currentLine.setSPARCTranslation(SPARCcode);
			currentBlock.addLine(currentLine);
			if(newBlock == true) {
				blocksList.add(currentBlock);
				currentBlock = new BasicBlock("UNLABELEDBLOCK_" + unlabeledBlockCounter++);
			}
		}
		
		blocksList.add(currentBlock);
		
		for(LLVMLine line : program) {
			// System.out.println(line.getSPARCTranslation());
		}

		mapRegisters(blocksList);
	}
	
	@SuppressWarnings("unchecked")
	public static void mapRegisters(ArrayList<BasicBlock> program) {
		ArrayList<String> liveRegisters;
		LLVMLine currentLine;
		Conflict currentConflict;
		
		for(BasicBlock block : program) {
			liveRegisters = new ArrayList<String>();
			
			// we want to walk the lines of code in the block in reverse order to generate the interference graph
			for(int lineNum = block.contents.size()-1; lineNum >= 0; lineNum--) {
				currentLine = block.contents.get(lineNum);
				
				if(		currentLine.getOperation().equals("ba") ||
						currentLine.getOperation().equals("brz") ||
						currentLine.getOperation().equals("label") ){
					
					continue;
				}
				
				if(currentLine.getRegisterDefined() != null) {
					if(mappings.contains(currentLine.getRegisterDefined())) {
						currentLine.setRegisterDefined(mappings.get(mappings.indexOf(new Mapping("reg", null))).mappingSPARC);
					}
					
					if(!liveRegisters.remove(currentLine.getRegisterDefined())) {
						// then a register is being defined but not used later in the same basic block
						block.liveOnExit.add(currentLine.getRegisterDefined());
					}
					for(String liveRegister : liveRegisters) {
						currentConflict = new Conflict(liveRegister, currentLine.getRegisterDefined());
						if(!block.conflicts.contains(currentConflict))
							block.conflicts.add(currentConflict);
					}
				}
				
				for(String registerUsed : currentLine.registersUsed)
					if(!liveRegisters.contains(registerUsed))
						liveRegisters.add(registerUsed);
			}
			
			for(String registerUsed : liveRegisters) {
				block.liveOnEntry.add(registerUsed);
			}

			for(Mapping m : mappings)
				block.replace(m);
		}
		
		for(BasicBlock block : program) {
			int mappingLocation;
			ArrayList<String> availableMappings = new ArrayList<String>();
			ArrayList<String> conflictingRegisters;
			ArrayList<String> takenMappings;
			ArrayList<String> registersToMap;
			for(int x = 0; x < 8; x++) {
				availableMappings.add("%i" + x);
				availableMappings.add("%l" + x);
			}
			
			for(String inputRegister : block.liveOnEntry) {
				if(availableMappings.contains(inputRegister))
					availableMappings.remove(new Mapping(inputRegister, null));
				else
					for(Mapping m : mappings)
						if(m.originalLLVM.equals(inputRegister))
							availableMappings.remove(m.mappingSPARC);
			}
			
			for(LLVMLine curLine: block.contents) {
				if(curLine.getOperation().equals("ba"))
					continue;
				registersToMap = curLine.registersUsed;
				if(curLine.getRegisterDefined() != null)
					registersToMap.add(curLine.getRegisterDefined());
				for(String register : registersToMap)
					if(!mappings.contains(new Mapping(register, null))) {
						conflictingRegisters = new ArrayList<String>();
						for(Conflict c : block.conflicts) {
							if(c.reg1.equals(register))
								conflictingRegisters.add(c.reg2);
							else if(c.reg2.equals(register))
								conflictingRegisters.add(c.reg1);
						}
						// System.out.println("conflicts: " + conflictingRegisters.toString());
						takenMappings = new ArrayList<String>();
						for(String conflictingRegister : conflictingRegisters) {
							mappingLocation = mappings.indexOf(new Mapping(conflictingRegister, null));
							if(mappingLocation > -1) {
								takenMappings.add(mappings.get(mappingLocation).mappingSPARC);
							}
						}
						// System.out.println(takenMappings.toString());
						for(String availableMapping : availableMappings) {
							if(!takenMappings.contains(availableMapping)) {
								mappings.add(new Mapping(register, availableMapping));
								break;
							}
						}
					}
			}
		}
		
		Collections.sort(mappings);
		Collections.reverse(mappings);
		for(BasicBlock block : program)
			for(Mapping m : mappings)
				block.replace(m);
		
		System.out.println("pcGreeting: .asciz \"Hello world.\"\\n");

	    System.out.println("\t.section \".text\"");

	    System.out.println("\t.align 4");
	  
	    System.out.println("\t.global main");

		System.out.println("main:");
		System.out.println("\tsave\t%sp, -96, %sp");
		System.out.println("\tcall\tllvm_fun");
		System.out.println("\tnop");
		System.out.println("\tret");
		System.out.println("\trestore");
		
		for(BasicBlock block : program) {
			//if(block.contents.size() > 0)
			//	System.out.println(block.toString());
			
			for(LLVMLine l : block.contents)
				System.out.println(l.getSPARCTranslation());
		}
	}
}
