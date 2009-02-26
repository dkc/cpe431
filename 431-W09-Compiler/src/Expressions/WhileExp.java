package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Values.*;

public class WhileExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg body;
	
	public WhileExp(CodeAndReg test, CodeAndReg body, int regnum){
		super(regnum);
		this.test = test;
		this.body = body;
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {
		return null;
	}
}
