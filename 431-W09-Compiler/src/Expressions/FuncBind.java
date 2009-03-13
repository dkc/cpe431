package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class FuncBind extends AbstractCodeAndReg {
	public ArrayList<FuncDec> funs;

	public FuncBind(ArrayList<FuncDec> funs, int regnum){
		super(regnum);
		//System.out.println("made funcbind");
		this.funs = funs;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
		for(FuncDec f: funs){
			env.add(f.name);
			//System.out.println("added " + f.name);
			//env.add(funs.get(i).name + "met");
		}
		for(FuncDec f: funs){
			f.staticPass(env, funcids, stringdecs);
		}
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		//compile all func decs
		for(CodeAndReg fun: this.funs){
			this.code.addAll(fun.compile(env, funcdecs, fieldTable).getCode());
		}
		
		//retturn void
		
		LLVMLine line = new LLVMLine(this.reg + " = add i32 0, 11\n");
		line.setOperation("add");
		line.setRegisterDefined(this.reg);
		line.addConstantUsed(0);
		line.addConstantUsed(11);
		this.code.add(line);
		
		return this;
	}
}
