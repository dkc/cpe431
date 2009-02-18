package Expressions;


import Environment.Env;

public class FReturn extends AbstractCodeAndReg {
	CodeAndReg target;
	
	
	public FReturn(CodeAndReg target,int regnum){
		super(regnum);
		this.target = target;
	}
	
	@Override
	public CodeAndReg compile(Env env){// throws ReturnException {
		//Value retVal;
	
		//try {
			this.code.addAll(target.compile(env).getCode());
			this.code.add(this.reg + " = add i32 0, " + target.getReg() + "\n");
		//} catch (ReturnException e) { /* we've caught another ReturnException, which should only be thrown from a return */
			/*System.err.println("Encountered a return inside of a return--exiting...");
			System.exit(1);
			return null;
		}*/
		
		//throw new ReturnException(target);
		return this;
	}

}
