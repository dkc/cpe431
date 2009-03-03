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
	public CodeAndReg mbody;
	public Env fscope;
	public Env mscope;
	public int mregnum;
	private String mallocreg = "%fmalreg";
	private String eframereg = "%efreg";
	private String typereg = "%typeptr";
	private String memreg = "%memaddreg";
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	private String envlinkptr = "%envlkptr";
	private String mmallocreg = "%mfmalreg";
	private String meframereg = "%mefreg";
	private String mtypereg = "%mtypeptr";
	private String mmemreg = "%mmemaddreg";
	private String mptrreg = "%mptrreg";
	private String mobjreg = "%mobjreg";
	private String menvlinkptr = "%menvlkptr";
	private String mreg = "%mreg";
	private int methodid = 0;
	private int functionid = 0;
	
	public FuncDec(String name, ArrayList<String> params, CodeAndReg body, int regnum){// CodeAndReg mbody, int regnum, int mregnum){
		super(regnum);
		//this.mregnum = mregnum;
		this.name = name;
		this.params = params;
		this.body = body;
		//this.mbody = mbody;
		this.mallocreg += regnum;
		this.eframereg += regnum;
		this.typereg += regnum;
		this.memreg += regnum;
		this.ptrreg += regnum;
		this.objreg += regnum;
		this.envlinkptr += regnum;
		this.mmallocreg += regnum;
		this.meframereg += regnum;
		this.mtypereg += regnum;
		this.mmemreg += regnum;
		this.mptrreg += regnum;
		this.mobjreg += regnum;
		this.menvlinkptr += regnum;
		this.mreg += regnum;
	}
	
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs){
		//env.add(this.name);
		this.functionid = funcid++;
		//this.methodid = funcid++;
		this.fscope = new Env(this.regnum);
		//this.mscope = new Env(this.mregnum);
		Env.addScope(this.fscope, env);
		//Env.addScope(this.mscope, env);
		for(String par: params){
			this.fscope.add(par);
			//this.mscope.add(par);
		}
		this.fscope.add("this");//this at end
		this.body.staticPass(this.fscope, funcid, stringdecs);
		//this.mbody.staticPass(this.mscope, funcid);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//build closure obj
		currentLine = new LLVMLine(this.mallocreg + " = malloc %cobj, align 4\n");
		
		//closure env
		//TODO added slot at end for this if needed, kinda hack
		currentLine = new LLVMLine(fscope.getMallocReg() + " = malloc {%eframe*, i32, [" + fscope.numIds() +
 	   	  " x i32]}, align 4\n");
 	   	currentLine = new LLVMLine(fscope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + fscope.numIds() + 
 	   			  " x i32]}* " + fscope.getMallocReg() + " to %eframe*\n");
 	   	//set env link pointer
 	   	currentLine = new LLVMLine(this.envlinkptr + " = getelementptr %eframe* + " + this.fscope.getCurrentScope() + 
 	   			", i32 0, i32 0\n");
 	   currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.envlinkptr + "\n");
 	   	
		//store func num
		currentLine = new LLVMLine(this.typereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 0\n");
		currentLine = new LLVMLine("store i32 " + ((this.functionid << 2) + 1) + ", i32* " + this.typereg + "\n");
 	   	//1 is tag right now
		
		//store env ptr
		currentLine = new LLVMLine(this.eframereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 2\n");
		currentLine = new LLVMLine("store %eframe* " + this.fscope.getCurrentScope() + ", %eframe** " + this.eframereg + "\n");
		
		//setup pointer to func obj
		RegAndIndex regind = Env.lookup(this.name, env);
		currentLine = new LLVMLine(this.objreg + " = ptrtoint %cobj* " + this.mallocreg + " to i32\n");
		currentLine = new LLVMLine(this.memreg + " = shl i32 " + this.objreg + ", 2\n");
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.memreg + "\n");//add 1 for tag to pointer
		
		//store pointer to env
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine = new LLVMLine("store i32 " + this.reg
				+ ", i32* " + this.ptrreg + "\n");
		
		
		
		/*
		//build method closure obj
		this.code.add(this.mmallocreg + " = malloc %cobj, align 4\n");
		//closure env
		//TODO added slot at end for this if needed, kinda hack
		this.code.add(mscope.getMallocReg() + " = malloc {%eframe*, i32, [" + mscope.ids.size() +
 	   	  " x i32]}, align 4\n");
 	   	this.code.add(mscope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + mscope.ids.size() + 
 	   			  " x i32]}* " + mscope.getMallocReg() + " to %eframe*\n");
 	   	//set env link pointer
 	   	this.code.add(this.menvlinkptr + " = getelementptr %eframe* + " + this.mscope.getCurrentScope() + 
 	   			", i32 0, i32 0\n");
 	   this.code.add("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.menvlinkptr + "\n");
 	   	
		//store func num
		this.code.add(this.mtypereg + " = getelementptr %cobj* " + 
				this.mmallocreg + ", i32 0, i32 0\n");
		this.code.add("store i32 " + ((this.methodid << 2) + 1) + ", i32* " + this.mtypereg + "\n");
 	   	//1 is tag right now
		
		//store env ptr
		this.code.add(this.meframereg + " = getelementptr %cobj* " + 
				this.mmallocreg + ", i32 0, i32 2\n");
		this.code.add("store %eframe* " + this.mscope.getCurrentScope() + ", %eframe** " + this.meframereg + "\n");
		
		
		//setup pointer to method obj
		RegAndIndex mregind = Env.lookup(this.name + "met", env);
		this.code.add(this.mobjreg + " = ptrtoint %cobj* " + this.mmallocreg + " to i32\n");
		this.code.add(this.mmemreg + " = shl i32 " + this.mobjreg + ", 2\n");
		this.code.add(this.mreg + " = add i32 1, " + this.mmemreg + "\n");//add 1 for tag to pointer
		
		//store pointer to env
		this.code.add(this.mptrreg + " = getelementptr %eframe* " + 
				mregind.reg + ", i32 0, i32 2, i32 " + mregind.index + "\n");
		this.code.add("store i32 " + this.mreg
				+ ", i32* " + this.mptrreg + "\n");
		
		
		*/
		
		//write func dec?
		funcdecs.add("define i32 @footle_fun" + this.functionid + "(%eframe* " + this.fscope.getCurrentScope() + "){\n");
		int savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		ArrayList<LLVMLine> llvmbody = this.body.compile(this.fscope, funcdecs, fieldTable).getCode();
		ArrayList<String> body = new ArrayList<String>();
		for(LLVMLine l: llvmbody){
			body.add(l.getCode());
		}
		body.add("ret i32 10\n");
		body.add("}\n");
		funcdecs.addAll(savedindex,body);
		
		//TODO define method
		//funcdecs.add("define i32 @footle_fun" + this.methodid + "(%eframe* " + this.mscope.getCurrentScope() + "){\n");
		//savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		//ArrayList<String> mbody = this.mbody.compile(this.mscope, funcdecs, fieldTable).getCode();
		//body.add("ret i32 10\n");
		//mbody.add("}\n");
		//funcdecs.addAll(body);
		
		return this;
	}
}
