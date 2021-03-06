package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class NewObj extends AbstractCodeAndReg{
	VarRef fname;
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
	
	private String constmal = "%constmal";
	private String fieldptr = "%fieldptr";
	private String fidptr = "%fidptr";
	private String fvalptr = "%fvalptr";
	private String emptyptr = "%emptyptr";
	
	
	public NewObj(VarRef fname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.fname = fname;
		this.args = args;
		
		this.constmal += regnum;
		this.fieldptr += regnum;
		this.fidptr += regnum;
		this.fvalptr += regnum;
		this.emptyptr += regnum;
		
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
		this.code.add(currentLine);
		
		//store id
		currentLine = new LLVMLine(this.idptr + " = getelementptr %pobj* " + this.objreg + 
				", i32 0, i32 0\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 0, i32* " + this.idptr + "\n");//tag 0 for pobj
		this.code.add(currentLine);
		
		//store pobj to env
		currentLine = new LLVMLine(this.castreg + " = ptrtoint %pobj* " + this.objreg + " to i32\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.castreg + ", 2\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
		this.code.add(currentLine);
		
		//tag is 01 cuz its ptr
		
		//CALL NEW
		//Lookup closure by name in env
		this.code.addAll(this.fname.compile(env, funcdecs, fieldTable).getCode());
		
		//type check for ptr
		currentLine = new LLVMLine("call void @type_check( i32 " + this.fname.getReg() + ", i32 1)\n");//1 is cobj type
		this.code.add(currentLine);
		
		//shift
		currentLine = new LLVMLine(this.fshftreg + " = lshr i32 " + this.fname.getReg() + ", 2\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.cobjreg + " = inttoptr i32 " + this.fshftreg + 
				" to %cobj*\n");
		this.code.add(currentLine);
		
		//Type check for closure
		currentLine = new LLVMLine(this.cidptr + " = getelementptr %cobj* " + this.cobjreg + 
		", i32 0, i32 0\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.cobjid + " = load i32* " + this.cidptr + "\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("call void @obj_type_check( i32 " + this.cobjid + ", i32 1)\n");//1 is cobj type
		this.code.add(currentLine);
		
		//store new to first slot in obj
		fieldTable.put("constructor", new Integer(this.regnum));
		int fid = this.regnum;
		
		currentLine = new LLVMLine(this.slotsptr + " = getelementptr %pobj* " + this.objreg + 
		", i32 0, i32 1\n");
		this.code.add(currentLine);

		currentLine = new LLVMLine(this.constmal + " = malloc %slots\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store %slots* " + this.constmal + ", %slots** " + this.slotsptr + "\n");
		this.code.add(currentLine);
		
		//store field and val
		currentLine = new LLVMLine(this.fieldptr + " = getelementptr %slots* " + this.constmal + 
				", i32 0, i32 0\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.fidptr + " = getelementptr %field* " + this.fieldptr +
				", i32 0, i32 0\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + fid + ", i32* " + this.fidptr + "\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.fvalptr + " = getelementptr %field* " + this.fieldptr +
				", i32 0, i32 1\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + this.fname.getReg() + ", i32* " + this.fvalptr + "\n");
		this.code.add(currentLine);
		
		//store empty slots
		currentLine = new LLVMLine(this.emptyptr + " = getelementptr %slots* " + this.constmal + 
		", i32 0, i32 1\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store %slots* @empty_slots, %slots** " + this.emptyptr + "\n");
		this.code.add(currentLine);
		
		//evaluate args
		currentLine = new LLVMLine(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.argsreg + " = bitcast [" + args.size() + " x i32]* " + 
				this.argptr + " to i32*\n");
		this.code.add(currentLine);
		for(int i = 0;i < args.size();i++){
			this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
			currentLine = new LLVMLine(this.argslistptr + i + " = getelementptr i32* " + 
					this.argsreg + ", i32 " + i + "\n");
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 " + args.get(i).getReg() + ", i32* " + 
					this.argslistptr + i + "\n");
			this.code.add(currentLine);
		}
		
		//send compiled args and closure id # to dispatch
		currentLine = new LLVMLine("call i32 @dispatch_fun( %cobj* " + 
				this.cobjreg + ", i32 " + this.args.size() + ", i32* " + this.argsreg + ", i32 " + this.reg + " ) nounwind\n");
		this.code.add(currentLine);
		
		return this;
	}

	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		this.fname.staticPass(env, funcids, stringdecs);
		//call staticPass on args?
		for(CodeAndReg arg: args){
			arg.staticPass(env, funcids, stringdecs);
		}
	}
	
	
	
}
