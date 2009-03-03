package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;
import LLVMObjects.LLVMLine;

public class FReturn extends AbstractCodeAndReg {
	CodeAndReg target;
	
	
	public FReturn(CodeAndReg target,int regnum){
		super(regnum);
		this.target = target;
	}
	
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		this.code.addAll(target.compile(env, funcdecs, fieldTable).getCode());
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + target.getReg() + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(target.getReg());
			
		return this;
	}


	@Override
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs) {
		// TODO Auto-generated method stub
		super.staticPass(env, funcid, stringdecs);
	}

}
