package Expressions;

import Environment.Env;
import Expressions.CodeAndReg;
import Values.PObject;
import Values.ReturnContainer;
import Values.VObject;
import Values.Value;

public class FieldMut extends AbstractCodeAndReg {
	String name;
	CodeAndReg newval;
	CodeAndReg obj;
	
	public FieldMut(String name, CodeAndReg newval, CodeAndReg obj, int regnum){
		super(regnum);
		this.name = name;
		this.newval = newval;
		this.obj = obj;
	}
	
	@Override
	public CodeAndReg compile(Env env) {
		return null;
	}
}
