package Expressions;
import java.util.ArrayList;

import Environment.Env;
import Expressions.Const.FVoid;

public class Sequence extends AbstractCodeAndReg{

	public ArrayList<CodeAndReg> seq;
	
	public Sequence(ArrayList<CodeAndReg> seq, int regnum){
		super(regnum);
		this.seq = seq;
	}
	
	public void staticPass(Env env){
		for(CodeAndReg reg : seq)
			reg.staticPass(env);
	}
	
	public CodeAndReg compile(Env env){// throws ReturnException {
		if(seq.size() == 0){
			CodeAndReg fvoid = new FVoid(this.regnum).compile(env);
			this.code.addAll(fvoid.getCode());
			return fvoid;
		}else{
		//CodeAndReg val = null;
		String lastReg = "0"; // need the void indicator here, actually
		for(CodeAndReg i : seq){
			this.code.addAll(i.compile(env).getCode());
			lastReg = i.getReg();
		}
		//store val from last expr (wait why are we doing this)
		this.code.add(this.reg + " = add i32 0, " + lastReg + "\n");
		
		return this;
		}
	}
}
