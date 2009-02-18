package Expressions;

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
	
	public CodeAndReg compile(Env env) {
		return null;
	}
}
