package Expressions;

import java.util.ArrayList;

import Environment.Env;


public abstract class AbstractCodeAndReg implements CodeAndReg{
	protected ArrayList<String> code;
	protected String reg;
	
	public AbstractCodeAndReg(int regnum){
		this.reg = "%r" + regnum;
		this.code = new ArrayList<String>();
	}
	
	public void staticPass(Env env){}
	
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
	
	public CodeAndReg compile(Env env){
		return this;
	}
}
