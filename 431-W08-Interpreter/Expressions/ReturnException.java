package Expressions;

import Values.Value;

public class ReturnException extends Exception {
	
	static final long serialVersionUID = 0;
	public Value storedContainer;
	
	public ReturnException(Value c) {
		storedContainer = c;
	}
}