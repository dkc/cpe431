package CodeAndRegs;

import Values.*;

import Environment.Env;
public class IfExp extends AbstractCodeAndReg{
	CodeAndReg test;
	CodeAndReg fthen;
	CodeAndReg felse;
	
	String testcode = "%tst = icmp eq i32 0, ";
	String branchcode = "br i1 %tst, label %then, label %else\n";
	
	public IfExp(CodeAndReg test, CodeAndReg fthen, CodeAndReg felse,int regnum){
		super(regnum);
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
	}
	
	public CodeAndReg compile(){
		//test
		this.test.compile();
		this.code = this.test.code + testcode + this.test.reg + "\n" + 
			this.branchcode;
		
		//then
		this.fthen.compile();
		this.code = this.code + "then:\n" + this.fthen.code + "br label %end\n";
		
		//else
		this.felse.compile();
		this.code = this.code + "else:\n" + this.felse.code + "br label %end\n";
		
		//end
		this.code = this.code + "end:\n" + this.reg + " = phi i32 [" + 
			this.fthen.reg + ",%then], [" + this.felse.reg + ",%else]\n";
		
		return this;
	}
}
