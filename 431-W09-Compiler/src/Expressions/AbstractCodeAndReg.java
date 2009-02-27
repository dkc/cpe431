package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;


public abstract class AbstractCodeAndReg implements CodeAndReg{
	protected ArrayList<LLVMLine> code;
	protected String reg;
	protected int regnum;
	
	public AbstractCodeAndReg(int regnum){
		this.regnum = regnum;
		this.reg = "%r" + regnum;
		this.code = new ArrayList<LLVMLine>();
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){}
	
	public int getRegnum(){
		return this.regnum;
	}
	
	public String getReg(){
		return this.reg;
	}
	
	public ArrayList<LLVMLine> getCode(){
		return this.code;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		return this;
	}
}
