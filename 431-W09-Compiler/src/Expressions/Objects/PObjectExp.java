package Expressions.Objects;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;

//public class PObjectExp implements FObjectExp{
public class PObjectExp extends AbstractCodeAndReg{
	public Env slots;
	
	public PObjectExp(Env slots, int regnum){
		super(regnum);
		this.slots = slots;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		return this;
	}
}
