package Values;

import java.util.ArrayList;
import Environment.Env;
import Expressions.*;

public class VClosure implements Value{
	public ArrayList<String> params;
	public Env env;
	
	public VClosure(ArrayList<String> params,Env env){
		this.params = params;
		this.env = env;
	}

	@Override
	public Object storedValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
