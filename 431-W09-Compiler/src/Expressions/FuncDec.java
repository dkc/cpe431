package Expressions;

import java.util.ArrayList;
import Environment.Env;

public class FuncDec extends AbstractCodeAndReg{
	public String name;
	public ArrayList<String> params;
	public CodeAndReg body;
	public Env env;
	
	public FuncDec(String name, ArrayList<String> params, CodeAndReg body,int regnum){
		super(regnum);
		this.name = name;
		this.params = params;
		this.body = body;
	}
	
	public void staticPass(Env env){
		this.env = env;
		//TODO regnum
		Env newScope = new Env();
		this.env = Env.addScope(env, newScope);
	}
}
