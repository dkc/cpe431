package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;

public class FReturn extends AbstractCodeAndReg {
	CodeAndReg target;
	
	
	public FReturn(CodeAndReg target,int regnum){
		super(regnum);
		this.target = target;
	}
	
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
			this.code.addAll(target.compile(env, funcdecs, fieldTable).getCode());
			this.code.add("ret i32 " + target.getReg() + "\n");
		return this;
	}


	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids) {
		// TODO Auto-generated method stub
		super.staticPass(env, funcids);
	}

}
