package Expressions;
import java.util.ArrayList;

import Environment.Env;
import Expressions.Const.FVoid;

public class Sequence extends AbstractCodeAndReg{

	ArrayList<CodeAndReg> seq;
	
	public Sequence(ArrayList<CodeAndReg> seq, int regnum){
		super(regnum);
		this.seq = seq;
	}
	
	public CodeAndReg compile(Env env){// throws ReturnException {
		if(seq.size() == 0){
			//TODO reg number ?
			return new FVoid(-1).compile(env);
		}else{
		//CodeAndReg val = null;
		int i;
		for(i = 0;i < seq.size();i++){
			this.code.addAll(seq.get(i).compile(env).getCode());
		}
		//store val from last expr
		this.code.add(this.reg + " = add i32 0, " + seq.get(i).getReg() + "\n");
		
		return this;
		}
	}
}
