package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Values.*;

public class MethodCall extends AbstractCodeAndReg {
	CodeAndReg obj;
	String mname;
	ArrayList<CodeAndReg> args;
	
	public MethodCall(CodeAndReg obj, String mname, ArrayList<CodeAndReg> args, int regnum){
		super(regnum);
		this.obj = obj;
		this.mname = mname;
		this.args = args;
	}
	
	@Override
	public CodeAndReg compile(Env env){
		/* disemboweled this, the way we deal with method calls in the compiler is completely
		 * different from the Interpreter's strategy */
		return null;
	}
}