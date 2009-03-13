package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class FieldMut extends AbstractCodeAndReg {
	String name;
	CodeAndReg newval;
	CodeAndReg obj;
	
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
	private String idslotsptrreg = "%idslots";
	private String objid = "%objid";
	
	public FieldMut(CodeAndReg obj, String name, CodeAndReg newval, int regnum){
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
		this.idslotsptrreg += regnum;
		this.objid += regnum;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		//reserved name check
		for(int i = 0; i < res_len; i++){
			if(this.name.equals(reserved_names[i])){
				System.err.println("Static Pass Error Field Mutation: illegal use of primitive name");
				System.exit(-1);
			}
		}
		this.obj.staticPass(env, funcids, stringdecs);
		this.newval.staticPass(env, funcids, stringdecs);
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		//compile newVal
		this.code.addAll(this.newval.compile(env, funcdecs, fieldTable).getCode());
		
		//get obj pointer
		this.code.addAll(this.obj.compile(env, funcdecs, fieldTable).getCode());
		
		//typecheck ptr
		currentLine = new LLVMLine("call void @type_check( i32 " + this.obj.getReg() + ", i32 1 )\n");
		currentLine.setOperation("call");
		currentLine.setLabel("type_check");
		currentLine.addRegisterUsed(this.obj.getReg());
		currentLine.addConstantUsed(1);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.obj.getReg() + ", 2\n");
		currentLine.setOperation("lshr");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.obj.getReg());
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.castreg + " = inttoptr i32 " + this.shftreg + 
				" to %pobj*\n");

		currentLine.setOperation("inttoptr");
		currentLine.setRegisterDefined(this.castreg);
		currentLine.addRegisterUsed(this.shftreg);
		this.code.add(currentLine);
		
		//check for float
		currentLine = new LLVMLine(this.idslotsptrreg + " = getelementptr %pobj* " + this.castreg + 
		", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.idslotsptrreg);
		currentLine.addRegisterUsed(this.castreg);
		currentLine.addConstantUsed(4*0);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.objid + " = load i32* " + this.idslotsptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.objid);
		currentLine.addRegisterUsed(this.idslotsptrreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("call void @neg_float_check( i32 " + this.objid + " )\n");
		currentLine.setOperation("call");
		currentLine.setLabel("neg_float_check");
		currentLine.addRegisterUsed(this.objid);
		this.code.add(currentLine);
		
		//get slots ptr
		currentLine = new LLVMLine(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + 
				", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.slotsptrreg);
		currentLine.addConstantUsed(4*1);
		currentLine.addRegisterUsed(this.castreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
		currentLine.setOperation("load");
		currentLine.setRegisterDefined(this.slotsreg);
		currentLine.addRegisterUsed(this.slotsptrreg);
		this.code.add(currentLine);
		
		//lookup id
		//System.out.println("name: " + name);
		Integer fid = fieldTable.get(name);
		if(fid == null){//add new field
			//System.out.println("name added to fieldTable");
			fieldTable.put(name, new Integer(this.regnum));
			fid = this.regnum;
			
			//malloc
			currentLine = new LLVMLine(this.newslotreg + " = malloc %slots\n");
			currentLine.setOperation("malloc");
			currentLine.setRegisterDefined(this.newslotreg);
			currentLine.addConstantUsed(-1);//TODO sizeof slots
			this.code.add(currentLine);
			
			//store old front of list to next
			currentLine = new LLVMLine(this.newsptr + " = getelementptr %slots* " + 
					this.newslotreg + ", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.newsptr);
			currentLine.addRegisterUsed(this.newslotreg);
			currentLine.addConstantUsed(4*1);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine("store %slots* " + this.slotsreg + ", %slots** " + 
					this.newsptr + "\n");
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
			//System.out.println("name stored");
			currentLine = new LLVMLine(this.lookupreg + " = call i32* @field_lookup( i32 " + fid + ", %slots* " + this.slotsreg + " )\n");
			currentLine.setOperation("call");
			currentLine.setLabel("lookup_field");
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
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, 11\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(11);
		currentLine.addConstantUsed(0);
		this.code.add(currentLine);
		
		return this;
	}
}
