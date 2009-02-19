package Expressions;


import Environment.Env;
import Environment.RegAndIndex;

public class Bind extends AbstractCodeAndReg{
	public String name;
	public CodeAndReg val;
	//public CodeAndReg body;
	private String ptrreg = "%ptrreg";
	
	public Bind(String name, CodeAndReg val,int regnum){
		super(regnum);
		this.name = name;
		this.val = val;
		//this.body = body;
		this.ptrreg += regnum;
	}
	
	public void staticPass(Env env){
		env.add(name);
	}
	
	public CodeAndReg compile(Env env){
		this.code.addAll(val.compile(env).getCode());
		
		//llvm load code into eframe
		
		RegAndIndex regind = Env.lookup(name, env);
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
		regind.reg + ", i32 2, i32 " + regind.index + "\n");
		this.code.add("store i32 " + val.getReg() + ", i32* " + this.ptrreg + "\n");
		
		//return value
		this.code.add(this.reg + " = add i32 0, " + val.getReg() + "\n");
		
		//this.code.addAll(body.compile(env).getCode());
		return this;
	}
}
