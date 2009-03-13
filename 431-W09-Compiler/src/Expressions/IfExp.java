package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class IfExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg fthen;
	CodeAndReg felse;
	Env thenscope;
	Env elsescope;
	String testreg = "%tstreg";
	String thenlbl = "then";
	String elselbl = "else";
	private String thenenvptr = "%thenenvptr";
	private String elseenvptr = "%elseenvptr";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
		this.testreg += regnum;
		this.thenlbl += regnum;
		this.elselbl += regnum;
		this.thenenvptr += regnum;
		this.elseenvptr += regnum;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
		this.thenscope = Env.addScope(new Env(regnum), env);
		this.elsescope = Env.addScope(new Env(-regnum), env);
		this.test.staticPass(env, funcids, stringdecs);
		this.fthen.staticPass(this.thenscope,funcids,stringdecs);
		this.felse.staticPass(this.elsescope,funcids,stringdecs);
		//this.fthen.staticPass(env, funcids, stringdecs);
		//this.felse.staticPass(env, funcids, stringdecs);
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		ArrayList<LLVMLine> iffunc = new ArrayList<LLVMLine>();
		LLVMLine currentLine;
		
		//call if fun
		currentLine = new LLVMLine(this.reg + " = call i32 @if_func" + this.regnum + "( %eframe* " + env.getCurrentScope() + " )\n");
		currentLine.setOperation("call");
		currentLine.setLabel("if_func" + this.regnum);
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(env.getCurrentScope());
		this.code.add(currentLine);
		
		//define func
		currentLine = new LLVMLine("define i32 @if_func" + this.regnum + "( %eframe* " + env.getCurrentScope() + " ){\n");
		iffunc.add(currentLine);
		
		//test
		iffunc.addAll(this.test.compile(env, funcdecs, fieldTable).getCode());
		
		//type check boolean?
		currentLine = new LLVMLine("call void @type_check( i32 " + this.test.getReg() + ", i32 3 )\n");
		currentLine.setOperation("call");
		currentLine.addRegisterUsed(this.test.getReg());
		currentLine.addConstantUsed(3);
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 7, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.test.getReg());
		currentLine.addConstantUsed(6);
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %" + this.thenlbl + 
				", label %" + this.elselbl + "\n");
		currentLine.setOperation("br i1");
		currentLine.addRegisterUsed(this.testreg);
		currentLine.addRegisterUsed(this.thenlbl);
		currentLine.addRegisterUsed(this.elselbl);
		iffunc.add(currentLine);
		
		//then
		currentLine = new LLVMLine(this.thenlbl + ":\n");
		currentLine.setOperation("label");
		currentLine.setLabel(this.thenlbl);
		iffunc.add(currentLine);
		
		//malloc then scope
		//closure env
		currentLine = new LLVMLine(thenscope.getMallocReg() + " = malloc {%eframe*, i32, [" + thenscope.numIds() +
 	   	  " x i32]}, align 4\n");
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine(thenscope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + thenscope.numIds() + 
 	   			  " x i32]}* " + thenscope.getMallocReg() + " to %eframe*\n");
		iffunc.add(currentLine);
		
		//set env link pointer
 	   	currentLine = new LLVMLine(this.thenenvptr + " = getelementptr %eframe* " + this.thenscope.getCurrentScope() + 
 	   			", i32 0, i32 0\n");
 	   iffunc.add(currentLine);
 	   
 	   	currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.thenenvptr + "\n");
 	   iffunc.add(currentLine);
		
		iffunc.addAll(this.fthen.compile(this.thenscope, funcdecs, fieldTable).getCode());
		
		currentLine = new LLVMLine("ret i32 " + this.fthen.getReg() + "\n");
		currentLine.setOperation("ret");
		currentLine.addRegisterUsed(this.fthen.getReg());
		iffunc.add(currentLine);
		
		//else
		currentLine = new LLVMLine(this.elselbl + ":\n");
		currentLine.setOperation("label");
		currentLine.setLabel(this.elselbl);
		iffunc.add(currentLine);
		
		//malloc env
		currentLine = new LLVMLine(elsescope.getMallocReg() + " = malloc {%eframe*, i32, [" + elsescope.numIds() +
	   	  " x i32]}, align 4\n");
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine(elsescope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + elsescope.numIds() + 
	   			  " x i32]}* " + elsescope.getMallocReg() + " to %eframe*\n");
		iffunc.add(currentLine);
		
		//set env link pointer
	   	currentLine = new LLVMLine(this.elseenvptr + " = getelementptr %eframe* " + this.elsescope.getCurrentScope() + 
	   			", i32 0, i32 0\n");
	   iffunc.add(currentLine);
	   
	   	currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.elseenvptr + "\n");
	   iffunc.add(currentLine);

		
		iffunc.addAll(this.felse.compile(env, funcdecs, fieldTable).getCode());
		currentLine = new LLVMLine("ret i32 " + this.felse.getReg() + "\n");
		currentLine.setOperation("ret");
		iffunc.add(currentLine);
		
		currentLine = new LLVMLine("}\n");
		iffunc.add(currentLine);
		

		funcdecs.addAll(iffunc);
		
		return this;
	}
}
