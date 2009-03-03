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
	
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs){
		//this.thenscope = Env.addScope(new Env(), env);
		//this.elsescope = Env.addScope(new Env(), env);
		this.test.staticPass(env, funcid, stringdecs);
		//this.fthen.staticPass(this.thenscope);
		//this.felse.staticPass(this.elsescope);
		this.fthen.staticPass(env, funcid, stringdecs);
		this.felse.staticPass(env, funcid, stringdecs);
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){

		LLVMLine currentLine;
		
		//test
		this.test.compile(env, funcdecs, fieldTable);
		this.code.addAll(this.test.getCode());
		
		//TODO type check boolean?
		
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 2, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.test.getReg());
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %then, label %else\n");
		currentLine.setOperation("br");
		currentLine.addRegisterUsed(this.testreg);
		this.code.add(currentLine);
		
		//then
		//this.fthen.compile(this.thenscope);
		this.fthen.compile(env, funcdecs, fieldTable);
		
		currentLine = new LLVMLine("then:\n");
		currentLine.setOperation("label");
		this.code.add(currentLine);
		
		this.code.addAll(this.fthen.getCode());
		
		currentLine = new LLVMLine("br label %end\n");
		currentLine.setOperation("br");
		this.code.add(currentLine);
		
		//else
		//this.felse.compile(this.elsescope);
		this.felse.compile(env, funcdecs, fieldTable);
		currentLine = new LLVMLine("else:\n");
		currentLine.setOperation("label");
		this.code.add(currentLine);
		
		this.code.addAll(this.felse.getCode());
		
		currentLine = new LLVMLine("br label %end\n");
		currentLine.setOperation("br");
		this.code.add(currentLine);
		
		//end
		currentLine = new LLVMLine("end:\n");
		currentLine.setOperation("label");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = phi i32 [" + this.fthen.getReg() + ",%then], [" + this.felse.getReg() + ",%else]\n");
		currentLine.setOperation("phi");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.fthen.getReg());
		currentLine.addRegisterUsed(this.felse.getReg());
		this.code.add(currentLine);
		
		//add fundef then
		/*funcdecs.add("define i32 @if_then_fun" + this.regnum + "(%eframe* " + env.getCurrentScope() + "){\n");
		int savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		ArrayList<String> body = this.fthen.compile(env, funcdecs, fieldTable).getCode();
		body.add("}\n");
		funcdecs.addAll(savedindex,body);
		
		//add fundef else
		funcdecs.add("define i32 @if_else_fun" + this.regnum + "(%eframe* " + env.getCurrentScope() + "){\n");
		savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		body = this.felse.compile(env, funcdecs, fieldTable).getCode();
		body.add("}\n");
		funcdecs.addAll(savedindex,body);
		*/
		return this;
	}
}
