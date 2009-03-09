package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.Const.FVoid;
import LLVMObjects.LLVMLine;

public class Application extends AbstractCodeAndReg{
	CodeAndReg func;
	ArrayList<CodeAndReg> args;
	private String typereg = "%typereg";
	private String ptrreg = "%ptrreg";
	private String shftreg = "%shftreg";
	private String argsreg = "%argsreg";
	private String argptr = "%argptr";
	private String argslistptr = "%alist";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	
	private String idslotsptrreg = "%idslots";
	private String objid = "%objid";
	
	public Application(CodeAndReg func, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.func = func;
		this.args = args;
		this.ptrreg += regnum;
		this.typereg += regnum;
		this.shftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "p";
		this.cobjreg += regnum;
		this.numargs += regnum;
		this.idslotsptrreg += regnum;
		this.objid += regnum;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs) {
		this.func.staticPass(env, funcids, stringdecs);
		//call staticPass on args?
		for(CodeAndReg arg: args){
			arg.staticPass(env, funcids, stringdecs);
		}
	}
	
	/* all of the PrimitiveFunction business has been PULLED thanks to the magic of the parser/tree parser--
	 * UnaryOperation and BinaryOperation ate the bulk of this functionality and the other built-ins (readline,
	 * string=?, string<?) are "predefined" applications that should always be included and can be called through
	 * here */
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
			LLVMLine currentLine;
		
			//Lookup closure by name in env
			this.code.addAll(func.compile(env, funcdecs, fieldTable).getCode());
			
			//get fun id
			//type check for ptr
			currentLine = new LLVMLine("call void @type_check( i32 " + this.func.getReg() + ", i32 1)\n");//1 is cobj type
			currentLine.setOperation("call");
			currentLine.addRegisterUsed(this.func.getReg());
			this.code.add(currentLine);
			
			//shift
			currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.func.getReg() + ", 2\n");
			currentLine.setOperation("lshr");
			currentLine.setRegisterDefined(this.shftreg);
			currentLine.addRegisterUsed(this.func.getReg());
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.shftreg + 
					" to %cobj*\n");
			currentLine.setOperation("inttoptr");
			currentLine.setRegisterDefined(this.cobjreg);
			currentLine.addRegisterUsed(this.shftreg);
			this.code.add(currentLine);
			
			//type check cobj
			currentLine = new LLVMLine(this.idslotsptrreg + " = getelementptr %cobj* " + this.cobjreg + ", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.idslotsptrreg);
			currentLine.addRegisterUsed(this.cobjreg);
			currentLine.addConstantUsed(4*0);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.objid + " = load i32* " + this.idslotsptrreg + "\n");
 			currentLine.setOperation("load");
			currentLine.setRegisterDefined(this.objid);
			currentLine.addRegisterUsed(this.idslotsptrreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("call void @obj_type_check( i32 " + this.objid + ", i32 1 )\n");
			currentLine.setOperation("call");
			currentLine.addRegisterUsed(this.objid);
			currentLine.setLabel("@obj_type_check");
			this.code.add(currentLine);
			
			//evaluate args
			currentLine = new LLVMLine(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
			currentLine.setOperation("malloc");
			currentLine.setRegisterDefined(this.argptr);
			currentLine.addConstantUsed(4 * args.size());
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
				currentLine.setRegisterDefined(this.argslistptr + i);
				currentLine.addRegisterUsed(this.argsreg);
				currentLine.addConstantUsed(4 * i);
				this.code.add(currentLine);
				
				currentLine = new LLVMLine("store i32 " + args.get(i).getReg() + ", i32* " + this.argslistptr + i + "\n");
				currentLine.setOperation("store");
				currentLine.addRegisterUsed(args.get(i).getReg());
				currentLine.addRegisterUsed(this.argslistptr + i);
				this.code.add(currentLine);
			}
			
			//send compiled args and closure id # to dispatch
			
			currentLine = new LLVMLine(this.reg + " = call i32 @dispatch_fun( %cobj* " + this.cobjreg + ", i32 " + this.args.size() + ", i32* " + this.argsreg + ", i32 0 ) nounwind\n");
			currentLine.setOperation("call");
			currentLine.setLabel("@dispatch_fun");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.cobjreg);
			currentLine.addRegisterUsed(this.numargs);
			currentLine.addRegisterUsed(this.argsreg);
			currentLine.addConstantUsed(this.args.size());
			currentLine.addConstantUsed(0);
			this.code.add(currentLine);
			
			return this;
	}
}
