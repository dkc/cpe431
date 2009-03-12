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
	private String slotsptr = "%slotsptr";
	
	public FuncDec(String name, ArrayList<String> params, CodeAndReg body, int regnum){// CodeAndReg mbody, int regnum, int mregnum){
		super(regnum);
		//System.out.println("in funcdec");
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
		this.slotsptr += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs){

		this.functionid = funcids.size();
		funcids.add(this.functionid);

		this.fscope = new Env(this.regnum);

		Env.addScope(this.fscope, env);

		//System.out.println("size of params: " + params.size());
		for(String par: params){
			this.fscope.add(par);
		}
		//this.fscope.add("this");//this at end
		this.body.staticPass(this.fscope, funcids, stringdecs);
		//this.mbody.staticPass(this.mscope, funcid);
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//build closure obj
		currentLine = new LLVMLine(this.mallocreg + " = malloc %cobj, align 4\n");
		this.code.add(currentLine);
		
		//closure env
		currentLine = new LLVMLine(fscope.getMallocReg() + " = malloc {%eframe*, i32, [" + fscope.numIds() +
 	   	  " x i32]}, align 4\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(fscope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + fscope.numIds() + 
 	   			  " x i32]}* " + fscope.getMallocReg() + " to %eframe*\n");
		this.code.add(currentLine);
		
		//set env link pointer
 	   	currentLine = new LLVMLine(this.envlinkptr + " = getelementptr %eframe* " + this.fscope.getCurrentScope() + 
 	   			", i32 0, i32 0\n");
 	   this.code.add(currentLine);
 	   
 	   	currentLine = new LLVMLine("store %eframe* " + env.getCurrentScope() + ", %eframe** " + this.envlinkptr + "\n");
 	   this.code.add(currentLine);
 	   	
		//store func num
		currentLine = new LLVMLine(this.typereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 0\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + ((this.functionid << 2) + 1) + ", i32* " + this.typereg + "\n");
		this.code.add(currentLine);
		
		//1 is tag right now
		
		//store env ptr to cobj
		currentLine = new LLVMLine(this.eframereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 2\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store %eframe* " + this.fscope.getCurrentScope() + ", %eframe** " + this.eframereg + "\n");
		this.code.add(currentLine);
		
		// TODO store empty slots
		currentLine = new LLVMLine(this.slotsptr + " = getelementptr %cobj* "
				+ this.mallocreg + ", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.slotsptr);
		currentLine.addRegisterUsed(this.mallocreg);
		this.code.add(currentLine);

		currentLine = new LLVMLine("store %slots* @empty_slots, %slots** "
				+ this.slotsptr + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.slotsptr);
		// TODO fix later
		this.code.add(currentLine);
		
		//setup pointer to func obj
		RegAndIndex regind = Env.lookup(this.name, env, this.regnum);
		this.code.addAll(regind.code);
		
		currentLine = new LLVMLine(this.objreg + " = ptrtoint %cobj* " + this.mallocreg + " to i32\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.memreg + " = shl i32 " + this.objreg + ", 2\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.memreg + "\n");//add 1 for tag to pointer
		this.code.add(currentLine);
		
		//store pointer to env
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + this.reg
				+ ", i32* " + this.ptrreg + "\n");
		this.code.add(currentLine);
		
		//write func dec?
		currentLine = new LLVMLine("define i32 @footle_fun" + this.functionid + "(%eframe* " + this.fscope.getCurrentScope() + "){\n");
		funcdecs.add(currentLine);
		int savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		ArrayList<LLVMLine> body = this.body.compile(this.fscope, funcdecs, fieldTable).getCode();
		currentLine = new LLVMLine("ret i32 10\n");
		body.add(currentLine);
		body.add(new LLVMLine("}\n"));
		funcdecs.addAll(savedindex,body);
		
		//define method
		currentLine = new LLVMLine("define i32 @footle_met" + this.functionid + "(%eframe* " + this.fscope.getCurrentScope() + ", i32 %this){\n");
		funcdecs.add(currentLine);
		//savedindex = funcdecs.size();
		// can't handle func dec in func body, save index, use insert? done?
		//ArrayList<String> mbody = this.mbody.compile(this.mscope, funcdecs, fieldTable).getCode();
		//body.add("ret i32 10\n");
		//mbody.add("}\n");
		funcdecs.addAll(body);
		
		return this;
	}
}
