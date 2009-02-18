package Environment;

import java.util.ArrayList;

public class Env {
	private String scopeReg;
	public int scopenum;
//	public String id;
	//public Value val;
	public ArrayList<String> ids;
	Env prev;
	
	public Env(){//int regnum){
		//this.id = id;
		//this.val = val;
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
		//if(env == null){
			//return newhead;
		//}
		//newhead.prev = oldhead;
		//newhead.scopeReg = oldhead.scopeReg;
		//return newhead;
		ids.add(id);
	}
	
	public static RegAndIndex lookup(String id, Env env){
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
			retVal.code.add("%eframeptr" + v.scopenum + " = getelementptr %eframe* " 
					+ v.scopeReg + ",i32 1\n");
			retVal.code.add("%scopereg" + v.scopenum + " = load i32* %eframeptr" + v.scopenum + "\n");
			i++;
			v = v.prev;
		}
		return null;
	}
	
	/*public boolean equals(Object o) {
		if(o == null || !(o instanceof Env)) {
			return false;
		}
		Env other = (Env)o;
		if(other.id.equals(this.id) && other.val.storedValue() == this.val.storedValue()) {
			if(this.prev == other.prev)
				return true;
			else if (this.prev == null || other.prev == null)
				return false;
			else
				return this.prev.equals(other.prev);
		}
		return false;
	}*/
}
