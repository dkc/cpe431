package CodeAndRegs;


import Environment.Env;

import Values.*;


public class Binop extends AbstractCodeAndReg{
	
	public CodeAndReg left;
	public String op;
	public CodeAndReg right;
	public String type;
	
	public  Binop(CodeAndReg left, String op, CodeAndReg right,int regnum){
		super(regnum);
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	public CodeAndReg compile(){
		left.compile();
		right.compile();
		this.code = left.code + right.code;
		
		//type check
		
		
		//bit shift right
		this.code = this.code + "%lrshft" + regnum + " = " + left.reg + "\n"
		"%rrshft" + regnum + " = " + right.reg + "\n";
		if(exp.equals("+")){
			this.type = "INTEGER";
			this.code = this.code + "%shftans" + regnum + " = add i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("-")){
			this.type = "INTEGER";
			this.code = this.code + "%shftans" + regnum + " = sub i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("*")){
			this.type = "INTEGER";
			this.code = this.code + "%shftans" + regnum + " = mul i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("/")){
			this.type = "INTEGER";
			this.code = this.code + "%shftans" + regnum + " = udiv i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("and")){
			this.type = "BOOLEAN";
			this.code = this.code + "%shftans" + regnum + " = and i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("or")){
			this.type = "BOOLEAN";
			this.code = this.code + "%shftans" + regnum + " = or i32 %lrshft" 
			+ regnum + ", %rrshft" + regnum + "\n";
		}else if(exp.equals("not")){
			this.type = "BOOLEAN";
			this.code = this.code + "%shftans" + regnum + " = xor i32 %lrshft" 
			+ regnum + ", 1\n";
		}else if(exp.equals("==")){
			
		}else if(exp.equals(">")){
			
		}else if(exp.equals(">=")){
			
		}else if(exp.equals("<")){
			
		}else if(exp.equals("<=")){
			
		}
		
		//bit shift answer left
		this.code = this.code + this.reg + " = $shftans" + regnum + "\n";
		
		//add tag
		if(this.type == "BOOLEAN"){//add 2 for the tag
			this.code = this.code + this.reg + " = add i32 , 2" + this.reg + "\n";
		}
		
		return this;
	}
}
