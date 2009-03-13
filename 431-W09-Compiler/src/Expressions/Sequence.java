package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.Const.FVoid;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class Sequence extends AbstractCodeAndReg{

	public ArrayList<CodeAndReg> seq;
	
	public Sequence(ArrayList<CodeAndReg> seq, int regnum){
		super(regnum);
		this.seq = seq;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
		for(CodeAndReg reg : seq)
			reg.staticPass(env, funcids, stringdecs);
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){// throws ReturnException {

		if(seq.size() == 0){
			return new FVoid(this.regnum).compile(env, funcdecs, fieldTable);
		}else{
		for(CodeAndReg i : seq){
			this.code.addAll(i.compile(env, funcdecs, fieldTable).getCode());
		}
		
		//store val from last expr (wait why are we doing this)
		this.reg = seq.get(seq.size()-1).getReg();
		
		return this;
		}
	}
}
