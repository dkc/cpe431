package Expressions;

import Environment.Env;
import Values.*;

public class UnaryOperation extends AbstractCodeAndReg {
	public String operation;
	public CodeAndReg operand;
	private Env operandScope;
	
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
	
	public UnaryOperation(String operation, CodeAndReg operand, int regnum) {
		super(regnum);
		this.operation = operation;
		this.operand = operand;
	}
	
	@Override
	public void staticPass(Env env){
		operandScope = Env.addScope(new Env(), env);
		this.operand.staticPass(this.operandScope);
	}

	@Override
	public CodeAndReg compile(Env env) {
		operand.compile(env);
		this.code.addAll(operand.getCode());
		String targetReg = operand.getReg();
		this.code.add("UNARYOPERATION PLACEHOLDER: " + this.getReg() + " gets the result of " + operation + " " + targetReg + "\n");
		
		if (operation.equals("string-length")) {
		} else if (operation.equals("integer?")) {
		} else if (operation.equals("boolean?")) {
		} else if (operation.equals("floating-point?")) {
		} else if (operation.equals("void?")) {
		} else if (operation.equals("string?")) {
		} else if (operation.equals("closure?")) {
		} else if (operation.equals("plain?")) {
		} else if (operation.equals("print")) {
		} else if (operation.equals("not")){
		}
		
		return this;
	}
}
