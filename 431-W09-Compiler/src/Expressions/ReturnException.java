package Expressions;


public class ReturnException extends Exception {
	
	static final long serialVersionUID = 0;
	public CodeAndReg storedContainer;
	
	public ReturnException(CodeAndReg c) {
		storedContainer = c;
	}
}