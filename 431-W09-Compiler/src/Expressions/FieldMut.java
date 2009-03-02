package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;
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
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		//compile newVal
		this.code.addAll(this.newval.compile(env, funcdecs, fieldTable).getCode());
		
		//get obj pointer
		RegAndIndex regind = Env.lookup(obj, env);
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		currentLine.addConstantUsed(4*2);
		currentLine.addConstantUsed(4*regind.index);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.objreg + " = load i32* " + this.ptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.objreg);
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		//TODO type check for float
		
		currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.objreg + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.objreg);
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		// what type to bitcast to, before type check?
		
		currentLine = new LLVMLine(this.castreg + " = inttoptr i32 " + this.shftreg + " to %pobj*\n");
		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.castreg);
		currentLine.addRegisterUsed(this.shftreg);
		this.code.add(currentLine);
		
		//get slots ptr
		
		currentLine = new LLVMLine(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + ", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.slotsptrreg);
		currentLine.addRegisterUsed(this.castreg);
		currentLine.addConstantUsed(4*1);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.slotsreg);
		currentLine.addRegisterUsed(this.slotsptrreg);
		this.code.add(currentLine);
		
		//get object type and check
		Integer fid = fieldTable.get(obj + name);
		if(fid == null){//add new field
			fid = fieldTable.put(obj + name, new Integer(this.regnum));
			//TODO adding to front, is it right?
			//malloc
			
			currentLine = new LLVMLine(this.newslotreg + " = malloc %slots\n");
			currentLine.setOperation("malloc");
			currentLine.setRegisterDefined(this.newslotreg);
			currentLine.addConstantUsed(-1); // how big is slots?
			this.code.add(currentLine);
			
			//store old front of list to next
			
			currentLine = new LLVMLine(this.newsptr + " = getelementptr %slots* " + this.newslotreg + ", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.newsptr);
			currentLine.addRegisterUsed(this.newslotreg);
			currentLine.addConstantUsed(4*1);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store %slots* " + this.slotsreg + ", %slots** " + this.newsptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.slotsreg);
			currentLine.addRegisterUsed(this.newsptr);
			this.code.add(currentLine);
			
			//store field id
			
			currentLine = new LLVMLine(this.fieldptr + " = getelementptr %slots* " + this.newslotreg + ", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.fieldptr);
			currentLine.addRegisterUsed(this.newslotreg);
			currentLine.addConstantUsed(4*0);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.fidptr + " = getelementptr %field*" + this.fieldptr + ", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.fidptr);
			currentLine.addRegisterUsed(this.fieldptr);
			currentLine.addConstantUsed(4*0);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 " + fid + ", i32* " + this.fidptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.fidptr);
			this.code.add(currentLine);
			
			//store field value
			
			currentLine = new LLVMLine(this.valptr + " = getelementptr %field*" + this.fieldptr + ", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.valptr);
			currentLine.addRegisterUsed(this.fieldptr);
			currentLine.addConstantUsed(4*1);
			this.code.add(currentLine);
			
			
			currentLine = new LLVMLine("store i32 " + this.newval.getReg() + ", i32* " + this.valptr + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.newval.getReg());
			currentLine.addRegisterUsed(this.valptr);
			this.code.add(currentLine);
			
			//store this slot to obj
			
			currentLine = new LLVMLine("store %slots* " + this.newslotreg + ", %slots** " + this.slotsptrreg + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(this.newslotreg);
			currentLine.addRegisterUsed(this.slotsptrreg);
			this.code.add(currentLine);
			
		}else{//store to field
			
			currentLine = new LLVMLine(this.lookupreg + " = call i32* @lookup_field( i32 " + fid + ", %slots* " + this.slotsreg + "\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.lookupreg);
			currentLine.addRegisterUsed(this.slotsreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store i32 " + newval.getReg() + ", i32* " + this.lookupreg + "\n");
			currentLine.setOperation("store");
			currentLine.addRegisterUsed(newval.getReg());
			currentLine.addRegisterUsed(this.lookupreg);
			this.code.add(currentLine);
		}
		
		//store retval
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + this.newval.getReg() + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.newval.getReg());
		currentLine.addConstantUsed(0);
		this.code.add(currentLine);
		
		return this;
	}
}
