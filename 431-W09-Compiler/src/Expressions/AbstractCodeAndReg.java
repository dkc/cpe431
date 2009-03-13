package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.FuncIDandParams;
import LLVMObjects.LLVMLine;


public abstract class AbstractCodeAndReg implements CodeAndReg{
	protected ArrayList<LLVMLine> code;
	protected String reg;
	protected int regnum;
	
	protected static int res_len = 15;
	protected  static String[] reserved_names = {
										"int?",
										"float?",
										"bool?",
										"void?",
										"string?",
										"closure?",
										"plain?",
										"print",
										"stringLength",
										"subString",
										"stringEqual?",
										"stringLessThan?",
										"readLine",
										"instanceof",
										"this"};
	
	
	public AbstractCodeAndReg(int regnum){
		this.regnum = regnum;
		this.reg = "%r" + regnum;
		this.code = new ArrayList<LLVMLine>();
	}
	
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){}
	
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
