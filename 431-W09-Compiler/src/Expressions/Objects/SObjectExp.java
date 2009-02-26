package Expressions.Objects;

import java.util.ArrayList;

import Environment.Env;
import Expressions.CodeAndReg;
import java.util.Hashtable;

public class SObjectExp extends PObjectExp{
	String strval;
	
	public SObjectExp(String strval, Env slots,int regnum){
		super(slots,regnum);
		this.strval = strval;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		return this;
	}
}
