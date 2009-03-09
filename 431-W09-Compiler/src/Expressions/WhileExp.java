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
	private String retreg = "%retreg";
	private String envlinkptr = "%envlinkptr";
	
	public WhileExp(CodeAndReg test, CodeAndReg body, int regnum){
		super(regnum);
		this.test = test;
		this.body = body;
		testreg += regnum;
		this.retreg += regnum;
		this.envlinkptr += regnum;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		ArrayList<LLVMLine> whilefunc = new ArrayList<LLVMLine>();
		LLVMLine currentLine;
		//call while func with env
		
		//call while
		currentLine = new LLVMLine("call i32 @while" + this.regnum + "( %eframe* " + env.getCurrentScope() + ")\n");
		currentLine.setOperation("call");
		currentLine.setLabel("while" + this.regnum);
		currentLine.addRegisterUsed(env.getCurrentScope());
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, 10\n"); // ret void
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(10);
		this.code.add(currentLine);
		
		//func def
		currentLine = new LLVMLine("define i32 @while" + regnum + "( %eframe* " + env.getCurrentScope() + " ){\n");
		currentLine.setOperation("fundec");
		currentLine.setLabel("while" + regnum);
		currentLine.addRegisterUsed(env.getCurrentScope());
		whilefunc.add(currentLine);
		
		whilefunc.addAll(this.test.compile(env, funcdecs, fieldTable).getCode());
		
		//compile and branch on test
		currentLine = new LLVMLine(this.testreg + " = icmp eq i32 6, " + this.test.getReg() + "\n");
		currentLine.setOperation("icmp eq");
		currentLine.setRegisterDefined(this.testreg);
		currentLine.addConstantUsed(6);
		currentLine.addRegisterUsed(this.test.getReg());
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("br i1 " + this.testreg + ", label %cont, label %fin\n");
		currentLine.setOperation("br i1");
		currentLine.addRegisterUsed(this.testreg);
		currentLine.addRegisterUsed("%cont");
		currentLine.addRegisterUsed("%fin");
		whilefunc.add(currentLine);
		
		//continue label
		currentLine = new LLVMLine("cont:\n");
		currentLine.setOperation("label");
		currentLine.setLabel("cont");
		whilefunc.add(currentLine);
		
		//setup env 
		currentLine = new LLVMLine(scope.getMallocReg() + " = malloc {%eframe*, i32, [" + scope.numIds() +
 	   	  " x i32]}, align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.scope.getMallocReg());
		currentLine.addConstantUsed(4 + 4 + 4 * scope.numIds());
		whilefunc.add(currentLine);
		
 	   	currentLine = new LLVMLine(scope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + scope.numIds() + 
 	   			  " x i32]}* " + scope.getMallocReg() + " to %eframe*\n");
 	   	currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(scope.getCurrentScope());
		currentLine.addRegisterUsed(this.scope.getMallocReg());
		whilefunc.add(currentLine);
 	   	
 	   	//set env link pointer
 	   	currentLine = new LLVMLine(this.envlinkptr + " = getelementptr %eframe* " + this.scope.getCurrentScope() + 
 	   			", i32 0, i32 0\n");
 	   	currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.envlinkptr);
		currentLine.addRegisterUsed(this.scope.getCurrentScope());
		whilefunc.add(currentLine);
 	   	
 	   currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.envlinkptr + "\n");
 	   currentLine.setOperation("store");
 	   currentLine.addRegisterUsed(env.getCurrentScope());
		currentLine.addRegisterUsed(this.envlinkptr);
		whilefunc.add(currentLine);
		
		//run body after env creation
		whilefunc.addAll(this.body.compile(this.scope, funcdecs, fieldTable).getCode());
		
		//recursive call
		currentLine = new LLVMLine(this.retreg + " = call i32 @while" + this.regnum + "( %eframe* " + env.getCurrentScope() + ")\n");
		currentLine.setOperation("call");
		currentLine.setLabel("while" + this.regnum);
		currentLine.setRegisterDefined(this.retreg);
		currentLine.addRegisterUsed(env.getCurrentScope());
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("ret i32 " + this.retreg + "\n");
		currentLine.setOperation("ret");
		currentLine.addRegisterUsed(this.retreg);
		whilefunc.add(currentLine);
		
		//if false return with call val in this.reg
		currentLine = new LLVMLine("fin:\n");
		currentLine.setOperation("label");
		currentLine.setLabel("fin");
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("ret i32 10\n");
		currentLine.setOperation("ret");
		currentLine.addConstantUsed(10);
		whilefunc.add(currentLine);
		
		currentLine = new LLVMLine("}\n");
		currentLine.setOperation("endfunc");
		whilefunc.add(currentLine);
		funcdecs.addAll(whilefunc);
		
		return this;
	}


	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs) {
		// TODO Auto-generated method stub
		this.scope = new Env(this.regnum);
		Env.addScope(this.scope, env);
		this.test.staticPass(this.scope, funcids, stringdecs);
		this.body.staticPass(this.scope, funcids, stringdecs);
	}
}
