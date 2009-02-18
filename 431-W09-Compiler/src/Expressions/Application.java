package Expressions;

import java.util.ArrayList;

import Environment.Env;
import Expressions.Const.FVoid;

public class Application extends AbstractCodeAndReg{
	CodeAndReg function;
	ArrayList<CodeAndReg> args;
	
	public Application(CodeAndReg function, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.function = function;
		this.args = args;
	}
	
	public void staticPass(Env env){
		/********** honestly baffled as to what this is trying to do, commenting it out for now to shut down errors
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
		***********/
	}
	
	/* all of the PrimitiveFunction business has been PULLED thanks to the magic of the parser/tree parser--
	 * UnaryOperation and BinaryOperation ate the bulk of this functionality and the other built-ins (readline,
	 * string=?, string<?) are "predefined" applications that should always be included and can be called through
	 * here */
	public CodeAndReg compile(Env env) {
		//Value v = null;
		this.code.addAll(function.compile(env).getCode());
		
		if(function instanceof FuncDec){
			FuncDec clos = (FuncDec) function;
			//CodeAndReg vargs;
			for(int i = 0;i < args.size();i++){
				//vargs = args.get(i).interp(env);
				//compile args
				this.code.addAll(args.get(i).compile(env).getCode());

				//TODO add code to store from arg to env
				
				
				//clos.env = Env.add(new Env(clos.params.get(i), vargs), clos.env);
			}
			//clos.body.interp(clos.env);
			//add func dec, is this the right place?
			this.code.addAll(clos.body.compile(env).getCode());
			
			//TODO Dipatch function
			
			
			//TODO regnum?
			return new FVoid(-1).compile(env); /* function did not return a value */
		}
		else { /* don't know what they're trying to feed us but it can't be good */
			System.err.println("WUGGA WUGGA WUGGA FFFFFFFFFFFFFFFFF"); // this is obviously not final
			System.exit(1);
			return null;
		}
	}
}
