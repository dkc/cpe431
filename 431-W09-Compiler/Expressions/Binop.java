package Expressions;


import Environment.Env;


public class Binop extends AbstractCodeAndReg{
	
	public CodeAndReg left;
	public String exp;
	public CodeAndReg right;
	public String type;
	private String lshftreg,rshftreg,ashftreg,aboolreg;
	
	public  Binop(CodeAndReg left, String op, CodeAndReg right,int regnum){
		super(regnum);
		this.left = left;
		this.exp = op;
		this.right = right;
		this.lshftreg = "%lrshft" + regnum;
		this.rshftreg = "%rrshft" + regnum;
		this.ashftreg = "%shftans" + regnum;
		this.aboolreg = "%boolreg" + regnum;
	}
	
	public CodeAndReg compile(Env env){
		left.compile(env);
		right.compile(env);
		this.code.addAll(left.getCode());
		this.code.addAll(right.getCode());
		
		//type check
		
		
		//bit shift right
		this.code.add(this.lshftreg + " = lshr i32 " + left.getReg() + ", 2\n");
		this.code.add(this.rshftreg + " = lshr i32 " + right.getReg() + ", 2\n");
		
		if(exp.equals("+")){
			this.type = "INTEGER";
			this.code.add(ashftreg + " = add i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("-")){
			this.type = "INTEGER";
			this.code.add(ashftreg + " = sub i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("*")){
			this.type = "INTEGER";
			this.code.add(ashftreg + " = mul i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("/")){
			this.type = "INTEGER";
			this.code.add(ashftreg + " = udiv i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("and")){
			this.type = "BOOLEAN";
			this.code.add(ashftreg + " = and i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("or")){
			this.type = "BOOLEAN";
			this.code.add(ashftreg + " = or i32 " 
			+ lshftreg + ", " + rshftreg + "\n");
		}else if(exp.equals("not")){
			this.type = "BOOLEAN";
			this.code.add(ashftreg + " = xor i32 " 
			+ lshftreg + ", 1\n");
		}else if(exp.equals("==")){
			this.type = "BOOLEAN";
			this.code.add(this.aboolreg + " = icmp eq i32 " 
					+ lshftreg + ", " + rshftreg + "\n");
			this.code.add(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
		}else if(exp.equals(">")){
			this.type = "BOOLEAN";
			this.code.add(this.aboolreg + " = icmp ugt i32 " + left.getReg() + ", " + right.getReg() + "\n");
			this.code.add(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
		}else if(exp.equals(">=")){
			this.type = "BOOLEAN";
			this.code.add(this.aboolreg + " = icmp uge i32 " + left.getReg() + ", " + right.getReg() + "\n");
			this.code.add(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
		}else if(exp.equals("<")){
			this.type = "BOOLEAN";
			this.code.add(this.aboolreg + " = icmp ult i32 " + left.getReg() + ", " + right.getReg() + "\n");
			this.code.add(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
		}else if(exp.equals("<=")){
			this.type = "BOOLEAN";
			this.code.add(this.aboolreg + " = icmp ule i32 " + left.getReg() + ", " + right.getReg() + "\n");
			this.code.add(this.ashftreg + " = zext i1 " + this.aboolreg + " to i32\n");
		}
		
		//bit shift answer left
		this.code.add(this.reg + " = shl i32 " + ashftreg + ", 2\n");
		
		//add tag
		if(this.type == "BOOLEAN"){//add 2 for the tag
			this.code.add(this.reg + " = add i32 , 2" + this.getReg() + "\n");
		}
		
		return this;
	}
}
