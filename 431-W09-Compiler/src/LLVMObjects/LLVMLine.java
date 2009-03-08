package LLVMObjects;

import java.util.ArrayList;

public class LLVMLine {
	private String code;
	private String SPARCTranslation;
	
	private String operation;
	private String registerDefined;
	private String targetBlock;
	
	private String label;
	protected ArrayList<String> registersUsed;
	private ArrayList<Number> constantsUsed;
	
	public LLVMLine(String code) {
		this.code = code;
		this.registersUsed = new ArrayList<String>();
		this.constantsUsed = new ArrayList<Number>();
	}
	
	public String getSPARCTranslation() {
		return SPARCTranslation;
	}

	public void setSPARCTranslation(String translation) {
		SPARCTranslation = translation;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void addConstantUsed(Number newRegisterUsed) {
		this.constantsUsed.add(newRegisterUsed);
	}

	/* strips the leading % sign and adds it to registersUsed */
	public void addRegisterUsed(String newRegisterUsed) {
		this.registersUsed.add(newRegisterUsed.replaceAll("%", ""));
	}

	public String getCode() {
		return code;
	}

	public String getRegisterDefined() {
		return registerDefined;
	}
	
	public void setRegisterDefined(String registerDefined) {
		this.registerDefined = registerDefined.replaceAll("%", "");
	}
	
	public int getNumConstants() {
		return this.constantsUsed.size();
	}
	
	public int getNumRegistersUsed() {
		return this.registersUsed.size();
	}
	
	public String getRegisterUsed(int regNum) {
		return this.registersUsed.get(regNum);
	}
	
	public Number getConstantUsed(int conNum) {
		return this.constantsUsed.get(conNum);
	}
	
	public int getConstantSum() {
		int sum = 0;
		for(Number n : constantsUsed)
			sum += n.intValue();
		
		return sum;
	}
	
	public String getTargetBlock() {
		return targetBlock;
	}

	public void setTargetBlock(String targetBlock) {
		this.targetBlock = targetBlock;
	}
	
	@Override
	public String toString() {
		String lineRepresentation = "----------------\n";
		lineRepresentation += code + "\n";
		lineRepresentation += operation + "\n";
		lineRepresentation += "defs:\t" + registerDefined + "\n";
		lineRepresentation += "uses:\t" + registersUsed.toString() + "\n";
		lineRepresentation += "consts:\t" + constantsUsed.toString();
		
		return lineRepresentation;
	}

	public void setLabel(String label) {
		this.label = label.replaceAll("%", "");
	}

	public String getLabel() {
		return label;
	}

	
}
