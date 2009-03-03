package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import LLVMObjects.LLVMLine;

public interface CodeAndReg {
	public String getReg();
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs);
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable);
	public ArrayList<LLVMLine> getCode();
}
