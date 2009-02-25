package Expressions;

import java.util.ArrayList;

import Environment.Env;
import LLVMObjects.LLVMLine;

public interface CodeAndReg {
	public String getReg();
	public ArrayList<LLVMLine> getCode();
	public void staticPass(Env env);
	public CodeAndReg compile(Env env);
}
