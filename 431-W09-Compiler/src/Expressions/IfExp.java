package Expressions;


import Environment.Env;
public class IfExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg fthen;
	CodeAndReg felse;
	Env thenscope;
	Env elsescope;
	
	String testcode = "%tst = icmp eq i32 0, ";
	String branchcode = "br i1 %tst, label %then, label %else\n";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
	}
	
	public void staticPass(Env env){
		Env newThenScope = Env.addScope(new Env(-1), env);
		this.thenscope = newThenScope;
		Env newElseScope = Env.addScope(new Env(-1), env);
		this.elsescope = newElseScope;
		this.test.staticPass(env);
		this.fthen.staticPass(this.thenscope);
		this.felse.staticPass(this.elsescope);
	}
	
	public CodeAndReg compile(Env env){
		//test
		this.test.compile(env);
		this.code.addAll(this.test.getCode());
		this.code.add(testcode + this.test.getReg() + "\n"); 
		this.code.add(this.branchcode);
		
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
