package Expressions;


import Environment.Env;
public class IfExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg fthen;
	CodeAndReg felse;
	Env thenscope;
	Env elsescope;
	String testreg = "%tstreg";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
		this.testreg += regnum;
	}
	
	public void staticPass(Env env){
		this.thenscope = Env.addScope(new Env(), env);
		this.elsescope = Env.addScope(new Env(), env);
		this.test.staticPass(env);
		this.fthen.staticPass(this.thenscope);
		this.felse.staticPass(this.elsescope);
	}
	
	public CodeAndReg compile(Env env){
		//test
		this.test.compile(env);
		this.code.addAll(this.test.getCode());
		this.code.add(this.testreg + " = icmp eq i32 2, " + this.test.getReg() + "\n"); 
		this.code.add("br i1 " + this.testreg + ", label %then, label %else\n");
		
		//then
		this.fthen.compile(this.thenscope);
		this.code.add("then:\n");
		this.code.addAll(this.fthen.getCode());
		this.code.add("br label %end\n");
		
		//else
		this.felse.compile(this.elsescope);
		this.code.add("else:\n");
		this.code.addAll(this.felse.getCode());
		this.code.add("br label %end\n");
		
		//end
		this.code.add("end:\n");
		this.code.add(this.reg + " = phi i32 [" + 
			this.fthen.getReg() + ",%then], [" + this.felse.getReg() + ",%else]\n");
		
		return this;
	}
}
