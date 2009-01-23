package Expressions;

import Environment.*;
import Values.*;


public interface Expression {

	public Value interp(Env env) throws ReturnException;
}
