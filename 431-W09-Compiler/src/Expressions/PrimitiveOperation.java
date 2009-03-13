package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;

import Environment.FuncIDandParams;

public class PrimitiveOperation extends AbstractCodeAndReg {
	private String op;
	private ArrayList<CodeAndReg> args;
	
	private String objreg = "%objreg";
	private String idptr = "%idptr";
	private String slotsptr = "%slotsptr";
	private String castreg = "%castreg";
	private String shftreg = "%shftreg";
	private String constptr = "%constptr";
	private String stringptr = "%stringptr";
	private String strptr = "%strptr";
	
	public PrimitiveOperation(String operation, ArrayList<CodeAndReg> arguments, int regnum) {
		super(regnum);
		this.op = operation;
		this.args = arguments;
		
		this.objreg += regnum;
		this.idptr += regnum;
		this.slotsptr += regnum;
		this.castreg += regnum;
		this.shftreg += regnum;
		this.constptr += regnum;
		this.stringptr += regnum;
		this.strptr += regnum;
	}

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs,
			Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		if(this.op.equals("subString")){
			//call string_sub
			if(args.size() != 3){
				System.err.println("Runtime Error: wrong number of args to substring");
				System.exit(-1);
			}
			this.code.addAll(this.args.get(0).compile(env, funcdecs, fieldTable).getCode());
			this.code.addAll(this.args.get(1).compile(env, funcdecs, fieldTable).getCode());
			this.code.addAll(this.args.get(2).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.strptr + " = call i8* @string_sub( i32 " 
					+ this.args.get(0).getReg() +  ", i32 " + this.args.get(1).getReg() + 
					", i32 " + this.args.get(2).getReg() + " )\n");
			this.code.add(currentLine);		
			
			currentLine = new LLVMLine(this.objreg + " = malloc %sobj\n");
			currentLine.setOperation("malloc");
			currentLine.setRegisterDefined(this.objreg);
			this.code.add(currentLine);
			
			//store id
			currentLine = new LLVMLine(this.idptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.idptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 2, i32* " + this.idptr + "\n");//tag 2 for string
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.idptr);
			this.code.add(currentLine);
			
			//store empty slots
			currentLine = new LLVMLine(this.slotsptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.slotsptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store %slots* @empty_slots, %slots** " + this.slotsptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.slotsptr);
			this.code.add(currentLine);
			
			//store string to obj
			currentLine = new LLVMLine(this.stringptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 2\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.stringptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i8* " + this.strptr + ", i8** " + this.stringptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.strptr);
			currentLine.addRegisterUsed(this.stringptr);
			this.code.add(currentLine);
			
			//store sobj to env
			currentLine = new LLVMLine(this.castreg + " = ptrtoint %sobj* " + this.objreg + " to i32\n");
			currentLine.setOperation("ptrtoint");
			currentLine.setRegisterDefined(this.castreg);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.castreg + ", 2\n");
			currentLine.setOperation("shl");
			currentLine.setRegisterDefined(this.shftreg);
			currentLine.addRegisterUsed(this.castreg);
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.shftreg);
			currentLine.addConstantUsed(1);
			this.code.add(currentLine);
		}else if(this.op.equals("stringEqual?")){
			//call string_eq
			if(args.size() != 2){
				System.err.println("Runtime Error: wrong number of args to string=?");
				System.exit(-1);
			}
			this.code.addAll(this.args.get(0).compile(env, funcdecs, fieldTable).getCode());
			this.code.addAll(this.args.get(1).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.reg + " = call i32 @string_eq( i32 " 
					+ this.args.get(0).getReg() +  ", i32 " + this.args.get(1).getReg() + " )\n");
			this.code.add(currentLine);
		}else if(this.op.equals("stringLessThan?")){
			//call string_comp
			if(args.size() != 2){
				System.err.println("Runtime Error: wrong number of args to string<?");
				System.exit(-1);
			}
			this.code.addAll(this.args.get(0).compile(env, funcdecs, fieldTable).getCode());
			this.code.addAll(this.args.get(1).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.reg + " = call i32 @string_comp( i32 " 
					+ this.args.get(0).getReg() +  ", i32 " + this.args.get(1).getReg() + " )\n");
			this.code.add(currentLine);
		}else if(this.op.equals("readLine")){
			if(args.size() != 0){
				System.err.println("Runtime Error: wrong number of args to read-line");
				System.exit(-1);
			}
			currentLine = new LLVMLine(this.strptr + " = call i8* @read_line()\n");
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.objreg + " = malloc %sobj\n");
			currentLine.setOperation("malloc");
			currentLine.setRegisterDefined(this.objreg);
			this.code.add(currentLine);
			
			//store id
			currentLine = new LLVMLine(this.idptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.idptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 2, i32* " + this.idptr + "\n");//tag 2 for string
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.idptr);
			this.code.add(currentLine);
			
			//store empty slots
			currentLine = new LLVMLine(this.slotsptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.slotsptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store %slots* @empty_slots, %slots** " + this.slotsptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.slotsptr);
			this.code.add(currentLine);
			
			//store string to obj
			currentLine = new LLVMLine(this.stringptr + " = getelementptr %sobj* " + this.objreg + 
					", i32 0, i32 2\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.stringptr);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i8* " + this.strptr + ", i8** " + this.stringptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.strptr);
			currentLine.addRegisterUsed(this.stringptr);
			this.code.add(currentLine);
			
			//store sobj to env
			currentLine = new LLVMLine(this.castreg + " = ptrtoint %sobj* " + this.objreg + " to i32\n");
			currentLine.setOperation("ptrtoint");
			currentLine.setRegisterDefined(this.castreg);
			currentLine.addRegisterUsed(this.objreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.castreg + ", 2\n");
			currentLine.setOperation("shl");
			currentLine.setRegisterDefined(this.shftreg);
			currentLine.addRegisterUsed(this.castreg);
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.shftreg);
			currentLine.addConstantUsed(1);
			this.code.add(currentLine);
		}else if(this.op.equals("instanceof")){
			if(args.size() != 2){
				System.err.println("Runtime Error: wrong number of args to instanceof");
				System.exit(-1);
			}
			this.code.addAll(this.args.get(0).compile(env, funcdecs, fieldTable).getCode());
			this.code.addAll(this.args.get(1).compile(env, funcdecs, fieldTable).getCode());

			currentLine = new LLVMLine(this.reg + " = call i32 @instance_of( i32 " 
					+ this.args.get(0).getReg() +  ", i32 " + this.args.get(1).getReg() + " )\n");
			this.code.add(currentLine);
		}
		return this;
	}

	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids,
			ArrayList<String> stringdecs) {
		// TODO Auto-generated method stub
		super.staticPass(env, funcids, stringdecs);
	}

	
}
