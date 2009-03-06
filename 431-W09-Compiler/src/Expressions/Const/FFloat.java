package Expressions.Const;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;


public class FFloat extends AbstractCodeAndReg{
	public float number;
	
	private String objreg = "%objreg";
	private String idptr = "%idptr";
	private String slotsptr = "%slotsptr";
	private String castreg = "%castreg";
	private String shftreg = "%shftreg";
	private String floatptr = "%floatptr";
	
	public FFloat(float number,int regnum){
		super(regnum);
		this.number = number;
		
		this.objreg += regnum;
		this.idptr += regnum;
		this.slotsptr += regnum;
		this.castreg += regnum;
		this.shftreg += regnum;
		this.floatptr += regnum;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){	
		LLVMLine currentLine;
		currentLine = new LLVMLine(this.objreg + " = malloc %fobj\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.objreg);
		this.code.add(currentLine);
		
		//store id
		currentLine = new LLVMLine(this.idptr + " = getelementptr %fobj* " + this.objreg + ", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.idptr);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 3, i32* " + this.idptr + "\n");//tag 3 for float
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.idptr);
		this.code.add(currentLine);
		
		//store float value
		currentLine = new LLVMLine(this.floatptr + " = getelementptr %fobj* " + this.objreg + 
				", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.floatptr);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store float " + this.number + ", float* " + this.floatptr + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.floatptr);
		this.code.add(currentLine);
		
		//store pobj to env
		currentLine = new LLVMLine(this.castreg + " = ptrtoint %fobj* " + this.objreg + " to i32\n");
		currentLine.setOperation("ptrtoint");
		currentLine.setRegisterDefined(this.castreg);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.castreg + ", 2\n");
		currentLine.setOperation("shl");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.castreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.shftreg);
		currentLine.addConstantUsed(1);
		this.code.add(currentLine);
		
		return this;
	}
}
