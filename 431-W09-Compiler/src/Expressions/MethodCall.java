package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;
import Values.*;
import java.util.Hashtable;
public class MethodCall extends AbstractCodeAndReg {
	CodeAndReg funclookup;
	ArrayList<CodeAndReg> args;
	
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	private String shftreg = "%shftreg";
	private String castreg = "%castreg";
	private String slotsptrreg = "%slotptrsreg";
	private String slotsreg = "%slotsreg";
	private String lookupreg = "%lookupreg";
	
	private String typereg = "%typereg";
	//private String cptrreg = "%cptrreg";
	private String cshftreg = "%cshftreg";
	private String argsreg = "%argsreg";
	private String argptr = "%argptr";
	private String argslistptr = "%alist";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	private String thisptr = "%thisptr";
	private String idslotsptrreg = "%idslots";
	private String objid = "%objid";
	private String cobjid = "%cobjid";
	private String cidptr = "%cidptr";
	
	public MethodCall(FieldLookup funclookup, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.funclookup = funclookup;
		this.args = args;
		
		this.ptrreg += regnum;
		this.objreg += regnum;
		this.shftreg += regnum;
		this.castreg += regnum;
		this.slotsptrreg += regnum;
		this.slotsreg += regnum;
		this.lookupreg += regnum;
		
		this.thisptr += regnum;
		
		this.idslotsptrreg += regnum;
		this.objid += regnum;
		
		//this.cptrreg += regnum;
		this.typereg += regnum;
		this.cshftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "p";
		this.cobjreg += regnum;
		this.numargs += regnum;
		
		this.cobjid += regnum;
		this.cidptr += regnum;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//get obj pointer
		this.code.addAll(this.funclookup.compile(env, funcdecs, fieldTable).getCode());
		
		//typecheck ptr
		currentLine = new LLVMLine("call void @type_check( i32 " + this.funclookup.getReg() + ", i32 1 )\n");
		currentLine.setOperation("call");
		currentLine.setLabel("type_check");
		currentLine.addRegisterUsed(this.funclookup.getReg());
		this.code.add(currentLine);
		
		//shift
		currentLine = new LLVMLine(this.cshftreg + " = lshr i32 " + this.typereg + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.cshftreg);
		currentLine.addRegisterUsed(this.typereg);
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.cshftreg + 
				" to %cobj*\n");
		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.cobjreg);
		currentLine.addRegisterUsed(this.cshftreg);
		this.code.add(currentLine);
		
		//type check for func close
		currentLine = new LLVMLine(this.cidptr + " = getelementptr %cobj* " + this.cobjreg + 
		", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.cidptr);
		currentLine.addRegisterUsed(this.cobjreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.cobjid + " = load i32* " + this.cidptr + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.cobjid);
		currentLine.addRegisterUsed(this.cidptr);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("call void @obj_type_check( i32 " + this.cobjid + ", i32 1)\n");//1 is cobj type
		currentLine.setOperation("call");
		currentLine.setLabel("obj_type_check");
		currentLine.addRegisterUsed(this.cobjid);
		currentLine.addConstantUsed(1);
		this.code.add(currentLine);
		
		//evaluate args
		currentLine = new LLVMLine(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.argptr);
		currentLine.addConstantUsed(4*(args.size()+1));
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.argsreg + " = bitcast [" + args.size() + " x i32]* " + 
				this.argptr + " to i32*\n");
		currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(this.argsreg);
		currentLine.addRegisterUsed(this.argptr);
		currentLine.addConstantUsed(4*(args.size()+1));
		this.code.add(currentLine);
		
		for(int i = 0;i < args.size();i++){
			this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.argslistptr + i + " = getelementptr i32* " + 
					this.argsreg + ", i32 " + i + "\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.argslistptr + i);
			currentLine.addRegisterUsed(this.argsreg);
			currentLine.addConstantUsed(4*i);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 " + args.get(i).getReg() + ", i32* " + 
					this.argslistptr + i + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.args.get(i).getReg());
			currentLine.addRegisterUsed(this.argslistptr + i);
			this.code.add(currentLine);
		}
		
		//add this reference
		/* = new LLVMLine(this.thisptr + " = getelementptr i32* " + this.argsreg + 
				", i32 " + args.size() + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.thisptr);
		currentLine.addRegisterUsed(this.argsreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + this.objreg + ", i32* " + this.thisptr + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.objreg);
		currentLine.addRegisterUsed(this.thisptr);
		this.code.add(currentLine);*/
		
		//TODO add 1 to func id?
		//send compiled args and closure id # to dispatch
		//currentLine = new LLVMLine(this.numargs + " = add i32 0, " + args.size() + "\n");
		currentLine = new LLVMLine(this.reg + " = call i32 @dispatch_fun( %cobj* " + 
				this.cobjreg + ", i32 " + args.size() + ", i32* " + this.argsreg + ", i32 " + this.objreg + " ) nounwind\n");
		currentLine.setOperation("call");
		currentLine.setLabel("dispatch_fun");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.cobjreg);
		currentLine.addConstantUsed(4*args.size());
		currentLine.addRegisterUsed(this.argsreg);
		this.code.add(currentLine);
		
		return this;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs) {
		this.funclookup.staticPass(env, funcids, stringdecs);
		//call staticPass on args?
		for(CodeAndReg arg: args){
			arg.staticPass(env, funcids, stringdecs);
		}
	}
}