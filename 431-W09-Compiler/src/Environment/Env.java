package Environment;

import java.util.ArrayList;

import LLVMObjects.LLVMLine;

public class Env {
	private String scopeReg;
	private String mallocReg = "%scomalreg";
	public int scopenum;
	private ArrayList<String> ids;
	Env prev;
	
	public Env(int scopenum){//int regnum){
		this.ids = new ArrayList<String>();
		this.scopenum = scopenum;
		this.scopeReg = "%scopereg" + this.scopenum;
		this.mallocReg += this.scopenum;
		prev = null;
	}
	
	public static Env addScope(Env newScope, Env oldScope){
		newScope.prev = oldScope;
		//newScope.scopenum = oldScope.scopenum + 1;
		//newScope.scopeReg = "%scopereg" + newScope.scopenum;
		return newScope;
	}
	
	public String getCurrentScope(){
		return this.scopeReg;
	}

	public String getMallocReg(){
		return this.mallocReg;
	}

	public void add(String id){
		ids.add(id);
	}
	
	public int numIds() {
		return ids.size();
	}
	
	public static RegAndIndex lookup(String id, Env env){
		LLVMLine currentLine;
		Env v = env;
		int index = 0;
		//int i = 0;
		RegAndIndex retVal = new RegAndIndex();
		while(v != null){
			for(index = 0;index < v.ids.size();index++){
				if(v.ids.get(index).equals(id)){
					retVal.index = index;
					retVal.reg = v.getCurrentScope();
					/*if(i == 0){
						retVal.reg = env.scopeReg;
					}else{//TODO val is in higher scope
						retVal.reg = "%scopereg" + v.scopenum;
					}*/
					return retVal;
				}
			}
			//TODO add code to get element ptr to next scope in local reg
			
			String eframeptr = "%eframeptr" + v.scopenum;
			currentLine = new LLVMLine(eframeptr + " = getelementptr %eframe* " + v.scopeReg + ", i32 0, i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(eframeptr);
			currentLine.addRegisterUsed(v.scopeReg);
			currentLine.addConstantUsed(4*1);
			
			//i++;
			v = v.prev;
			
			if(v == null)
			{
				return null;
			}
			
			currentLine = new LLVMLine(v.scopeReg + " = load %eframe** " + eframeptr + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(v.scopeReg);
			currentLine.addRegisterUsed(eframeptr);
			
		}
		return null;
	}
}
