package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Values.*;

public class WhileExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg body;
	
	public WhileExp(CodeAndReg test, CodeAndReg body, int regnum){
		super(regnum);
		this.test = test;
		this.body = body;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		return null;
	}
}
