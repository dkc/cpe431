package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Values.*;

public class NewObj extends AbstractCodeAndReg{
	String objid;
	ArrayList<CodeAndReg> args;
	
	public NewObj(String objid, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.objid = objid;
		this.args = args;
	}

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		this.code.add(this.objreg + " = malloc %pobj\n");
		
		//store pobj to env
		this.code.add(this.castreg + " = ptrtoint %pobj* " + this.objreg + " to i32\n");
		this.code.add(this.shftreg + " = lhs i32 " + this.castreg + ", 2\n");
		//tag is 00 cuz its pobj
		RegAndIndex regind = Env.lookup(objid, env);
		this.code.addAll(regind.code);
		this.code.add(this.pttreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add("store i32 " + this.shftreg + ", i32* "+ this.pttreg + "\n");
		this.code.add("\n");
		return this;
	}

	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids) {
		env.add(this.objid);
		//call staticPass on args?
		for(CodeAndReg arg: args){
			arg.staticPass(env, funcids);
		}
	}
	
	
}
