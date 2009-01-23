import Expressions.*;
import Values.ReturnContainer;

public class Interp {
	
	public Object interp(Expression exp) {
		try {
			return exp.interp(null).storedValue();
		} catch(ReturnException e) {
			System.err.println("Caught a return outside of a function/method; exiting");
			System.exit(1);
			return null;
		}
	}
}
