package Expressions;


import Environment.Env;

import Values.*;

/* note: string<? and string=? had to be pushed out of here for the compiler--they're called with Application */
public class BinaryOperation extends AbstractCodeAndReg{
	
	public CodeAndReg left;
	public String op;
	public CodeAndReg right;
	
	public  BinaryOperation(CodeAndReg left, String op, CodeAndReg right, int regnum){
		super(regnum);
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	public CodeAndReg compile(Env env){
		
		/* assuming this dunks the results of these two pieces of code into the register they provide for getReg() */
		this.code.addAll(left.compile(env).getCode());
		this.code.addAll(right.compile(env).getCode());
		
		/**********/
		/* PLACEHOLDER obviously, this needs to be done per operation with real instructions */
		this.code.add("BINARYOPERATION PLACEHOLDER: " + this.getReg() + " gets the result of "+ left.getReg() + " " + op + " " + right.getReg() + "\n");
		/**********/
		
		/* fetch the reg of left and right in and execute the operation on left and right */
		
		if(op.equals("+")){
		}else if(op.equals("-")){
		}else if(op.equals("*")){
		}else if(op.equals("/")){
		}else if(op.equals(">")){
		}else if(op.equals("<")){
		}else if(op.equals(">=")){
		}else if(op.equals("<=")){
		}else if(op.equals("and")){
		}else if(op.equals("or")){
		}else if(op.equals("==")){
		}

		return this;
	}
}
