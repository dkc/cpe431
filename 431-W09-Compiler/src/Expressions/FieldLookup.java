package Expressions;


import Environment.Env;
import Environment.RegAndIndex;
import Expressions.Objects.PObjectExp;
import Values.*;

public class FieldLookup extends AbstractCodeAndReg {
	String name;
	CodeAndReg obj;

	
	public FieldLookup(String name, CodeAndReg obj,int regnum){
		super(regnum);
		this.name = name;
		this.obj = obj;
	}
	
	public void staticPass(Env env){
		/*if(!(val instanceof VObject)){
			System.err.println("Looking for fields... on something that's NOT an object? You've gone too far this time. Exiting");
			System.exit(1);
			return null;
		}*/
	}
	
	@Override
	public CodeAndReg compile(Env env) {
		this.code.addAll(obj.compile(env).getCode());
		
			//TODO update obj slots ref
			RegAndIndex regind = Env.lookup(name,((PObjectExp)obj).slots);
			if(regind == null){
				System.err.println("Lookup on a nonexistant field--exiting");
				System.exit(1);
				return null;
			}else{
				
				return this;
			}
	}

}
