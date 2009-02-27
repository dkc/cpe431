package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.Const.FVoid;
import LLVMObjects.LLVMLine;

public class Application extends AbstractCodeAndReg{
	String fname;
	ArrayList<CodeAndReg> args;
	private String typereg = "%typereg";
	private String ptrreg = "%ptrreg";
	private String shftreg = "%shftreg";
	private String argsreg = "%argsreg";
	private String argptr = "%argptr";
	private String argslistptr = "%";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	
	public Application(String fname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.fname = fname;
		this.args = args;
		this.ptrreg += regnum;
		this.typereg += regnum;
		this.shftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "argslistptr";
		this.cobjreg += regnum;
		this.numargs += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		//check # args against # params?
	}
	
	/* all of the PrimitiveFunction business has been PULLED thanks to the magic of the parser/tree parser--
	 * UnaryOperation and BinaryOperation ate the bulk of this functionality and the other built-ins (readline,
	 * string=?, string<?) are "predefined" applications that should always be included and can be called through
	 * here */
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
			//Lookup closure by name in env
		LLVMLine currentLine;
		RegAndIndex regind = Env.lookup(this.fname, env);
		
		//get fun id
		//TODO bitcast to cobj
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.typereg + " = load i32* " + this.ptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.typereg);
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		//type check for func close
		//1 is cobj type
		currentLine = new LLVMLine("call void @type_check( i32 " + this.typereg + ", i32 1)\n");
		currentLine.setOperation("call");
		currentLine.addRegisterUsed(this.typereg);
		this.code.add(currentLine);
		
		
		//shift
		currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.typereg + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.typereg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.shftreg + " to %cobj*\n");
		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.cobjreg);
		currentLine.addRegisterUsed(this.shftreg);
		this.code.add(currentLine);
		
		//evaluate args
		currentLine = new LLVMLine(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.argptr);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.argsreg + " = bitcast [" + args.size() + " x i32]* " + this.argptr + " to i32*\n");
		currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(this.argsreg);
		currentLine.addRegisterUsed(this.argptr);
		this.code.add(currentLine);
		
		for(int i = 0;i < args.size();i++){
			this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
			
			currentLine = new LLVMLine(this.argslistptr + i + " = getelementptr i32* " + this.argsreg + ", i32 " + i + "\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.argslistptr);
			currentLine.addRegisterUsed(this.argsreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 " + args.get(i).getReg() + ", i32* " + this.argslistptr + i + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(args.get(i).getReg());
			currentLine.addRegisterUsed(this.argslistptr);
			this.code.add(currentLine);
		}
		
		//TODO Dipatch function nounwind needed?
		//send compiled args and closure id # to dispatch
		currentLine = new LLVMLine(this.numargs + " = add i32 0, " + args.size() + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.numargs);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = call i32 @dispatch_fun( %cobj* " + this.cobjreg + ", i32 " + this.numargs + ", i32* " + this.argsreg + " ) nounwind\n");
		currentLine.setOperation("call");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.cobjreg);
		currentLine.addRegisterUsed(this.argsreg);
		this.code.add(currentLine);
		
		//TODO Tricky, does it work?
		//this.code.addAll(new FVoid(this.regnum).compile(env, funcdecs, fieldTable).getCode());
		return this; /* function did not return a value */
	}
}
