package Environment;

import Values.PObject;
import Values.Value;

public class Env {
	public String id;
	//public int index;
	Env prev;
	
	public Env(String id){//, String addr){
		this.id = id;
		//this.addr = addr;
		prev = null;
	}
	
	public static Env add(Env newhead, Env oldhead){
		if(oldhead == null){
			return newhead;
		}
		newhead.prev = oldhead;
		return newhead;
	}
	
	public static int lookup(String id, Env env){
		Env v = env;
		int index = 0;
		while(v != null){
			if(v.id.equals(id)){
				return index;
			}
			v = v.prev;
			index++;
		}
		return null;
	}
}
