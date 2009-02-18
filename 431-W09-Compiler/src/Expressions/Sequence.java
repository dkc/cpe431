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
			CodeAndReg fvoid = new FVoid(this.regnum).compile(env);
			this.code.addAll(fvoid.getCode());
			return fvoid;
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
