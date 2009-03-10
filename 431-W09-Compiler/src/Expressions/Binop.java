package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;


public class Binop extends AbstractCodeAndReg{
	
	public CodeAndReg left;
	public String exp;
	public CodeAndReg right;
	public String type;
	private String lshftreg,rshftreg,ashftreg,aboolreg,shftreg;
	
	public  Binop(CodeAndReg left, String op, CodeAndReg right,int regnum){
		super(regnum);
		this.left = left;
		this.exp = op;
		this.right = right;
		this.lshftreg = "%lrshft" + regnum;
		this.rshftreg = "%rrshft" + regnum;
		this.ashftreg = "%shftans" + regnum;
		this.aboolreg = "%boolreg" + regnum;
		this.shftreg = "%shftreg" + regnum;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		left.compile(env, funcdecs, fieldTable);
		right.compile(env, funcdecs, fieldTable);

		LLVMLine currentLine;
		
		this.code.addAll(left.getCode());
		this.code.addAll(right.getCode());
		
		//type check
		
		
		//bit shift right
		currentLine = new LLVMLine(this.lshftreg + " = lshr i32 " + left.getReg() + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.lshftreg);
		currentLine.addRegisterUsed(left.getReg());
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.rshftreg + " = lshr i32 " + right.getReg() + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.rshftreg);
		currentLine.addRegisterUsed(right.getReg());
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		if(exp.equals("+")){
			this.type = "INTEGER";
			
			currentLine = new LLVMLine(ashftreg + " = add i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(ashftreg);
			currentLine.addRegisterUsed(lshftreg);
			currentLine.addRegisterUsed(rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("-")){
			this.type = "INTEGER";
			
			currentLine = new LLVMLine(ashftreg + " = sub i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("sub");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("*")){
			this.type = "INTEGER";
			
			currentLine = new LLVMLine(ashftreg + " = mul i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("mul");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("/")){
			this.type = "INTEGER";
			
			currentLine = new LLVMLine(ashftreg + " = sdiv i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("sdiv");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("and")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(ashftreg + " = and i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("and");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("or")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(ashftreg + " = or i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("or");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("==")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(this.aboolreg + " = icmp eq i32 " + lshftreg + ", " + rshftreg + "\n");
			currentLine.setOperation("icmp eq");
			currentLine.setRegisterDefined(this.aboolreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
			currentLine.setOperation("zext");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.aboolreg);
			this.code.add(currentLine);
			
		}else if(exp.equals(">")){
			this.type = "BOOLEAN";

			currentLine = new LLVMLine(this.aboolreg + " = icmp sgt i32 " + this.lshftreg + ", " + this.rshftreg + "\n");
			currentLine.setOperation("icmp ugt");
			currentLine.setRegisterDefined(this.aboolreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
			currentLine.setOperation("zext");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.aboolreg);
			this.code.add(currentLine);
			
		}else if(exp.equals(">=")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(this.aboolreg + " = icmp sge i32 " + this.lshftreg + ", " + this.rshftreg + "\n");
			currentLine.setOperation("icmp sge");
			currentLine.setRegisterDefined(this.aboolreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
			currentLine.setOperation("zext");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.aboolreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("<")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(this.aboolreg + " = icmp slt i32 " + this.lshftreg + ", " + this.rshftreg + "\n");
			currentLine.setOperation("icmp slt");
			currentLine.setRegisterDefined(this.aboolreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
			currentLine.setOperation("zext");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.aboolreg);
			this.code.add(currentLine);
			
		}else if(exp.equals("<=")){
			this.type = "BOOLEAN";
			
			currentLine = new LLVMLine(this.aboolreg + " = icmp sle i32 " + this.lshftreg + ", " + this.rshftreg + "\n");
			currentLine.setOperation("icmp sle");
			currentLine.setRegisterDefined(this.aboolreg);
			currentLine.addRegisterUsed(this.lshftreg);
			currentLine.addRegisterUsed(this.rshftreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
			currentLine.setOperation("zext");
			currentLine.setRegisterDefined(this.ashftreg);
			currentLine.addRegisterUsed(this.aboolreg);
			this.code.add(currentLine);
			
		}
		
		//bit shift answer left
		currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.ashftreg + ", 2\n");
		currentLine.setOperation("shl");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.ashftreg);
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		//add tag
		if(this.type == "BOOLEAN"){//add 2 for the tag
			currentLine = new LLVMLine(this.reg + " = add i32 2, " + this.shftreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.shftreg);
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
		}else{
			currentLine = new LLVMLine(this.reg + " = add i32 0, " + this.shftreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.shftreg);
			currentLine.addConstantUsed(0);
			this.code.add(currentLine);
		}
		
		return this;
	}
}
