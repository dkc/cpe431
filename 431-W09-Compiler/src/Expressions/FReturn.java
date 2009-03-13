package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class FReturn extends AbstractCodeAndReg {
	CodeAndReg target;
	
	
	public FReturn(CodeAndReg target,int regnum){
		super(regnum);
		this.target = target;
	}
	
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		this.code.addAll(target.compile(env, funcdecs, fieldTable).getCode());
		
		currentLine = new LLVMLine("ret i32 " + target.getReg() + "\n");
		currentLine.setOperation("ret");
		currentLine.addRegisterUsed(target.getReg());
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, 11\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(0);
		currentLine.addConstantUsed(11);
		this.code.add(currentLine);
		//throw new ReturnException();	
		return this;
	}


	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		target.staticPass(env, funcids, stringdecs);
	}

}
