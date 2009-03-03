package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;

public class FuncBind extends AbstractCodeAndReg {
	public ArrayList<FuncDec> funs;

	public FuncBind(ArrayList<FuncDec> funs){
		super(-1);
		this.funs = funs;
	}
	
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs){
		for(int i = 1; i < funs.size(); i++){
			env.add(funs.get(i).name);
			//env.add(funs.get(i).name + "met");
		}
		for(FuncDec f: funs){
			f.staticPass(env, funcid, stringdecs);
		}
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		//compile all func decs
		for(CodeAndReg fun: this.funs){
			this.code.addAll(fun.compile(env, funcdecs, fieldTable).getCode());
		}
		
		//retturn void
		
		LLVMLine line = new LLVMLine(this.reg + " = add i32 0, 10\n");
		line.setOperation("add");
		line.setRegisterDefined(this.reg);
		line.addConstantUsed(0);
		line.addConstantUsed(10);
		this.code.add(line);
		
		return this;
	}
}
