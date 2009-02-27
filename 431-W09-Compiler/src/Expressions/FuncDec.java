package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;

public class FuncDec extends AbstractCodeAndReg{
	public String name;
	public ArrayList<String> params;
	public CodeAndReg body;
	public Env scope;
	private String mallocreg = "%fmalreg";
	private String eframereg = "%efreg";
	private String typereg = "%typeptr";
	private String memreg = "%memaddreg";
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	
	public FuncDec(String name, ArrayList<String> params, CodeAndReg body,int regnum){
		super(regnum);
		this.name = name;
		this.params = params;
		this.body = body;
		this.mallocreg += regnum;
		this.eframereg += regnum;
		this.typereg += regnum;
		this.memreg += regnum;
		this.ptrreg += regnum;
		this.objreg += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		env.add(this.name);
		funcids.add(this.regnum);
		this.scope = new Env(this.regnum);
		Env.addScope(this.scope, env);
		for(String par: params){
			this.scope.add(par);
		}
		this.body.staticPass(this.scope, null);
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//build closure obj
		currentLine = new LLVMLine(this.mallocreg + " = malloc %cobj, align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.mallocreg);
		this.code.add(currentLine);
		
		//closure env
		currentLine = new LLVMLine(scope.getMallocReg() + " = malloc {%eframe*, i32, [" + scope.numIds() + " x i32]}, align 4\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(scope.getMallocReg());
		this.code.add(currentLine);
		
 	   	currentLine = new LLVMLine(scope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + scope.numIds() + " x i32]}* " + scope.getMallocReg() + " to %eframe*\n");
		currentLine.setOperation("bitcast");
		currentLine.setRegisterDefined(scope.getCurrentScope());
		currentLine.addRegisterUsed(scope.getMallocReg());
		this.code.add(currentLine);
		
		//store func num
		currentLine = new LLVMLine(this.typereg + " = getelementptr %cobj* " + this.mallocreg + ", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.typereg);
		currentLine.addRegisterUsed(this.mallocreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + ((this.regnum << 2) + 1) + ", i32* " + this.typereg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.typereg);
		this.code.add(currentLine);
		
 	   	//1 is tag right now
		
		//store env ptr
		currentLine = new LLVMLine(this.eframereg + " = getelementptr %cobj* " + this.mallocreg + ", i32 0, i32 2\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.eframereg);
		currentLine.addRegisterUsed(this.mallocreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store %eframe* " + this.scope.getCurrentScope() + ", %eframe** " + this.eframereg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.scope.getCurrentScope());
		currentLine.addRegisterUsed(this.eframereg);
		this.code.add(currentLine);
		
		//setup pointer to obj
		RegAndIndex regind = Env.lookup(this.name, env);
		
		currentLine = new LLVMLine(this.objreg + " = ptrtoint %cobj* " + this.mallocreg + " to i32\n");
		currentLine.setOperation("ptrtoint");
		currentLine.setRegisterDefined(this.objreg);
		currentLine.addRegisterUsed(this.mallocreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.memreg + " = shl i32 " + this.objreg + ", 2\n");
		currentLine.setOperation("shl");
		currentLine.setRegisterDefined(this.memreg);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.memreg + "\n"); //add 1 for tag
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.memreg);
		this.code.add(currentLine);
		
		//store pointer to env
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + this.reg + ", i32* " + this.ptrreg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.reg);
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		//write func dec?
		funcdecs.add("define i32 @footle_fun" + this.regnum + "(%eframe* " + this.scope.getCurrentScope() + "){\n");
		
		//TODO can't handle func dec in func body, save index, use insert? done?
		ArrayList<LLVMLine> body = this.body.compile(this.scope, funcdecs, fieldTable).getCode();
		body.add(new LLVMLine("}\n"));
		funcdecs.addAll(body);
		//funcdecs.add("ret i32 " + this.reg + "\n");
		
		return this;
	}
}
