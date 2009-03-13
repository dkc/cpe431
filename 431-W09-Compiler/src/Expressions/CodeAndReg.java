package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.FuncIDandParams;
import LLVMObjects.LLVMLine;

public interface CodeAndReg {
	public String getReg();
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs);
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable);
	public ArrayList<LLVMLine> getCode();
}
