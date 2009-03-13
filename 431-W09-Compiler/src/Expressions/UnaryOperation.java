package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class UnaryOperation extends AbstractCodeAndReg {
	/* valid unary operations:
	 *	* string-length
	 *	* integer?
	 *	* floating-point?
	 *	* void?
	 *	* string?
	 *	* closure?
	 *	* plain?
	 *	* print
	 *  * not
	 */
	
	public String operation;
	public CodeAndReg operand;
	private Env operandScope;
	private String objreg = "%objreg";
	private String idptrreg = "%idptrreg";
	private String idreg = "%idreg";
	private String strptrreg = "%strptrreg";
	private String strreg = "%strreg";
	private String shftreg = "%shftreg";
	private String ashftreg = "%ashftreg";
	private String addreg = "%addreg";
	private String boolextreg = "%boolextreg";
	
	public UnaryOperation(String operation, CodeAndReg operand, int regnum) {
		super(regnum);
		//System.out.println("op in unary op: " + operation);
		this.operation = operation;
		this.operand = operand;
		this.objreg += regnum;
		this.idptrreg += regnum;
		this.idreg += regnum;
		this.strptrreg += regnum;
		this.strreg += regnum;
		this.shftreg += regnum;
		this.ashftreg += regnum;
		this.addreg += regnum;
		this.boolextreg += regnum;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
		operandScope = Env.addScope(new Env(this.regnum), env);
		this.operand.staticPass(this.operandScope, null, stringdecs);
	}

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		
		this.code.addAll(operand.compile(env, funcdecs, fieldTable).getCode());
		//this.code.addAll(operand.getCode());
		//String targetReg = operand.getReg();
		
		//currentLine = new LLVMLine("UNARYOPERATION PLACEHOLDER: " + this.getReg() + " gets the result of " + operation + " " + targetReg + "\n");
		//this.code.add(currentLine);

		if (operation.equals("stringLength")) {
			//call string_len
			currentLine = new LLVMLine(this.reg + " = call i32 @string_len( i32 " + this.operand.getReg() +  " )\n");
			this.code.add(currentLine);
		} else if (operation.equals("int?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_int_check( i32 " + this.operand.getReg() + " )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			this.code.add(currentLine);
			
		} else if (operation.equals("bool?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_bool_check( i32 " + this.operand.getReg() + " )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			this.code.add(currentLine);
			
		} else if (operation.equals("float?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_obj_check( i32 " + this.operand.getReg() + ", i32 3 )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(3);
			this.code.add(currentLine);
		} else if (operation.equals("void?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_void_check( i32 " + this.operand.getReg() + " )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			this.code.add(currentLine);
		} else if (operation.equals("string?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_obj_check( i32 " + this.operand.getReg() + ", i32 2 )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
		} else if (operation.equals("closure?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_obj_check( i32 " + this.operand.getReg() + ", i32 1 )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(1);
			this.code.add(currentLine);
		} else if (operation.equals("plain?")) {
			currentLine = new LLVMLine(this.reg + " = call i32 @instance_obj_check( i32 " + this.operand.getReg() + ", i32 0 )\n");
			currentLine.setOperation("call");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(0);
			this.code.add(currentLine);
		} else if (operation.equals("print")) {
			//calll print
			currentLine = new LLVMLine("call void @footle_print( i32 " + this.operand.getReg() + " )\n");
			currentLine.setOperation("call");
			currentLine.addRegisterUsed(this.operand.getReg());
			this.code.add(currentLine);
			
			//TODO ret string instead
			currentLine = new LLVMLine(this.reg + " = add i32 0, 11\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addConstantUsed(0);
			currentLine.addConstantUsed(11);
			this.code.add(currentLine);
			
		} else if (operation.equals("not")){
			currentLine = new LLVMLine("call void @type_check( i32 " + this.operand.getReg() + ", i32 3)\n");
			currentLine.setOperation("call");
			currentLine.setLabel("type_check");
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(3);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.shftreg + " = lshr i32 " + this.operand.getReg() + ", 2\n");
			currentLine.setOperation("lshr");
			currentLine.setRegisterDefined(this.shftreg);
			currentLine.addRegisterUsed(this.operand.getReg());
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.boolextreg + " = xor i32 " + this.shftreg + ", 1\n");
			currentLine.setOperation("xor");
			currentLine.setRegisterDefined(this.boolextreg);
			currentLine.addRegisterUsed(this.shftreg);
			currentLine.addConstantUsed(1);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.addreg + " = shl i32 " + this.boolextreg + ", 2\n");
			currentLine.setOperation("shl");
			currentLine.setRegisterDefined(this.addreg);
			currentLine.addRegisterUsed(this.boolextreg);
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
			
			//add 3 for bool tag
			currentLine = new LLVMLine(this.reg + " = add i32 3, " + this.addreg + "\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.addreg);
			currentLine.addConstantUsed(3);
			this.code.add(currentLine);
		}
		
		return this;
	}
}
