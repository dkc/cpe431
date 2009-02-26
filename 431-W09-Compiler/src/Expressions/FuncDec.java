package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;

public class FuncDec extends AbstractCodeAndReg{
	public String name;
	public ArrayList<String> params;
	public CodeAndReg body;
	public Env scope;
	private String mallocreg = "%fmalreg";
	private String eframereg = "%efreg";
	private String typereg = "%typeptr";
	private String memreg = "%memaddreg";
	private String ptrreg = "%ptrreg";
	private String objreg = "%objreg";
	
	public FuncDec(String name, ArrayList<String> params, CodeAndReg body,int regnum){
		super(regnum);
		this.name = name;
		this.params = params;
		this.body = body;
		this.mallocreg += regnum;
		this.eframereg += regnum;
		this.typereg += regnum;
		this.memreg += regnum;
		this.ptrreg += regnum;
		this.objreg += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		env.add(this.name);
		funcids.add(this.regnum);
		this.scope = new Env(this.regnum);
		Env.addScope(this.scope, env);
		for(String par: params){
			this.scope.add(par);
		}
		this.body.staticPass(this.scope, null);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		//build closure obj
		this.code.add(this.mallocreg + " = malloc %cobj, align 4\n");
		//closure env
		this.code.add(scope.getMallocReg() + " = malloc {%eframe*, i32, [" + scope.ids.size() +
 	   	  " x i32]}, align 4\n");
 	   	this.code.add(scope.getCurrentScope() + " = bitcast {%eframe*, i32, [" + scope.ids.size() + 
 	   			  " x i32]}* " + scope.getMallocReg() + " to %eframe*\n");
		//store func num
		this.code.add(this.typereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 0\n");
		this.code.add("store i32 " + ((this.regnum << 2) + 1) + ", i32* " + this.typereg + "\n");
 	   	//1 is tag right now
		
		//store env ptr
		this.code.add(this.eframereg + " = getelementptr %cobj* " + 
				this.mallocreg + ", i32 0, i32 2\n");
		this.code.add("store %eframe* " + this.scope.getCurrentScope() + ", %eframe** " + this.eframereg + "\n");
		
		//setup pointer to obj
		RegAndIndex regind = Env.lookup(this.name, env);
		this.code.add(this.objreg + " = ptrtoint %cobj* " + this.mallocreg + " to i32\n");
		this.code.add(this.memreg + " = shl i32 " + this.objreg + ", 2\n");
		this.code.add(this.reg + " = add i32 1, " + this.memreg + "\n");//add 1 for tag
		
		//store pointer to env
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		this.code.add("store i32 " + this.reg
				+ ", i32* " + this.ptrreg + "\n");
		
		//write func dec?
		funcdecs.add("define i32 @footle_fun" + this.regnum + "(%eframe* " + this.scope.getCurrentScope() + "){\n");
		int savedindex = funcdecs.size();
		//TODO can't handle func dec in func body, save index, use insert? done?
		ArrayList<String> body = this.body.compile(this.scope, funcdecs, fieldTable).getCode();
		body.add("}\n");
		funcdecs.addAll(savedindex,body);
		//funcdecs.add("ret i32 " + this.reg + "\n");
		
		return this;
	}
}
