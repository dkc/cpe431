package Expressions.Objects;

import Values.*;
import Environment.Env;

public class PObjectExp implements FObjectExp{
	Env slots;
	
	public PObjectExp(Env slots){
		this.slots = slots;
	}
	
	public Value interp(Env env){
		return new PObject(slots);
	}
}
