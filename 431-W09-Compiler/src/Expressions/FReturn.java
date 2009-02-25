package Expressions;


import Environment.Env;
import LLVMObjects.LLVMLine;

public class FReturn extends AbstractCodeAndReg {
	CodeAndReg target;
	
	
	public FReturn(CodeAndReg target,int regnum){
		super(regnum);
		this.target = target;
	}
	
	@Override
	public CodeAndReg compile(Env env){
		LLVMLine currentLine;
		
		this.code.addAll(target.compile(env).getCode());
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + target.getReg() + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(target.getReg());
			
		return this;
	}

}
