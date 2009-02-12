package Expressions;
import java.util.ArrayList;

import Environment.Env;
import Values.*;

public class Sequence implements Expression{

	ArrayList<Expression> seq;
	
	public Sequence(ArrayList<Expression> seq){
		this.seq = seq;
	}
	
	public Value interp(Env env) throws ReturnException {
		if(seq.size() == 0){
			return new VVoid();
		}else{
		Value val = null;
		for(int i = 0;i < seq.size();i++){
			val = seq.get(i).interp(env);
		}
		return val;
		}
	}
}
