package Expressions.Objects;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.CodeAndReg;
import Values.*;

public class CObjectExp extends PObjectExp{
	VClosure clos;
	
	public CObjectExp(VClosure clos,Env slots,int regnum){
		super(slots,regnum);
		this.clos = clos;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		return this;
	}
}
