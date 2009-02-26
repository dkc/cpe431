package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.CodeAndReg;
import Values.PObject;
import Values.ReturnContainer;
import Values.VObject;
import Values.Value;

public class FieldMut extends AbstractCodeAndReg {
	String name;
	CodeAndReg newval;
	String obj;
	
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	private String shftreg = "%shftreg";
	private String castreg = "%castreg";
	private String slotsptrreg = "%slotptrsreg";
	private String slotsreg = "%slotsreg";
	private String lookupreg = "%lookupreg";
	private String newslotreg = "%newslotreg";
	private String newsptr = "%newsptr";
	private String fieldptr = "%fieldptr";
	private String fidptr = "%fidptr";
	private String valptr = "%valptr";
	
	public FieldMut(String name, CodeAndReg newval, String obj, int regnum){
		super(regnum);
		this.name = name;
		this.newval = newval;
		this.obj = obj;
		this.ptrreg += regnum;
		this.objreg += regnum;
		this.shftreg += regnum;
		this.castreg += regnum;
		this.slotsptrreg += regnum;
		this.slotsreg += regnum;
		this.lookupreg += regnum;
		this.newslotreg += regnum;
		this.newsptr += regnum;
		this.fieldptr += regnum;
		this.fidptr += regnum;
		this.valptr += regnum;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {
		//compile newVal
		this.code.addAll(this.newval.compile(env, funcdecs, fieldTable).getCode());
		
		//get obj pointer
		RegAndIndex regind = Env.lookup(obj, env);
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add(this.objreg + " = load i32* " + this.ptrreg + "\n");
		//TODO type check for float
		this.code.add(this.shftreg + " = lshr i32 " + this.objreg + ", 2\n");
		// what type to bitcast to, before type check?
		this.code.add(this.castreg + " = inttoptr i32 " + this.shftreg + 
				" to %pobj*\n");
		
		//get slots ptr
		this.code.add(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + 
				", i32 0, i32 1\n");
		this.code.add(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
		
		//get object type and check
		Integer fid = fieldTable.get(obj + name);
		if(fid == null){//add new field
			fid = fieldTable.put(obj + name, new Integer(this.regnum));
			//TODO adding to front, is it right?
			//malloc
			this.code.add(this.newslotreg + " = malloc %slots\n");
			//store old front of list to next
			this.code.add(this.newsptr + " = getelementptr %slots* " + 
					this.newslotreg + ", i32 0, i32 1\n");
			this.code.add("store %slots* " + this.slotsreg + ", %slots** " + 
					this.newsptr + "\n");
			//store field id
			this.code.add(this.fieldptr + " = getelementptr %slots* " + 
					this.newslotreg + ", i32 0, i32 0\n");
			this.code.add(this.fidptr + " = getelementptr %field*" + this.fieldptr + 
					", i32 0, i32 0\n");
			this.code.add("store i32 " + fid + ", i32* " + this.fidptr + "\n");
			//store field value
			this.code.add(this.valptr + " = getelementptr %field*" + this.fieldptr + 
					", i32 0, i32 1\n");
			this.code.add("store i32 " + this.newval.getReg() + ", i32* " + this.valptr + "\n");
			//store this slot to obj
			this.code.add("store %slots* " + this.newslotreg + ", %slots** " + this.slotsptrreg + "\n");
			
		}else{//store to field
		this.code.add( this.lookupreg + " = call i32* @lookup_field( i32 " + fid + 
				", %slots* " + this.slotsreg + "\n");
		this.code.add("store i32 " + newval.getReg() + ", i32* " + this.lookupreg + "\n");
		}
		
		//store retval
		this.code.add(this.reg + " = add i32 0, " + this.newval.getReg() + "\n");
		
		return this;
	}
}
