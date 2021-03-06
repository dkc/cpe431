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
	
	public boolean contains(String id){
		return this.ids.contains(id);
	}
	
	public int numIds() {
		return ids.size();
	}
	
	public static RegAndIndex lookup(String id, Env env, int regnum){
		LLVMLine currentLine;
		Env v = env;
		int index = 0;
		int i = 0;
		RegAndIndex retVal = new RegAndIndex();
		while(v != null){
			for(index = 0;index < v.ids.size();index++){
				if(v.ids.get(index).equals(id)){
					retVal.index = index;
					//retVal.reg = v.getCurrentScope() + regnum;
					if(i == 0){
						retVal.reg = v.getCurrentScope();
					}else{//TODO val is in higher scope
						retVal.reg = v.getCurrentScope() + "_" + regnum;
					}
					return retVal;
				}
			}
			String eframeptr = "%eframeptr" + regnum + "_" + i;
			//TODO add code to get element ptr to next scope in local reg
			if(i == 0){
			
			currentLine = new LLVMLine(eframeptr + " = getelementptr %eframe* " + v.scopeReg + ", i32 0, i32 0\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(eframeptr);
			currentLine.addRegisterUsed(v.scopeReg);
			currentLine.addConstantUsed(4*0);
			retVal.code.add(currentLine);
			}else{
				currentLine = new LLVMLine(eframeptr + " = getelementptr %eframe* " + v.scopeReg + "_" + regnum + ", i32 0, i32 0\n");
				currentLine.setOperation("getelementptr");
				currentLine.setRegisterDefined(eframeptr);
				currentLine.addRegisterUsed(v.scopeReg + "_" + regnum);
				currentLine.addConstantUsed(4*0);
				retVal.code.add(currentLine);
			}
			i++;
			v = v.prev;
			
			if(v==null)
			{
				//System.err.println("lookup not found: " + id);
				return null;
			}
			
			currentLine = new LLVMLine(v.scopeReg + "_" + regnum + " = load %eframe** " + eframeptr + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(v.scopeReg + "_" + regnum);
			currentLine.addRegisterUsed(eframeptr);
			retVal.code.add(currentLine);
			
		}
		return null;
	}
}
