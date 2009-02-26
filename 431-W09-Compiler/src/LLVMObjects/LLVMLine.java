package LLVMObjects;

import java.util.ArrayList;

public class LLVMLine {
	private String code;
	private String operation;
	private String registerDefined;
	private ArrayList<String> registersUsed;
	
	public LLVMLine(String code)
	{
		this.code = code;
		this.registerDefined = registerDefined;
		this.registersUsed = new ArrayList<String>();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public ArrayList<String> getRegistersUsed() {
		return registersUsed;
	}

	public void addRegisterUsed(String newRegisterUsed) {
		this.registersUsed.add(newRegisterUsed);
	}

	public String getCode() {
		return code;
	}

	public String getRegisterDefined() {
		return registerDefined;
	}
	
	public void setRegisterDefined(String registerDefined) {
		this.registerDefined = registerDefined;
	}
}
