package Expressions;

import Environment.Env;

public class VarMut extends AbstractCodeAndReg{
	String id;
	CodeAndReg newVal;
	
	public VarMut(String id, CodeAndReg newVal,int regnum){
		super(regnum);
		this.id = id;
		this.newVal = newVal;
	}
	
	public CodeAndReg compile(Env env) {
		//are we actually changin mem or just adding a new binding for same id?
		int index = Env.lookup(this.id, env);
		//try {
			this.code.addAll(newVal.compile(env).getCode());
			//TODO load val to eframe
			
			
			//store to ret reg
			this.code.add(this.reg + " = add i32 0, " + newVal.getReg() + "\n");
			
			return this;
		/*} catch (ReturnException e) {
			System.err.println("return in varmut; exiting");
			System.exit(1);
			return null;
		}*/
	}
}
