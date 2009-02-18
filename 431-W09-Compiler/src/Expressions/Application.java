package Expressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Environment.Env;
import Expressions.Const.FVoid;
import Expressions.FuncExp.BOp;
import Expressions.FuncExp.UOp;

public class Application extends AbstractCodeAndReg{
	CodeAndReg function;
	ArrayList<CodeAndReg> args;
	
	public Application(CodeAndReg function, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.function = function;
		this.args = args;
	}
	
	public void staticPass(Env env){
		//new scope ?
		if(this.function instanceof FuncDec){
		FuncDec fdec = (FuncDec) this.function;
		fdec.staticPass(env);
		for(int i = 0;i < args.size();i++){
			if(i == 1){
				//TODO set regnum
				env.setNewScope(-1);
			}
			fdec.env = Env.add(new Env(fdec.params.get(i)), fdec.env);
		}
		}
		return env;
	}
	
	public CodeAndReg compile(Env env) {
		//Value v = null;
		try {
			this.code.addAll(function.compile(env).getCode());
		} catch (ReturnException e) {
			System.err.println("Return is not a valid application; exiting");
		}
		
		if(function instanceof FuncDec){
			FuncDec clos = (FuncDec) function;
			//CodeAndReg vargs;
			for(int i = 0;i < args.size();i++){
				try {
					//vargs = args.get(i).interp(env);
					//compile args
					this.code.addAll(args.get(i).compile(env).getCode());
					
					
					
				} catch (ReturnException e) {
					System.err.println("Return used in a closure argument--exiting...");
					System.exit(1);
					return null;
				}
				//TODO add code to store from arg to env
				
				
				//clos.env = Env.add(new Env(clos.params.get(i), vargs), clos.env);
			}
			try {
				//clos.body.interp(clos.env);
				//add func dec, is this the right place?
				this.code.addAll(clos.body.compile(env).getCode());
				
				//TODO Dipatch function
				
				
				//TODO regnum?
				return new FVoid(-1).compile(env); /* function did not return a value */
				
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
				BOp primitive = new BOp((String)v.storedValue()); /* CodeAndReg representing the primitive func */
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
						System.err.println("A return CodeAndReg provided to substring? Aww, you *shouldn't* have. EXITING");
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
