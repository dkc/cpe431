package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class FieldLookup extends AbstractCodeAndReg {
	String name;
	CodeAndReg obj;
	
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	private String shftreg = "%shftreg";
	private String castreg = "%castreg";
	private String slotsptrreg = "%slotptrsreg";
	private String slotsreg = "%slotsreg";
	private String lookupreg = "%lookupreg";
	private String idslotsptrreg = "%idslots";
	private String objid = "%objid";

	
	public FieldLookup(CodeAndReg obj, String name, int regnum){
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
		this.idslotsptrreg += regnum;
		this.objid += regnum;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		//reserved name use check
			for(int i = 0; i < res_len; i++){
				if(this.name.equals(reserved_names[i])){
					System.err.println("Static Pass Error Field Lookup: illegal use of primitive name");
					System.exit(-1);
				}
			}
		this.obj.staticPass(env, funcids, stringdecs);
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
			LLVMLine currentLine;
		
			//get obj pointer
			this.code.addAll(this.obj.compile(env, funcdecs, fieldTable).getCode());
			
			//typecheck ptr
			currentLine = new LLVMLine("call void @type_check( i32 " + this.obj.getReg() + ", i32 1 )\n");
			currentLine.setOperation("call");
			currentLine.setLabel("type_check");
			currentLine.addRegisterUsed(this.obj.getReg());
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
			
			
			//update obj slots ref
			currentLine = new LLVMLine(this.slotsptrreg + " = getelementptr %pobj* " + this.castreg + 
					", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.slotsptrreg);
			currentLine.addRegisterUsed(this.castreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.slotsreg + " = load %slots** " + this.slotsptrreg + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(this.slotsreg);
			currentLine.addRegisterUsed(this.slotsptrreg);
			this.code.add(currentLine);
			
			//lookup field
			System.out.println(name);
			Integer fid = fieldTable.get(name);
			if(fid.equals(null)){
				System.err.println("Runtime Error Field Lookup: field does not exist\n");
				System.exit(-1);
			}else{
			currentLine = new LLVMLine( this.lookupreg + " = call i32* @field_lookup( i32 " + fid + 
					", %slots* " + this.slotsreg + " )\n");
			currentLine.setOperation("call");
			currentLine.setLabel("lookup_field");
			currentLine.setRegisterDefined(this.lookupreg);
			currentLine.addRegisterUsed(this.slotsreg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.reg + " = load i32* " + this.lookupreg + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.lookupreg);
			this.code.add(currentLine);
			}
			return this;
	}

}
