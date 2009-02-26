package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Environment.RegAndIndex;
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
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		//get obj pointer
		RegAndIndex regind = Env.lookup(obj, env);
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add(this.objreg + " = load i32* " + this.ptrreg + "\n");
		this.code.add(this.shftreg + " = lshr i32 " + this.objreg + ", 2\n");
		//TODO typecheck, but opposite here... as in !typecheck
		//this.code.add("call void @type_check( i32 " + ", i32 1 )\n");
		this.code.add(this.castreg + " = inttoptr i32 " + this.shftreg + 
				" to %pobj*\n");
		
		//update obj slots ref
		this.code.add(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + 
				", i32 0, i32 1\n");
		this.code.add(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
		
		//get object type and check
		int fid = fieldTable.get(obj + mname);
		this.code.add( this.lookupreg + " = call i32* @lookup_field( i32 " + fid + 
				", %slots* " + this.slotsreg + "\n");
		this.code.add(this.typereg + " = load i32* " + this.lookupreg + "\n");
		
		
		
		
		
		//Lookup closure by name in env
		//RegAndIndex regind = Env.lookup(this.mname, env);
		
		//get fun id
		//bitcast to cobj
		//this.code.add(this.cptrreg + " = getelementptr %eframe* " + 
			//	regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		//this.code.add(this.typereg + " = load i32* " + this.cptrreg + "\n");
		//type check for func close
		this.code.add("call void @type_check( i32 " + this.typereg + ", i32 1)\n");//1 is cobj type
		//shift
		this.code.add(this.cshftreg + " = lshr i32 " + this.typereg + ", 2\n");
		this.code.add(this.cobjreg + " = inttoptr i32 " + this.cshftreg + 
				" to %cobj*\n");
		
		//evaluate args
		this.code.add(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
		this.code.add(this.argsreg + " = bitcast [" + args.size() + " x i32]* " + 
				this.argptr + " to i32*\n");
		for(int i = 0;i < args.size();i++){
			this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
			this.code.add(this.argslistptr + i + " = getelementptr i32* " + 
					this.argsreg + ", i32 " + i + "\n");
			this.code.add("store i32 " + args.get(i).getReg() + ", i32* " + 
					this.argslistptr + i + "\n");
		}
		
		//TODO Dipatch function nounwind needed?
		//send compiled args and closure id # to dispatch
		this.code.add(this.numargs + " = add i32 0, " + args.size() + "\n");
		this.code.add(this.reg + " = call i32 @dispatch_fun( %cobj* " + 
				this.cobjreg + ", i32 " + this.numargs + ", i32* " + this.argsreg + " ) nounwind\n");
		
		//TODO Tricky, does it work?
		//this.code.addAll(new FVoid(this.regnum).compile(env, funcdecs, fieldTable).getCode());
		return this; /* function did not return a value */
	}
}