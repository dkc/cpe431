package LLVMObjects;

import java.util.ArrayList;

public class BasicBlock {
	
	private String blockName;
	private ArrayList<LLVMLine> contents;
	
	private ArrayList<String> liveOnEntry;
	private ArrayList<String> liveOnExit;
	private ArrayList<String> targetBlocks;
	
	public BasicBlock(String label) {
		this.blockName = label;
		contents = new ArrayList<LLVMLine>();
		liveOnEntry = new ArrayList<String>();
		liveOnExit = new ArrayList<String>();
		targetBlocks = new ArrayList<String>();
	}
	
	public void addTargetBlock(String targetBlock) {
		if(!targetBlocks.contains(targetBlock))
			this.targetBlocks.add(targetBlock);
	}
	
	public void addLine(LLVMLine line) {
		contents.add(line);
	}
	
	public String toString() {
		String returnValue = "";
		
		returnValue += "Block Name: " + blockName + "\n";
		returnValue += "Live on entry: " + liveOnEntry.toString() + "\n";
		returnValue += "Live on exit: " + liveOnExit.toString() + "\n";
		returnValue += "Target blocks: " + targetBlocks.toString() + "\n";
		returnValue += "Pseudo-SPARC contained:\n";
		for(LLVMLine currentLine : contents)
			returnValue += currentLine.getSPARCTranslation() + "\n";
		returnValue += "---------------";
		
		return returnValue;
	}
}
