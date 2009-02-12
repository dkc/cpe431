package Values;

import java.util.ArrayList;
import Environment.Env;
import Expressions.*;

public class VClosure implements Value{
	public ArrayList<String> params;
	public Expression body;
	public Env env;
	
	public VClosure(ArrayList<String> params, Expression body,Env env){
		this.params = params;
		this.body = body;
		this.env = env;
	}

	@Override
	public Object storedValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
