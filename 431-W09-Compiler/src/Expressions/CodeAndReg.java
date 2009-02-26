package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;

public interface CodeAndReg {
	//public String reg = "%r";
	//public ArrayList<String> code = new ArrayList<String>();
	
	public String getReg();
	public ArrayList<String> getCode();
	public void staticPass(Env env, ArrayList<Integer> funcids);
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable);
}
