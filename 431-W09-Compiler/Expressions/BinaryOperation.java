package Expressions;


import Environment.Env;

import Values.*;


public class BinaryOperation implements Expression{
	
	public Expression left;
	public Expression op;
	public Expression right;
	
	public  BinaryOperation(Expression left, Expression op, Expression right){
		this.left = left;
		this.op = op;
		this.right = right;
	}
	
	public Value interp(Env env){
		String exp;
		Object lhs, rhs;
		try {
			exp = (String) (op.interp(env)).storedValue();
			lhs = left.interp(env).storedValue();
			rhs = right.interp(env).storedValue();
		} catch (ReturnException e) {
			System.err.println("You've got your Return in my BinOp! exiting");
			System.exit(1);
			return null;
		}
		
		if(((lhs instanceof Integer) || (lhs instanceof Float)) 
				&& ((rhs instanceof Integer) || (rhs instanceof Float))){
			if((lhs instanceof Float) || (rhs instanceof Float)){
				if(lhs instanceof Integer){
					lhs = new Float((Integer) lhs);
				}else if(rhs instanceof Integer){
					rhs = new Float((Integer) rhs);
				}
				if(exp.equals("+")){
					return new VFloat((Float) lhs + (Float) rhs);
				}else if(exp.equals("-")){
					return new VFloat((Float) lhs - (Float) rhs);
				}else if(exp.equals("*")){
					return new VFloat((Float) lhs * (Float) rhs);
				}else if(exp.equals("/")){
					return new VFloat((Float) lhs / (Float) rhs);
				}else if(exp.equals(">")){
					return new VBoolean((Float) lhs > (Float) rhs);
				}else if(exp.equals("<")){
					return new VBoolean((Float) lhs < (Float) rhs);
				}else if(exp.equals(">=")){
					return new VBoolean((Float) lhs >= (Float) rhs);
				}else if(exp.equals("<=")){
					return new VBoolean((Float) lhs <= (Float) rhs);
				}
			}else{//both int
				if(exp.equals("+")){
					return new VInteger((Integer) lhs + (Integer) rhs);
				}else if(exp.equals("-")){
					return new VInteger((Integer) lhs - (Integer) rhs);
				}else if(exp.equals("*")){
					return new VInteger((Integer) lhs * (Integer) rhs);
				}else if(exp.equals("/")){
					return new VInteger((Integer) lhs / (Integer) rhs);
				}else if(exp.equals(">")){
					return new VBoolean((Integer) lhs > (Integer) rhs);
				}else if(exp.equals("<")){
					return new VBoolean((Integer) lhs < (Integer) rhs);
				}else if(exp.equals(">=")){
					return new VBoolean((Integer) lhs >= (Integer) rhs);
				}else if(exp.equals("<=")){
					return new VBoolean((Integer) lhs <= (Integer) rhs);
				}
			}
		}else if((lhs instanceof Boolean) && (rhs instanceof Boolean)){
			if(exp.equals("and")){
				return new VBoolean((Boolean) lhs && (Boolean) rhs);
			}else if(exp.equals("or")){
				return new VBoolean((Boolean) lhs || (Boolean) rhs);
			}
		}else if((lhs instanceof SObject) && (rhs instanceof SObject)){
			lhs = ((SObject)lhs).strval;
			rhs = ((SObject)rhs).strval;
			if(exp.equals("string=?")){
				return new VBoolean(((String)lhs).equals(rhs));
			}else if(exp.equals("string<?")){
				return new VBoolean(((String)lhs).compareTo((String)rhs) < 0);
			}
		}
		
		if(exp.equals("==")){
			if(lhs instanceof CObject || lhs instanceof PObject || lhs instanceof SObject) /* pointer equals */
				return new VBoolean(lhs == rhs);
			else /* standard equals */
				return new VBoolean(lhs.equals(rhs));
		}else{
			System.err.println("binop with non numeric exp");
		}

		System.err.println("tragic failure in binop: " + lhs.getClass() + " " + exp + " " + rhs.getClass());
		System.exit(1);
		return null;
	}
}
