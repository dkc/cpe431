package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;
import Values.*;

public class NewObj extends AbstractCodeAndReg{
	String fname;
	ArrayList<CodeAndReg> args;
	
	private String objreg = "%objreg";
	private String idptr = "%idptr";
	private String slotsptr = "%slotsptr";
	private String castreg = "%castreg";
	private String shftreg = "%shftreg";
	
	private String typereg = "%typereg";
	private String ptrreg = "%ptrreg";
	private String fshftreg = "%fshftreg";
	private String argsreg = "%argsreg";
	private String argptr = "%argptr";
	private String argslistptr = "%";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	private String thisptr = "%thisptr";
	
	private String cidptr = "%cidptr";
	private String cobjid = "%cobjid";
	
	public NewObj(String fname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.fname = fname;
		this.args = args;
		
		this.objreg += regnum;
		this.idptr += regnum;
		this.slotsptr += regnum;
		this.castreg += regnum;
		this.shftreg += regnum;
		
		this.thisptr += regnum;
		
		this.ptrreg += regnum;
		this.typereg += regnum;
		this.fshftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "argslistptr";
		this.cobjreg += regnum;
		this.numargs += regnum;
		this.cobjid += regnum;
		this.cidptr += regnum;
	}

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {

		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.objreg + " = malloc %pobj\n");
		
		//store id
		currentLine = new LLVMLine(this.idptr + " = getelementptr %pobj* " + this.objreg + 
				", i32 0, i32 0\n");
		currentLine = new LLVMLine("store i32 0, i32* " + this.idptr + "\n");//tag 0 for pobj
		
		//store empty slots
		currentLine = new LLVMLine(this.slotsptr + " = getelementptr %pobj* " + this.objreg + 
				", i32 0, i32 1\n");
		currentLine = new LLVMLine("store %slots* @empty_slots, %slots** " + this.slotsptr + "\n");
		
		//store pobj to env
		currentLine = new LLVMLine(this.castreg + " = ptrtoint %pobj* " + this.objreg + " to i32\n");
		currentLine = new LLVMLine(this.shftreg + " = lhs i32 " + this.castreg + ", 2\n");
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
		//tag is 01 cuz its ptr
		
		//RegAndIndex regind = Env.lookup(objid, env);
		//currentLine = new LLVMLineAll(regind.code);
		//currentLine = new LLVMLine(this.pttreg + " = getelementptr %eframe* " + 
				//regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		//currentLine = new LLVMLine("store i32 " + this.tagreg + ", i32* "+ this.pttreg + "\n");
		//currentLine = new LLVMLine("\n");
		
		//TODO call new?
		//Lookup closure by name in env
		RegAndIndex regind = Env.lookup(this.fname, env);
		this.code.addAll(regind.code);
		
		//get fun id
		//TODO bitcast to cobj
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine = new LLVMLine(this.typereg + " = load i32* " + this.ptrreg + "\n");
		//type check for ptr
		currentLine = new LLVMLine("call void @type_check( i32 " + this.typereg + ", i32 1)\n");//1 is cobj type
		//shift
		currentLine = new LLVMLine(this.fshftreg + " = lshr i32 " + this.typereg + ", 2\n");
		currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.fshftreg + 
				" to %cobj*\n");
		
		//Type check for closure
		currentLine = new LLVMLine(this.cidptr + " = getelementptr %cobj* " + this.cobjreg + 
		", i32 0, i32 0\n");
		currentLine = new LLVMLine(this.cobjid + " = load %slots** " + this.cidptr + "\n");
		currentLine = new LLVMLine("call void @obj_type_check( i32 " + this.cobjid + ", i32 1)\n");//1 is cobj type
		
		//evaluate args
		currentLine = new LLVMLine(this.argptr + " = malloc [" + (args.size() + 1) + " x i32], align 4\n");
		currentLine = new LLVMLine(this.argsreg + " = bitcast [" + (args.size() + 1) + " x i32]* " + 
				this.argptr + " to i32*\n");
		for(int i = 0;i < args.size();i++){
			this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.argslistptr + i + " = getelementptr i32* " + 
					this.argsreg + ", i32 " + i + "\n");
			currentLine = new LLVMLine("store i32 " + args.get(i).getReg() + ", i32* " + 
					this.argslistptr + i + "\n");
		}
		
		//add this reference
		currentLine = new LLVMLine(this.thisptr + " = getelementptr i32* " + this.argsreg + 
				", i32 " + args.size() + "\n");
		currentLine = new LLVMLine("store i32 " + this.objreg + ", i32* " + this.thisptr + "\n");
		
		//TODO Dipatch function nounwind needed?
		//send compiled args and closure id # to dispatch
		currentLine = new LLVMLine("call i32 @dispatch_fun( %cobj* " + 
				this.cobjreg + ", i32 " + (this.args.size() + 1) + ", i32* " + this.argsreg + " ) nounwind\n");
		
		return this;
	}

	@Override
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs) {
		//call staticPass on args?
		for(CodeAndReg arg: args){
			arg.staticPass(env, funcid, stringdecs);
		}
	}
	
	
	
}
