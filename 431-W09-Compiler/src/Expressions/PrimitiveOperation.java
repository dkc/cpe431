package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;

public class PrimitiveOperation extends AbstractCodeAndReg {

	public PrimitiveOperation(String operation, ArrayList<CodeAndReg> arguments, int regnum) {
		super(regnum);
	}

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs,
			Hashtable<String, Integer> fieldTable) {
		// TODO Auto-generated method stub
		return super.compile(env, funcdecs, fieldTable);
	}

	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids,
			ArrayList<String> stringdecs) {
		// TODO Auto-generated method stub
		super.staticPass(env, funcids, stringdecs);
	}

	
}
