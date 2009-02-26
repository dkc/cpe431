package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;


public abstract class AbstractCodeAndReg implements CodeAndReg{
	protected ArrayList<String> code;
	protected String reg;
	protected int regnum;
	
	public AbstractCodeAndReg(int regnum){
		this.regnum = regnum;
		this.reg = "%r" + regnum;
		this.code = new ArrayList<String>();
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){}
	
	public int getRegnum(){
		return this.regnum;
	}
	
	public String getReg(){
		return this.reg;
	}
	
	public ArrayList<String> getCode(){
		/*String retcode = "";
		for(String line : code){
			retcode += line;
		}*/
		return this.code;
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		return this;
	}
}
