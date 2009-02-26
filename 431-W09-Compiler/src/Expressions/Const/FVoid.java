package Expressions.Const;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;

public class FVoid extends AbstractCodeAndReg{
	public FVoid(int regnum){
		super(regnum);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		//void is 2 + 10 tag bits
		this.code.add(this.reg + " = add i32 0, " + 10 + "\n");
		return this;
	}
}
