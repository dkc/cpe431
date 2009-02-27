package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;
import Values.*;
import java.util.Hashtable;
public class MethodCall extends AbstractCodeAndReg {
	String obj;
	String mname;
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
	private String argslistptr = "%";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	
	
	
	public MethodCall(String obj, String mname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.obj = obj;
		this.mname = mname;
		this.args = args;
		
		this.ptrreg += regnum;
		this.objreg += regnum;
		this.shftreg += regnum;
		this.castreg += regnum;
		this.slotsptrreg += regnum;
		this.slotsreg += regnum;
		this.lookupreg += regnum;
		
		//this.cptrreg += regnum;
		this.typereg += regnum;
		this.cshftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "argslistptr";
		this.cobjreg += regnum;
		this.numargs += regnum;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//get obj pointer
		RegAndIndex regind = Env.lookup(obj, env);
		
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.objreg + " = load i32* " + this.ptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.objreg);
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.objreg + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		//TODO typecheck, but opposite here... as in !typecheck
		//this.code.add("call void @type_check( i32 " + ", i32 1 )\n");
		
		currentLine = new LLVMLine(this.castreg + " = inttoptr i32 " + this.shftreg + " to %pobj*\n");
		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.castreg);
		currentLine.addRegisterUsed(this.shftreg);
		this.code.add(currentLine);
		
		//update obj slots ref
		
		currentLine = new LLVMLine(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + ", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.slotsptrreg);
		currentLine.addRegisterUsed(this.castreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.slotsreg);
		currentLine.addRegisterUsed(this.slotsptrreg);
		this.code.add(currentLine);
		
		//get object type and check
		int fid = fieldTable.get(obj + mname);
		
		currentLine = new LLVMLine(this.lookupreg + " = call i32* @lookup_field( i32 " + fid + ", %slots* " + this.slotsreg + "\n");
		currentLine.setOperation("call");
		currentLine.setRegisterDefined(this.lookupreg);
		currentLine.addRegisterUsed(this.slotsreg);
		this.code.add(currentLine);
		
		
		currentLine = new LLVMLine(this.typereg + " = load i32* " + this.lookupreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.typereg);
		currentLine.addRegisterUsed(this.lookupreg);
		this.code.add(currentLine);
		
		//Lookup closure by name in env
		//RegAndIndex regind = Env.lookup(this.mname, env);
		
		//get fun id
		//bitcast to cobj
		//this.code.add(this.cptrreg + " = getelementptr %eframe* " + 
			//	regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		//this.code.add(this.typereg + " = load i32* " + this.cptrreg + "\n");
		//type check for func close
		
		currentLine = new LLVMLine("call void @type_check( i32 " + this.typereg + ", i32 1)\n"); //1 is cobj type
		currentLine.setOperation("call");
		currentLine.addRegisterUsed(this.typereg);
		this.code.add(currentLine);
		
		//shift
		
		currentLine = new LLVMLine(this.cshftreg + " = lshr i32 " + this.typereg + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.cshftreg);
		currentLine.addRegisterUsed(this.typereg);
		this.code.add(currentLine);
		
		
		currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.cshftreg + " to %cobj*\n");
		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.cobjreg);
		currentLine.addRegisterUsed(this.cshftreg);
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