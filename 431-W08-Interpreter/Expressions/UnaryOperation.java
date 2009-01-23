package Expressions;

import Environment.Env;
import Values.*;

public class UnaryOperation implements Expression {
	public Expression operation;
	public Expression operand;
	
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
	
	public UnaryOperation(Expression op, Expression operationTarget) {
		operation = op;
		operand = operationTarget;
	}

	@Override
	public Value interp(Env env) throws ReturnException {
		String operationValue = (String) (operation.interp(env)).storedValue();
		
		Value operandValue = operand.interp(env);
		
		if (operationValue.equals("string-length")) {
			if(operandValue instanceof SObject) {
				VInteger stringLength = new VInteger(((SObject)operandValue).strval.length());
				return stringLength;
			}
		} else if (operationValue.equals("integer?")) {
			if(operandValue instanceof VInteger)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("boolean?")) {
			if(operandValue instanceof VBoolean)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		}else if (operationValue.equals("floating-point?")) {
			if(operandValue instanceof VFloat)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("void?")) {
			if(operandValue instanceof VVoid)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("string?")) {
			if(operandValue instanceof SObject)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("closure?")) {
			if(operandValue instanceof CObject)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("plain?")) {
			if(operandValue instanceof PObject)
				return new VBoolean(true);
			else
				return new VBoolean(false);
		} else if (operationValue.equals("print")) { /* attempts to use the value's toString and returns a void */
			System.out.println(operandValue);
			return new VVoid();
		} else if (operationValue.equals("not")){
			if(operandValue instanceof VBoolean)
				return new VBoolean(!((Boolean)operandValue.storedValue()));
		}
		
		/* if all else fails, error */
		System.err.println("Error: Cannot apply operation " + operationValue + " to single operand " + operandValue);
		System.exit(1);
		return null;
	}
}
