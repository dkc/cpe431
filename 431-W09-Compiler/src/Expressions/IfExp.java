package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
public class IfExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg fthen;
	CodeAndReg felse;
	Env thenscope;
	Env elsescope;
	String testreg = "%tstreg";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
		this.testreg += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		//this.thenscope = Env.addScope(new Env(), env);
		//this.elsescope = Env.addScope(new Env(), env);
		this.test.staticPass(env, null);
		//this.fthen.staticPass(this.thenscope);
		//this.felse.staticPass(this.elsescope);
		this.fthen.staticPass(env, null);
		this.felse.staticPass(env, null);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		//test
		this.test.compile(env, funcdecs, fieldTable);
		this.code.addAll(this.test.getCode());
		this.code.add(this.testreg + " = icmp eq i32 2, " + this.test.getReg() + "\n"); 
		this.code.add("br i1 " + this.testreg + ", label %then, label %else\n");
		
		//then
		//this.fthen.compile(this.thenscope);
		this.fthen.compile(env, funcdecs, fieldTable);
		this.code.add("then:\n");
		this.code.addAll(this.fthen.getCode());
		this.code.add("br label %end\n");
		
		//else
		//this.felse.compile(this.elsescope);
		this.felse.compile(env, funcdecs, fieldTable);
		this.code.add("else:\n");
		this.code.addAll(this.felse.getCode());
		this.code.add("br label %end\n");
		
		//end
		this.code.add("end:\n");
		this.code.add(this.reg + " = phi i32 [" + 
			this.fthen.getReg() + ",%then], [" + this.felse.getReg() + ",%else]\n");
		
		return this;
	}
}
