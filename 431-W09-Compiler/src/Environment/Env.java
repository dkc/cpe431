package Environment;

import java.util.ArrayList;

import LLVMObjects.LLVMLine;

public class Env {
	private String scopeReg;
	public int scopenum;
	private ArrayList<String> ids;
	Env prev;
	
	public Env(){
		this.ids = new ArrayList<String>();
		this.scopenum = 0;
		this.scopeReg = "%scopereg" + this.scopenum;
		prev = null;
	}
	
	public static Env addScope(Env newScope, Env oldScope){
		newScope.prev = oldScope;
		newScope.scopenum = oldScope.scopenum + 1;
		newScope.scopeReg = "%scopereg" + newScope.scopenum;
		return newScope;
	}
	
	public String getCurrentScope(){
		return this.scopeReg;
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
		int i = 0;
		RegAndIndex retVal = new RegAndIndex();
		while(v != null){
			for(index = 0;index < v.ids.size();index++){
				if(v.ids.get(index).equals(id)){
					retVal.index = index;
					if(i == 0){
						retVal.reg = env.scopeReg;
					}else{//TODO val is in higher scope
						retVal.reg = "%scopereg" + v.scopenum;
					}
					return retVal;
				}
			}
			//TODO add code to get element ptr to next scope in local reg
			
			currentLine = new LLVMLine("%eframeptr" + v.scopenum + " = getelementptr %eframe* " + v.scopeReg + ",i32 1\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined("%eframeptr" + v.scopenum);
			currentLine.addRegisterUsed(v.scopeReg);
			
			currentLine = new LLVMLine("%scopereg" + v.scopenum + " = load i32* %eframeptr" + v.scopenum + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined("%scopereg" + v.scopenum);
			currentLine.addRegisterUsed("%eframeptr" + v.scopenum);
			
			i++;
			v = v.prev;
		}
		return null;
	}
}
