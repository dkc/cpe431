package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
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
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){

		LLVMLine currentLine;
		
		//test
		this.test.compile(env, funcdecs, fieldTable);
		this.code.addAll(this.test.getCode());
		
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 2, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.test.getReg());
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %then, label %else\n");
		currentLine.setOperation("br");
		currentLine.addRegisterUsed(this.testreg);
		
		//then
		//this.fthen.compile(this.thenscope);
		this.fthen.compile(env, funcdecs, fieldTable);
		
		currentLine = new LLVMLine("then:\n");
		currentLine.setOperation("label");
		
		this.code.addAll(this.fthen.getCode());
		
		currentLine = new LLVMLine("br label %end\n");
		currentLine.setOperation("br");
		
		//else
		//this.felse.compile(this.elsescope);
		this.felse.compile(env, funcdecs, fieldTable);
		currentLine = new LLVMLine("else:\n");
		currentLine.setOperation("label");
		
		this.code.addAll(this.felse.getCode());
		
		currentLine = new LLVMLine("br label %end\n");
		currentLine.setOperation("br");
		
		//end
		currentLine = new LLVMLine("end:\n");
		currentLine.setOperation("label");
		
		currentLine = new LLVMLine(this.reg + " = phi i32 [" + this.fthen.getReg() + ",%then], [" + this.felse.getReg() + ",%else]\n");
		currentLine.setOperation("phi");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.fthen.getReg());
		currentLine.addRegisterUsed(this.felse.getReg());
		
		return this;
	}
}
