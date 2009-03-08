package LLVMObjects;

import java.util.ArrayList;

public class BasicBlock {
	
	protected String blockName;
	protected ArrayList<LLVMLine> contents;
	
	protected ArrayList<String> liveOnEntry;
	protected ArrayList<String> liveOnExit;
	protected ArrayList<String> targetBlocks;
	protected ArrayList<Conflict> conflicts;
	
	/* kind of a weird name here, sorry--if the end of a block is not an unconditional branch or
	 * call, we need to give the next block a way to know whether or not it needs to provide us
	 * its name/"label" for the purposes of matching up live on exit/entry regs */
	protected Boolean mayFallThrough = false;
	
	public BasicBlock(String label) {
		this.blockName = label;
		contents = new ArrayList<LLVMLine>();
		liveOnEntry = new ArrayList<String>();
		liveOnExit = new ArrayList<String>();
		targetBlocks = new ArrayList<String>();
		conflicts = new ArrayList<Conflict>();
	}
	
	public void addTargetBlock(String targetBlock) {
		if(!targetBlocks.contains(targetBlock))
			this.targetBlocks.add(targetBlock);
	}
	
	public void addLine(LLVMLine line) {
		contents.add(line);
	}
	
	@Override
	public String toString() {
		String returnValue = "";
		
		returnValue += "Block Name: " + blockName + "\n";
		returnValue += "Live on entry: " + liveOnEntry.toString() + "\n";
		returnValue += "Live on exit: " + liveOnExit.toString() + "\n";
		returnValue += "Target blocks: " + targetBlocks.toString() + "\n";
		returnValue += "Conflicts: " + conflicts.toString() + "\n";
		returnValue += "Pseudo-SPARC contained:\n";
		for(LLVMLine currentLine : contents)
			returnValue += currentLine.getSPARCTranslation() + "\n";
		returnValue += "---------------";
		
		return returnValue;
	}
}