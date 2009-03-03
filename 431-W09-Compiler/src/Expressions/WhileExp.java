package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;
import Values.*;

public class WhileExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg body;
	Env scope;
	
	private String testreg = "%testreg";
	private String scopereg = "%tmpscopereg";
	private String retreg = "%retreg";
	private String envlinkptr = "%envlinkptr";
	
	public WhileExp(CodeAndReg test, CodeAndReg body, int regnum){
		super(regnum);
		this.test = test;
		this.body = body;
		testreg += regnum;
		this.scopereg += regnum;
		this.retreg += regnum;
		this.envlinkptr += regnum;
	}
	
	
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		//call while func with env
		
		
		//define while func
		ArrayList<LLVMLine> whilefunc = new ArrayList<LLVMLine>();
		currentLine = new LLVMLine("define @while"  + "( %eframe " + this.scope.getCurrentScope() + " ){\n");
		currentLine.setOperation("fundec");
		whilefunc.add(currentLine);
		
		
		whilefunc.addAll(this.test.compile(this.scope, funcdecs, fieldTable).getCode());
		
		//type check boolean?
		//setup env and func call
		//closure env
		currentLine = new LLVMLine(scope.getMallocReg() + " = malloc {%eframe*, i32, [" + scope.numIds() +
 	   	  " x i32]}, align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.scope.getMallocReg());
		whilefunc.add(currentLine);
		
 	   	currentLine = new LLVMLine(this.scopereg + " = bitcast {%eframe*, i32, [" + scope.numIds() + 
 	   			  " x i32]}* " + scope.getMallocReg() + " to %eframe*\n");
 	   currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(this.scopereg);
		currentLine.addRegisterUsed(this.scope.getMallocReg());
		whilefunc.add(currentLine);
 	   	
 	   	//set env link pointer
 	   	currentLine = new LLVMLine(this.envlinkptr + " = getelementptr %eframe* + " + this.scopereg + 
 	   			", i32 0, i32 0\n");
 	   currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.scopereg);
		whilefunc.add(currentLine);
 	   	
 	   currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.envlinkptr + "\n");
 	  currentLine.setOperation("store");
 	 currentLine.addRegisterUsed(env.getCurrentScope());
		currentLine.addRegisterUsed(this.envlinkptr);
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("call @while" + "( %eframe* " + this.scopereg + ")\n");
		currentLine.setOperation("call");
		currentLine.addRegisterUsed(this.scopereg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, 10\n"); // ret void
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		this.code.add(currentLine);
		
		//compile and branch on test
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 2, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.test.getReg());
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %cont, label %fin\n");
		currentLine.setOperation("br");
		currentLine.addRegisterUsed(this.testreg);
		whilefunc.add(currentLine);
		
		//run body
		currentLine = new LLVMLine("cont:\n");
		currentLine.setOperation("lablel");
		whilefunc.add(currentLine);
		
		whilefunc.addAll(this.body.compile(this.scope, funcdecs, fieldTable).getCode());
			
		//setup env and func call
		//closure env
		currentLine = new LLVMLine(scope.getMallocReg() + " = malloc {%eframe*, i32, [" + scope.numIds() +
 	   	  " x i32]}, align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.scope.getMallocReg());
		whilefunc.add(currentLine);
		
 	   	currentLine = new LLVMLine(this.scopereg + " = bitcast {%eframe*, i32, [" + scope.numIds() + 
 	   			  " x i32]}* " + scope.getMallocReg() + " to %eframe*\n");
 	   currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(this.scopereg);
		currentLine.addRegisterUsed(this.scope.getMallocReg());
		whilefunc.add(currentLine);
 	   	
 	   	//set env link pointer
 	   	currentLine = new LLVMLine(this.envlinkptr + " = getelementptr %eframe* + " + this.scopereg + 
 	   			", i32 0, i32 0\n");
 	   currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addRegisterUsed(this.scopereg);
		whilefunc.add(currentLine);
 	   	
 	   currentLine = new LLVMLine("store %eframe* " + this.scope.getCurrentScope() + ", %eframe** " + this.envlinkptr + "\n");
 	  currentLine.setOperation("store");
 	 currentLine.addRegisterUsed(this.scope.getCurrentScope());
		currentLine.addRegisterUsed(this.envlinkptr);
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine(this.retreg + " = call @while" + "( %eframe* " + this.scopereg + ")\n");
		currentLine.setOperation("call");
		currentLine.setRegisterDefined(this.retreg);
		currentLine.addRegisterUsed(this.scopereg);
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("ret i32 " + this.retreg + "\n");
		currentLine.setOperation("ret");
		currentLine.addRegisterUsed(this.retreg);
		whilefunc.add(currentLine);
		
		//if false return with call val in this.reg
		currentLine = new LLVMLine("fin:");
		currentLine.setOperation("label");
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("ret i32 0\n");
		currentLine.setOperation("ret");
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("}\n");
		currentLine.setOperation("endfunc");
		whilefunc.add(currentLine);
		
		
		return this;
	}



	@Override
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs) {
		// TODO Auto-generated method stub
		this.scope = new Env(this.regnum);
		Env.addScope(this.scope, env);
		this.test.staticPass(this.scope, funcid, stringdecs);
		this.body.staticPass(this.scope, funcid, stringdecs);
	}
}
