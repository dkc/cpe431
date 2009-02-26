package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.Const.FVoid;

public class Application extends AbstractCodeAndReg{
	String fname;
	ArrayList<CodeAndReg> args;
	private String typereg = "%typereg";
	private String ptrreg = "%ptrreg";
	private String shftreg = "%shftreg";
	private String argsreg = "%argsreg";
	private String argptr = "%argptr";
	private String argslistptr = "%";
	private String cobjreg = "%clos";
	private String numargs = "%numargs";
	
	public Application(String fname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.fname = fname;
		this.args = args;
		this.ptrreg += regnum;
		this.typereg += regnum;
		this.shftreg += regnum;
		this.argsreg += regnum;
		this.argptr += regnum;
		this.argslistptr += regnum + "argslistptr";
		this.cobjreg += regnum;
		this.numargs += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		//check # args against # params?
	}
	
	/* all of the PrimitiveFunction business has been PULLED thanks to the magic of the parser/tree parser--
	 * UnaryOperation and BinaryOperation ate the bulk of this functionality and the other built-ins (readline,
	 * string=?, string<?) are "predefined" applications that should always be included and can be called through
	 * here */
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {
			//Lookup closure by name in env
			RegAndIndex regind = Env.lookup(this.fname, env);
			
			//get fun id
			//TODO bitcast to cobj
			this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
					regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
			this.code.add(this.typereg + " = load i32* " + this.ptrreg + "\n");
			//type check for func close
			this.code.add("call void @type_check( i32 " + this.typereg + ", i32 1)\n");//1 is cobj type
			//shift
			this.code.add(this.shftreg + " = lshr i32 " + this.typereg + ", 2\n");
			this.code.add(this.cobjreg + " = inttoptr i32 " + this.shftreg + 
					" to %cobj*\n");
			
			//evaluate args
			this.code.add(this.argptr + " = malloc [" + args.size() + " x i32], align 4\n");
			this.code.add(this.argsreg + " = bitcast [" + args.size() + " x i32]* " + 
					this.argptr + " to i32*\n");
			for(int i = 0;i < args.size();i++){
				this.code.addAll(args.get(i).compile(env, funcdecs, fieldTable).getCode());
				this.code.add(this.argslistptr + i + " = getelementptr i32* " + 
						this.argsreg + ", i32 " + i + "\n");
				this.code.add("store i32 " + args.get(i).getReg() + ", i32* " + 
						this.argslistptr + i + "\n");
			}
			
			//TODO Dipatch function nounwind needed?
			//send compiled args and closure id # to dispatch
			this.code.add(this.numargs + " = add i32 0, " + args.size() + "\n");
			this.code.add(this.reg + " = call i32 @dispatch_fun( %cobj* " + 
					this.cobjreg + ", i32 " + this.numargs + ", i32* " + this.argsreg + " ) nounwind\n");
			
			//TODO Tricky, does it work?
			//this.code.addAll(new FVoid(this.regnum).compile(env, funcdecs, fieldTable).getCode());
			return this; /* function did not return a value */
	}
}
