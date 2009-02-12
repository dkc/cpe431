package Environment;

import Values.PObject;
import Values.Value;

public class Env {
	public String id;
	public Value val;
	Env prev;
	
	public Env(String id, Value val){
		this.id = id;
		this.val = val;
		prev = null;
	}
	
	public static Env add(Env newhead, Env oldhead){
		if(oldhead == null){
			return newhead;
		}
		newhead.prev = oldhead;
		return newhead;
	}
	
	public static Env lookup(String id, Env env){
		Env v = env;
		while(v != null){
			if(v.id.equals(id)){
				return v;
			}
			v = v.prev;
		}
		return null;
	}
	
	public boolean equals(Object o) {
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
	}
}
