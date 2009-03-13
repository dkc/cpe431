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
			
		return this;
	}


	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		target.staticPass(env, funcids, stringdecs);
	}

}
