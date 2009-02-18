package Expressions;

import Environment.Env;
import Environment.RegAndIndex;

public class VarMut extends AbstractCodeAndReg{
	String id;
	CodeAndReg newVal;
	String ptrreg;
	
	public VarMut(String id, CodeAndReg newVal,int regnum){
		super(regnum);
		this.id = id;
		this.newVal = newVal;
		this.ptrreg = "%ptrreg" + regnum;
	}
	
	public CodeAndReg compile(Env env) {
		//are we actually changin mem or just adding a new binding for same id?
		RegAndIndex regind = Env.lookup(this.id, env);
		//try {
			this.code.addAll(newVal.compile(env).getCode());
			//TODO load val to eframe
			if(regind != null){
				//this.code.add(this.eframereg + " = add i32 0," + regind.reg + "\n");
				this.code.addAll(regind.code);
				this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
						regind.reg + ", i32 2, i32 " + regind.index + "\n");
						this.code.add("store i32 " + newVal.getReg() + ", i32* " + 
								this.ptrreg + "\n");
				
			}
			
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
