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
	String thenlbl = "then";
	String elselbl = "else";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
		this.testreg += regnum;
		this.thenlbl += regnum;
		this.elselbl += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs){
		//this.thenscope = Env.addScope(new Env(), env);
		//this.elsescope = Env.addScope(new Env(), env);
		this.test.staticPass(env, funcids, stringdecs);
		//this.fthen.staticPass(this.thenscope);
		//this.felse.staticPass(this.elsescope);
		this.fthen.staticPass(env, funcids, stringdecs);
		this.felse.staticPass(env, funcids, stringdecs);
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		ArrayList<LLVMLine> iffunc = new ArrayList<LLVMLine>();
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.reg + " =  call i32 @if_func" + this.regnum + "( %eframe* " + env.getCurrentScope() + " )\n");
		this.code.add(currentLine);
		
		//define func
		currentLine = new LLVMLine("define i32 @if_func" + this.regnum + "( %eframe* " + env.getCurrentScope() + " ){\n");
		iffunc.add(currentLine);
		
		//test
		this.test.compile(env, funcdecs, fieldTable);
		iffunc.addAll(this.test.getCode());
		
		//TODO type check boolean?
		
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 6, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.test.getReg());
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %" + this.thenlbl + 
				", label %" + this.elselbl + "\n");
		currentLine.setOperation("br");
		currentLine.addRegisterUsed(this.testreg);
		iffunc.add(currentLine);
		
		//then
		//TODO malloc then scope
		
		//this.fthen.compile(this.thenscope);
		this.fthen.compile(env, funcdecs, fieldTable);
		
		currentLine = new LLVMLine(this.thenlbl + ":\n");
		currentLine.setOperation("label");
		iffunc.add(currentLine);
		
		iffunc.addAll(this.fthen.getCode());
		
		currentLine = new LLVMLine("ret i32 " + this.fthen.getReg() + "\n");
		currentLine.setOperation("ret");
		iffunc.add(currentLine);
		
		//else
		//TODO malloc else sccope
		
		//this.felse.compile(this.elsescope);
		this.felse.compile(env, funcdecs, fieldTable);
		currentLine = new LLVMLine(this.elselbl + ":\n");
		currentLine.setOperation("label");
		iffunc.add(currentLine);
		
		iffunc.addAll(this.felse.getCode());
		
		currentLine = new LLVMLine("ret i32 " + this.felse.getReg() + "\n");
		currentLine.setOperation("ret");
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine("}\n");
		iffunc.add(currentLine);
		

		funcdecs.addAll(iffunc);
		
		return this;
	}
}
