package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.Objects.PObjectExp;
import Values.*;

public class FieldLookup extends AbstractCodeAndReg {
	String name;
	String obj;
	
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	private String shftreg = "%shftreg";
	private String castreg = "%castreg";
	private String slotsptrreg = "%slotptrsreg";
	private String slotsreg = "%slotsreg";
	private String lookupreg = "%lookupreg";

	
	public FieldLookup(String name, String obj,int regnum){
		super(regnum);
		this.name = name;
		this.obj = obj;
		this.ptrreg += regnum;
		this.objreg += regnum;
		this.shftreg += regnum;
		this.castreg += regnum;
		this.slotsptrreg += regnum;
		this.slotsreg += regnum;
		this.lookupreg += regnum;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
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
			int fid = fieldTable.get(obj + name);
			this.code.add( this.lookupreg + " = call i32* @lookup_field( i32 " + fid + 
					", %slots* " + this.slotsreg + "\n");
			this.code.add(this.reg + " = load i32* " + this.lookupreg + "\n");
			
			/*get slots pointer and work through linked list
			this.code.add(this."");
			
			//load return val
			this.code.add(this.reg + " = add i32 0, " + this.valreg);
			*/
			return this;
	}

}
