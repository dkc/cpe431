package Expressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Environment.Env;
import Expressions.FuncExp.BOp;
import Expressions.FuncExp.UOp;
import Values.*;

public class Application implements Expression{
	Expression function;
	ArrayList<Expression> args;
	
	public Application(Expression function, ArrayList<Expression> args){
		this.function = function;
		this.args = args;
	}
	
	public Value interp(Env env) {
		Value v = null;
		try {
			v = function.interp(env);
		} catch (ReturnException e) {
			System.err.println("Return is not a valid application; exiting");
		}
		
		if(v instanceof VClosure){
			VClosure clos = (VClosure) v;
			Value vargs;
			for(int i = 0;i < args.size();i++){
				try {
					vargs = args.get(i).interp(env);
				} catch (ReturnException e) {
					System.err.println("Return used in a closure argument--exiting...");
					System.exit(1);
					return null;
				}
				clos.env = Env.add(new Env(clos.params.get(i), vargs), clos.env);
			}
			try {
				clos.body.interp(clos.env);
				return new VVoid(); /* function did not return a value */
				
			} catch (ReturnException e) { /* when functions do return values, we catch their custom ReturnException */
				return e.storedContainer;
			}
		}
		else if (v instanceof VPrimitiveFunction){ //primitive
			if(args.size() == 0) { /* this better be readline--if it is, we handle it inline */
				if((String)v.storedValue() == "readline") {
					try {
						BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
						return new SObject(stdin.readLine() + "\n", null);
					} catch(IOException e) {
						System.err.println("I/O error in readline: " + e.toString());
						System.exit(1);
						return null;
					}
				}
			} else if(args.size() == 1) { /* should be a unary operation */
				UOp primitive = new UOp((String)v.storedValue());
				UnaryOperation unary = new UnaryOperation(primitive, args.get(0));
				try {
					return unary.interp(env);
				} catch (ReturnException e) {
					System.err.println("ReturnException in a unary operation; exiting");
					System.exit(1);
					return null;
				}
			} else if(args.size() == 2) { /* should be a binary operation */
				BOp primitive = new BOp((String)v.storedValue()); /* expression representing the primitive func */
				BinaryOperation binaryOperation = new BinaryOperation(args.get(0), primitive, args.get(1));
				return binaryOperation.interp(env);
			} else if(args.size() == 3) { /* should be substring; handled inline */
				if((String)v.storedValue() == "substring") {
					try {
						String targetString = ((SObject)(args.get(0).interp(env).storedValue())).strval;
						Integer startIndex = (Integer)args.get(1).interp(env).storedValue();
						Integer endIndex = (Integer)args.get(2).interp(env).storedValue();
						String substring = targetString.substring(startIndex.intValue(), endIndex.intValue());
						return new SObject(substring,null);
					} catch (ReturnException e) {
						System.err.println("A return expression provided to substring? Aww, you *shouldn't* have. EXITING");
						System.exit(1);
						return null;
					}
				}
			}
			/* we should never get here with a Footle primitive--error out */
			System.err.println("Primitive function " + (String)v.storedValue() + 
							   " provided with too many arguments; exiting");
			System.exit(1);
			return null;
		}
		else { /* don't know what they're trying to feed us but it can't be good */
			System.err.println("WUGGA WUGGA WUGGA FFFFFFFFFFFFFFFFF"); // this is obviously not final
			System.exit(1);
			return null;
		}
	}
}
