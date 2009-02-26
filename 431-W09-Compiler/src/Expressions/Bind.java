package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;

public class Bind extends AbstractCodeAndReg{
	public String name;
	public CodeAndReg val;
	private String ptrreg = "%ptrreg";
	
	public Bind(String name, CodeAndReg val,int regnum){
		super(regnum);
		this.name = name;
		this.val = val;
		this.ptrreg += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		env.add(name);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		this.code.addAll(val.compile(env, funcdecs, fieldTable).getCode());
		
		//llvm load code into eframe
		
		RegAndIndex regind = Env.lookup(name, env);
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
		regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add("store i32 " + val.getReg() + ", i32* " + this.ptrreg + "\n");
		
		//return value
		this.code.add(this.reg + " = add i32 0, " + val.getReg() + "\n");
		
		return this;
	}
}
