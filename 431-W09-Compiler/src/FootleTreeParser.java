// $ANTLR 2.7.7 (20060906): "footle.g" -> "FootleTreeParser.java"$


import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

import java.util.ArrayList;

import Expressions.*;
import Expressions.Const.*;
import Environment.*;
import Values.*;


public class FootleTreeParser extends antlr.TreeParser       implements FootleLexerTokenTypes
 {

	private int nextUniqueRegisterId = 0; /* should ALWAYS be the NEXT UNIQUE register # */
	private int closureCounter = 0; /* ditto previous int; always increment after use */
	
	private void error (String errormsg)
	{
		System.out.println(errormsg);
		System.exit(1);
	}
public FootleTreeParser() {
	tokenNames = _tokenNames;
}

	public final Sequence  validate(AST _t) throws RecognitionException {
		Sequence finalStmtResult = null;
		
		AST validate_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t133 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,PROGRAM);
			_t = _t.getFirstChild();
			finalStmtResult=sequence(_t);
			_t = _retTree;
			_t = __t133;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return finalStmtResult;
	}
	
	public final Sequence  sequence(AST _t) throws RecognitionException {
		Sequence compiledSequence = null;
		
		AST sequence_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
			ArrayList<CodeAndReg> seq = new ArrayList<CodeAndReg>();
			CodeAndReg fSR = null;
		
		
		try {      // for error handling
			{
			_loop136:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					fSR=stmt(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						seq.add(fSR);
					}
				}
				else {
					break _loop136;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
					compiledSequence = new Sequence(seq, nextUniqueRegisterId++);
					
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return compiledSequence;
	}
	
	public final CodeAndReg  stmt(AST _t) throws RecognitionException {
		CodeAndReg stmtResult = null;
		
		AST stmt_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST fieldId = null;
		AST id2 = null;
		
			CodeAndReg exprResult, stmtListResult, thenResult, elseResult, assignResult;
			FuncDec funcDec;
			ArrayList<FuncDec> funcDecList = new ArrayList<FuncDec>();
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NEW:
			case NOT:
			case BINOP:
			case CONST_INT:
			case CONST_FLOAT:
			case CONST_BOOLEAN:
			case CONST_IDENTIFIER:
			case CONST_STRING:
			case FIELD_LOOKUP:
			case INVOKE:
			case METHOD_CALL:
			{
				stmtResult=expr(_t);
				_t = _retTree;
				break;
			}
			case IFF:
			{
				AST __t138 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,IFF);
				_t = _t.getFirstChild();
				exprResult=expr(_t);
				_t = _retTree;
				AST __t139 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,THENF);
				_t = _t.getFirstChild();
				thenResult=sequence(_t);
				_t = _retTree;
				_t = __t139;
				_t = _t.getNextSibling();
				AST __t140 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,ELSEF);
				_t = _t.getFirstChild();
				elseResult=sequence(_t);
				_t = _retTree;
				_t = __t140;
				_t = _t.getNextSibling();
				_t = __t138;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new IfExp(exprResult, thenResult, elseResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case WHILE:
			{
				AST __t150 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,WHILE);
				_t = _t.getFirstChild();
				exprResult=expr(_t);
				_t = _retTree;
				stmtListResult=sequence(_t);
				_t = _retTree;
				_t = __t150;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new WhileExp(exprResult, stmtListResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case VAR:
			{
				AST __t151 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,VAR);
				_t = _t.getFirstChild();
				AST __t152 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				id2 = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t152;
				_t = _t.getNextSibling();
				exprResult=expr(_t);
				_t = _retTree;
				_t = __t151;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new Bind(id2.toString(), exprResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case RETURN:
			{
				AST __t153 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,RETURN);
				_t = _t.getFirstChild();
				exprResult=expr(_t);
				_t = _retTree;
				_t = __t153;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new FReturn(exprResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case FUNCTION_COLLECTION:
			{
				AST __t154 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,FUNCTION_COLLECTION);
				_t = _t.getFirstChild();
				{
				int _cnt156=0;
				_loop156:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_t.getType()==FUNCTION_DEC)) {
						funcDec=function(_t);
						_t = _retTree;
						if ( inputState.guessing==0 ) {
							funcDecList.add(funcDec);
						}
					}
					else {
						if ( _cnt156>=1 ) { break _loop156; } else {throw new NoViableAltException(_t);}
					}
					
					_cnt156++;
				} while (true);
				}
				_t = __t154;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new FuncBind(funcDecList, nextUniqueRegisterId++);
							
				}
				break;
			}
			default:
				boolean synPredMatched144 = false;
				if (_t==null) _t=ASTNULL;
				if (((_t.getType()==ASSIGN))) {
					AST __t144 = _t;
					synPredMatched144 = true;
					inputState.guessing++;
					try {
						{
						AST __t142 = _t;
						AST tmp10_AST_in = (AST)_t;
						match(_t,ASSIGN);
						_t = _t.getFirstChild();
						AST __t143 = _t;
						AST tmp11_AST_in = (AST)_t;
						match(_t,CONST_IDENTIFIER);
						_t = _t.getFirstChild();
						AST tmp12_AST_in = (AST)_t;
						match(_t,ID);
						_t = _t.getNextSibling();
						_t = __t143;
						_t = _t.getNextSibling();
						expr(_t);
						_t = _retTree;
						_t = __t142;
						_t = _t.getNextSibling();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched144 = false;
					}
					_t = __t144;
inputState.guessing--;
				}
				if ( synPredMatched144 ) {
					AST __t145 = _t;
					AST tmp13_AST_in = (AST)_t;
					match(_t,ASSIGN);
					_t = _t.getFirstChild();
					AST __t146 = _t;
					AST tmp14_AST_in = (AST)_t;
					match(_t,CONST_IDENTIFIER);
					_t = _t.getFirstChild();
					id = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					_t = __t146;
					_t = _t.getNextSibling();
					exprResult=expr(_t);
					_t = _retTree;
					_t = __t145;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							/* binding to variables; VarMuts should always work if the static pass determines use before initialization */
									stmtResult = new VarMut(id.toString(), exprResult, nextUniqueRegisterId++);
								
					}
				}
				else if ((_t.getType()==ASSIGN)) {
					AST __t147 = _t;
					AST tmp15_AST_in = (AST)_t;
					match(_t,ASSIGN);
					_t = _t.getFirstChild();
					AST __t148 = _t;
					AST tmp16_AST_in = (AST)_t;
					match(_t,FIELD_LOOKUP);
					_t = _t.getFirstChild();
					exprResult=expr(_t);
					_t = _retTree;
					AST __t149 = _t;
					AST tmp17_AST_in = (AST)_t;
					match(_t,CONST_IDENTIFIER);
					_t = _t.getFirstChild();
					fieldId = (AST)_t;
					match(_t,ID);
					_t = _t.getNextSibling();
					_t = __t149;
					_t = _t.getNextSibling();
					_t = __t148;
					_t = _t.getNextSibling();
					assignResult=expr(_t);
					_t = _retTree;
					_t = __t147;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							stmtResult = new FieldMut(exprResult, fieldId.toString(), assignResult, nextUniqueRegisterId++);
								
					}
				}
			else {
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return stmtResult;
	}
	
	public final CodeAndReg  expr(AST _t) throws RecognitionException {
		CodeAndReg result = null;
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST fieldId = null;
		AST methodId = null;
		AST functionName = null;
		AST objectName = null;
		
			CodeAndReg expression;
			ArrayList<CodeAndReg> argumentList;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case BINOP:
			{
				result=binop(_t);
				_t = _retTree;
				break;
			}
			case CONST_INT:
			case CONST_FLOAT:
			case CONST_BOOLEAN:
			case CONST_IDENTIFIER:
			case CONST_STRING:
			{
				result=const_val(_t);
				_t = _retTree;
				break;
			}
			case NOT:
			{
				AST __t169 = _t;
				AST tmp18_AST_in = (AST)_t;
				match(_t,NOT);
				_t = _t.getFirstChild();
				expression=expr(_t);
				_t = _retTree;
				_t = __t169;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						result = new UnaryOperation("not", expression, nextUniqueRegisterId++);
							
				}
				break;
			}
			case FIELD_LOOKUP:
			{
				AST __t170 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,FIELD_LOOKUP);
				_t = _t.getFirstChild();
				expression=expr(_t);
				_t = _retTree;
				AST __t171 = _t;
				AST tmp20_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				fieldId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t171;
				_t = _t.getNextSibling();
				_t = __t170;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						result = new FieldLookup(expression, fieldId.toString(), nextUniqueRegisterId++);
							
				}
				break;
			}
			case METHOD_CALL:
			{
				AST __t172 = _t;
				AST tmp21_AST_in = (AST)_t;
				match(_t,METHOD_CALL);
				_t = _t.getFirstChild();
				expression=expr(_t);
				_t = _retTree;
				AST __t173 = _t;
				AST tmp22_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				methodId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t173;
				_t = _t.getNextSibling();
				argumentList=args(_t);
				_t = _retTree;
				_t = __t172;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						result = new MethodCall(new FieldLookup(expression, methodId.toString(), nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
							
				}
				break;
			}
			case INVOKE:
			{
				AST __t174 = _t;
				AST tmp23_AST_in = (AST)_t;
				match(_t,INVOKE);
				_t = _t.getFirstChild();
				AST __t175 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				functionName = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t175;
				_t = _t.getNextSibling();
				argumentList=args(_t);
				_t = _retTree;
				_t = __t174;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						String funName = functionName.toString();
								result = new Application(new VarRef(funName, nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
								if (operation.equals("string-length")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("integer?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("boolean?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("floating-point?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("void?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("string?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("closure?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("plain?")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								} else if (operation.equals("print")) {
									result = new UnaryOperation(funName, argumentList.get(0));
								}
							
				}
				break;
			}
			case NEW:
			{
				AST __t176 = _t;
				AST tmp25_AST_in = (AST)_t;
				match(_t,NEW);
				_t = _t.getFirstChild();
				AST __t177 = _t;
				AST tmp26_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				objectName = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t177;
				_t = _t.getNextSibling();
				argumentList=args(_t);
				_t = _retTree;
				_t = __t176;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						result = new NewObj(new VarRef(objectName.toString(), nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
							
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return result;
	}
	
	public final FuncDec  function(AST _t) throws RecognitionException {
		FuncDec funcDecReturn = null;
		
		AST function_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST name = null;
		
			Sequence functionBody;
			ArrayList<String> params;
		
		
		try {      // for error handling
			AST __t158 = _t;
			AST tmp27_AST_in = (AST)_t;
			match(_t,FUNCTION_DEC);
			_t = _t.getFirstChild();
			AST __t159 = _t;
			AST tmp28_AST_in = (AST)_t;
			match(_t,FUNCTION_NAME);
			_t = _t.getFirstChild();
			AST __t160 = _t;
			AST tmp29_AST_in = (AST)_t;
			match(_t,CONST_IDENTIFIER);
			_t = _t.getFirstChild();
			name = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t160;
			_t = _t.getNextSibling();
			_t = __t159;
			_t = _t.getNextSibling();
			params=paramlist(_t);
			_t = _retTree;
			AST __t161 = _t;
			AST tmp30_AST_in = (AST)_t;
			match(_t,FUNCTION_BODY);
			_t = _t.getFirstChild();
			functionBody=sequence(_t);
			_t = _retTree;
			_t = __t161;
			_t = _t.getNextSibling();
			_t = __t158;
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
					funcDecReturn = new FuncDec(name.toString(), params, functionBody, nextUniqueRegisterId++);
						
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return funcDecReturn;
	}
	
	public final ArrayList<String>  paramlist(AST _t) throws RecognitionException {
		ArrayList<String> parameters = new ArrayList<String>();
		
		AST paramlist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
			String paramName;
		
		
		try {      // for error handling
			{
			_loop164:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==CONST_IDENTIFIER)) {
					paramName=param_id(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						parameters.add(paramName);
					}
				}
				else {
					break _loop164;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				System.out.println(parameters);
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return parameters;
	}
	
	public final String  param_id(AST _t) throws RecognitionException {
		String paramName = null;
		
		AST param_id_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST name = null;
		
		try {      // for error handling
			AST __t166 = _t;
			AST tmp31_AST_in = (AST)_t;
			match(_t,CONST_IDENTIFIER);
			_t = _t.getFirstChild();
			{
			name = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			if ( inputState.guessing==0 ) {
				paramName = name.toString();
			}
			}
			_t = __t166;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return paramName;
	}
	
	public final CodeAndReg  binop(AST _t) throws RecognitionException {
		CodeAndReg resultRegister = null;
		
		AST binop_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
			CodeAndReg lhs, rhs;
		
		
		try {      // for error handling
			boolean synPredMatched181 = false;
			if (_t==null) _t=ASTNULL;
			if (((_t.getType()==BINOP))) {
				AST __t181 = _t;
				synPredMatched181 = true;
				inputState.guessing++;
				try {
					{
					AST __t180 = _t;
					AST tmp32_AST_in = (AST)_t;
					match(_t,BINOP);
					_t = _t.getFirstChild();
					AST tmp33_AST_in = (AST)_t;
					match(_t,AND);
					_t = _t.getNextSibling();
					expr(_t);
					_t = _retTree;
					expr(_t);
					_t = _retTree;
					_t = __t180;
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched181 = false;
				}
				_t = __t181;
inputState.guessing--;
			}
			if ( synPredMatched181 ) {
				AST __t182 = _t;
				AST tmp34_AST_in = (AST)_t;
				match(_t,BINOP);
				_t = _t.getFirstChild();
				AST tmp35_AST_in = (AST)_t;
				match(_t,AND);
				_t = _t.getNextSibling();
				lhs=expr(_t);
				_t = _retTree;
				rhs=expr(_t);
				_t = _retTree;
				_t = __t182;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						resultRegister = new Binop(lhs, "&&", rhs, nextUniqueRegisterId++);
							
				}
			}
			else {
				boolean synPredMatched185 = false;
				if (_t==null) _t=ASTNULL;
				if (((_t.getType()==BINOP))) {
					AST __t185 = _t;
					synPredMatched185 = true;
					inputState.guessing++;
					try {
						{
						AST __t184 = _t;
						AST tmp36_AST_in = (AST)_t;
						match(_t,BINOP);
						_t = _t.getFirstChild();
						AST tmp37_AST_in = (AST)_t;
						match(_t,OR);
						_t = _t.getNextSibling();
						expr(_t);
						_t = _retTree;
						expr(_t);
						_t = _retTree;
						_t = __t184;
						_t = _t.getNextSibling();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched185 = false;
					}
					_t = __t185;
inputState.guessing--;
				}
				if ( synPredMatched185 ) {
					AST __t186 = _t;
					AST tmp38_AST_in = (AST)_t;
					match(_t,BINOP);
					_t = _t.getFirstChild();
					AST tmp39_AST_in = (AST)_t;
					match(_t,OR);
					_t = _t.getNextSibling();
					lhs=expr(_t);
					_t = _retTree;
					rhs=expr(_t);
					_t = _retTree;
					_t = __t186;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							resultRegister = new Binop(lhs, "||", rhs, nextUniqueRegisterId++);
								
					}
				}
				else {
					boolean synPredMatched189 = false;
					if (_t==null) _t=ASTNULL;
					if (((_t.getType()==BINOP))) {
						AST __t189 = _t;
						synPredMatched189 = true;
						inputState.guessing++;
						try {
							{
							AST __t188 = _t;
							AST tmp40_AST_in = (AST)_t;
							match(_t,BINOP);
							_t = _t.getFirstChild();
							AST tmp41_AST_in = (AST)_t;
							match(_t,EQ);
							_t = _t.getNextSibling();
							expr(_t);
							_t = _retTree;
							expr(_t);
							_t = _retTree;
							_t = __t188;
							_t = _t.getNextSibling();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched189 = false;
						}
						_t = __t189;
inputState.guessing--;
					}
					if ( synPredMatched189 ) {
						AST __t190 = _t;
						AST tmp42_AST_in = (AST)_t;
						match(_t,BINOP);
						_t = _t.getFirstChild();
						AST tmp43_AST_in = (AST)_t;
						match(_t,EQ);
						_t = _t.getNextSibling();
						lhs=expr(_t);
						_t = _retTree;
						rhs=expr(_t);
						_t = _retTree;
						_t = __t190;
						_t = _t.getNextSibling();
						if ( inputState.guessing==0 ) {
								resultRegister = new Binop(lhs, "==", rhs, nextUniqueRegisterId++);
									
						}
					}
					else {
						boolean synPredMatched193 = false;
						if (_t==null) _t=ASTNULL;
						if (((_t.getType()==BINOP))) {
							AST __t193 = _t;
							synPredMatched193 = true;
							inputState.guessing++;
							try {
								{
								AST __t192 = _t;
								AST tmp44_AST_in = (AST)_t;
								match(_t,BINOP);
								_t = _t.getFirstChild();
								AST tmp45_AST_in = (AST)_t;
								match(_t,LT);
								_t = _t.getNextSibling();
								expr(_t);
								_t = _retTree;
								expr(_t);
								_t = _retTree;
								_t = __t192;
								_t = _t.getNextSibling();
								}
							}
							catch (RecognitionException pe) {
								synPredMatched193 = false;
							}
							_t = __t193;
inputState.guessing--;
						}
						if ( synPredMatched193 ) {
							AST __t194 = _t;
							AST tmp46_AST_in = (AST)_t;
							match(_t,BINOP);
							_t = _t.getFirstChild();
							AST tmp47_AST_in = (AST)_t;
							match(_t,LT);
							_t = _t.getNextSibling();
							lhs=expr(_t);
							_t = _retTree;
							rhs=expr(_t);
							_t = _retTree;
							_t = __t194;
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
									resultRegister = new Binop(lhs, "<", rhs, nextUniqueRegisterId++);
										
							}
						}
						else {
							boolean synPredMatched197 = false;
							if (_t==null) _t=ASTNULL;
							if (((_t.getType()==BINOP))) {
								AST __t197 = _t;
								synPredMatched197 = true;
								inputState.guessing++;
								try {
									{
									AST __t196 = _t;
									AST tmp48_AST_in = (AST)_t;
									match(_t,BINOP);
									_t = _t.getFirstChild();
									AST tmp49_AST_in = (AST)_t;
									match(_t,GT);
									_t = _t.getNextSibling();
									expr(_t);
									_t = _retTree;
									expr(_t);
									_t = _retTree;
									_t = __t196;
									_t = _t.getNextSibling();
									}
								}
								catch (RecognitionException pe) {
									synPredMatched197 = false;
								}
								_t = __t197;
inputState.guessing--;
							}
							if ( synPredMatched197 ) {
								AST __t198 = _t;
								AST tmp50_AST_in = (AST)_t;
								match(_t,BINOP);
								_t = _t.getFirstChild();
								AST tmp51_AST_in = (AST)_t;
								match(_t,GT);
								_t = _t.getNextSibling();
								lhs=expr(_t);
								_t = _retTree;
								rhs=expr(_t);
								_t = _retTree;
								_t = __t198;
								_t = _t.getNextSibling();
								if ( inputState.guessing==0 ) {
										resultRegister = new Binop(lhs, ">", rhs, nextUniqueRegisterId++);
											
								}
							}
							else {
								boolean synPredMatched201 = false;
								if (_t==null) _t=ASTNULL;
								if (((_t.getType()==BINOP))) {
									AST __t201 = _t;
									synPredMatched201 = true;
									inputState.guessing++;
									try {
										{
										AST __t200 = _t;
										AST tmp52_AST_in = (AST)_t;
										match(_t,BINOP);
										_t = _t.getFirstChild();
										AST tmp53_AST_in = (AST)_t;
										match(_t,NE);
										_t = _t.getNextSibling();
										expr(_t);
										_t = _retTree;
										expr(_t);
										_t = _retTree;
										_t = __t200;
										_t = _t.getNextSibling();
										}
									}
									catch (RecognitionException pe) {
										synPredMatched201 = false;
									}
									_t = __t201;
inputState.guessing--;
								}
								if ( synPredMatched201 ) {
									AST __t202 = _t;
									AST tmp54_AST_in = (AST)_t;
									match(_t,BINOP);
									_t = _t.getFirstChild();
									AST tmp55_AST_in = (AST)_t;
									match(_t,NE);
									_t = _t.getNextSibling();
									lhs=expr(_t);
									_t = _retTree;
									rhs=expr(_t);
									_t = _retTree;
									_t = __t202;
									_t = _t.getNextSibling();
									if ( inputState.guessing==0 ) {
											resultRegister = new Binop(lhs, "!=", rhs, nextUniqueRegisterId++);
												
									}
								}
								else {
									boolean synPredMatched205 = false;
									if (_t==null) _t=ASTNULL;
									if (((_t.getType()==BINOP))) {
										AST __t205 = _t;
										synPredMatched205 = true;
										inputState.guessing++;
										try {
											{
											AST __t204 = _t;
											AST tmp56_AST_in = (AST)_t;
											match(_t,BINOP);
											_t = _t.getFirstChild();
											AST tmp57_AST_in = (AST)_t;
											match(_t,LTE);
											_t = _t.getNextSibling();
											expr(_t);
											_t = _retTree;
											expr(_t);
											_t = _retTree;
											_t = __t204;
											_t = _t.getNextSibling();
											}
										}
										catch (RecognitionException pe) {
											synPredMatched205 = false;
										}
										_t = __t205;
inputState.guessing--;
									}
									if ( synPredMatched205 ) {
										AST __t206 = _t;
										AST tmp58_AST_in = (AST)_t;
										match(_t,BINOP);
										_t = _t.getFirstChild();
										AST tmp59_AST_in = (AST)_t;
										match(_t,LTE);
										_t = _t.getNextSibling();
										lhs=expr(_t);
										_t = _retTree;
										rhs=expr(_t);
										_t = _retTree;
										_t = __t206;
										_t = _t.getNextSibling();
										if ( inputState.guessing==0 ) {
												resultRegister = new Binop(lhs, "<=", rhs, nextUniqueRegisterId++);
													
										}
									}
									else {
										boolean synPredMatched209 = false;
										if (_t==null) _t=ASTNULL;
										if (((_t.getType()==BINOP))) {
											AST __t209 = _t;
											synPredMatched209 = true;
											inputState.guessing++;
											try {
												{
												AST __t208 = _t;
												AST tmp60_AST_in = (AST)_t;
												match(_t,BINOP);
												_t = _t.getFirstChild();
												AST tmp61_AST_in = (AST)_t;
												match(_t,GTE);
												_t = _t.getNextSibling();
												expr(_t);
												_t = _retTree;
												expr(_t);
												_t = _retTree;
												_t = __t208;
												_t = _t.getNextSibling();
												}
											}
											catch (RecognitionException pe) {
												synPredMatched209 = false;
											}
											_t = __t209;
inputState.guessing--;
										}
										if ( synPredMatched209 ) {
											AST __t210 = _t;
											AST tmp62_AST_in = (AST)_t;
											match(_t,BINOP);
											_t = _t.getFirstChild();
											AST tmp63_AST_in = (AST)_t;
											match(_t,GTE);
											_t = _t.getNextSibling();
											lhs=expr(_t);
											_t = _retTree;
											rhs=expr(_t);
											_t = _retTree;
											_t = __t210;
											_t = _t.getNextSibling();
											if ( inputState.guessing==0 ) {
													resultRegister = new Binop(lhs, ">=", rhs, nextUniqueRegisterId++);
														
											}
										}
										else {
											boolean synPredMatched213 = false;
											if (_t==null) _t=ASTNULL;
											if (((_t.getType()==BINOP))) {
												AST __t213 = _t;
												synPredMatched213 = true;
												inputState.guessing++;
												try {
													{
													AST __t212 = _t;
													AST tmp64_AST_in = (AST)_t;
													match(_t,BINOP);
													_t = _t.getFirstChild();
													AST tmp65_AST_in = (AST)_t;
													match(_t,PLUS);
													_t = _t.getNextSibling();
													expr(_t);
													_t = _retTree;
													expr(_t);
													_t = _retTree;
													_t = __t212;
													_t = _t.getNextSibling();
													}
												}
												catch (RecognitionException pe) {
													synPredMatched213 = false;
												}
												_t = __t213;
inputState.guessing--;
											}
											if ( synPredMatched213 ) {
												AST __t214 = _t;
												AST tmp66_AST_in = (AST)_t;
												match(_t,BINOP);
												_t = _t.getFirstChild();
												AST tmp67_AST_in = (AST)_t;
												match(_t,PLUS);
												_t = _t.getNextSibling();
												lhs=expr(_t);
												_t = _retTree;
												rhs=expr(_t);
												_t = _retTree;
												_t = __t214;
												_t = _t.getNextSibling();
												if ( inputState.guessing==0 ) {
														resultRegister = new Binop(lhs, "+", rhs, nextUniqueRegisterId++);
															
												}
											}
											else {
												boolean synPredMatched217 = false;
												if (_t==null) _t=ASTNULL;
												if (((_t.getType()==BINOP))) {
													AST __t217 = _t;
													synPredMatched217 = true;
													inputState.guessing++;
													try {
														{
														AST __t216 = _t;
														AST tmp68_AST_in = (AST)_t;
														match(_t,BINOP);
														_t = _t.getFirstChild();
														AST tmp69_AST_in = (AST)_t;
														match(_t,MINUS);
														_t = _t.getNextSibling();
														expr(_t);
														_t = _retTree;
														expr(_t);
														_t = _retTree;
														_t = __t216;
														_t = _t.getNextSibling();
														}
													}
													catch (RecognitionException pe) {
														synPredMatched217 = false;
													}
													_t = __t217;
inputState.guessing--;
												}
												if ( synPredMatched217 ) {
													AST __t218 = _t;
													AST tmp70_AST_in = (AST)_t;
													match(_t,BINOP);
													_t = _t.getFirstChild();
													AST tmp71_AST_in = (AST)_t;
													match(_t,MINUS);
													_t = _t.getNextSibling();
													lhs=expr(_t);
													_t = _retTree;
													rhs=expr(_t);
													_t = _retTree;
													_t = __t218;
													_t = _t.getNextSibling();
													if ( inputState.guessing==0 ) {
															resultRegister = new Binop(lhs, "-", rhs, nextUniqueRegisterId++);
																
													}
												}
												else {
													boolean synPredMatched221 = false;
													if (_t==null) _t=ASTNULL;
													if (((_t.getType()==BINOP))) {
														AST __t221 = _t;
														synPredMatched221 = true;
														inputState.guessing++;
														try {
															{
															AST __t220 = _t;
															AST tmp72_AST_in = (AST)_t;
															match(_t,BINOP);
															_t = _t.getFirstChild();
															AST tmp73_AST_in = (AST)_t;
															match(_t,TIMES);
															_t = _t.getNextSibling();
															expr(_t);
															_t = _retTree;
															expr(_t);
															_t = _retTree;
															_t = __t220;
															_t = _t.getNextSibling();
															}
														}
														catch (RecognitionException pe) {
															synPredMatched221 = false;
														}
														_t = __t221;
inputState.guessing--;
													}
													if ( synPredMatched221 ) {
														AST __t222 = _t;
														AST tmp74_AST_in = (AST)_t;
														match(_t,BINOP);
														_t = _t.getFirstChild();
														AST tmp75_AST_in = (AST)_t;
														match(_t,TIMES);
														_t = _t.getNextSibling();
														lhs=expr(_t);
														_t = _retTree;
														rhs=expr(_t);
														_t = _retTree;
														_t = __t222;
														_t = _t.getNextSibling();
														if ( inputState.guessing==0 ) {
																resultRegister = new Binop(lhs, "*", rhs, nextUniqueRegisterId++);
																	
														}
													}
													else {
														boolean synPredMatched225 = false;
														if (_t==null) _t=ASTNULL;
														if (((_t.getType()==BINOP))) {
															AST __t225 = _t;
															synPredMatched225 = true;
															inputState.guessing++;
															try {
																{
																AST __t224 = _t;
																AST tmp76_AST_in = (AST)_t;
																match(_t,BINOP);
																_t = _t.getFirstChild();
																AST tmp77_AST_in = (AST)_t;
																match(_t,DIVIDE);
																_t = _t.getNextSibling();
																expr(_t);
																_t = _retTree;
																expr(_t);
																_t = _retTree;
																_t = __t224;
																_t = _t.getNextSibling();
																}
															}
															catch (RecognitionException pe) {
																synPredMatched225 = false;
															}
															_t = __t225;
inputState.guessing--;
														}
														if ( synPredMatched225 ) {
															AST __t226 = _t;
															AST tmp78_AST_in = (AST)_t;
															match(_t,BINOP);
															_t = _t.getFirstChild();
															AST tmp79_AST_in = (AST)_t;
															match(_t,DIVIDE);
															_t = _t.getNextSibling();
															lhs=expr(_t);
															_t = _retTree;
															rhs=expr(_t);
															_t = _retTree;
															_t = __t226;
															_t = _t.getNextSibling();
															if ( inputState.guessing==0 ) {
																	resultRegister = new Binop(lhs, "/", rhs, nextUniqueRegisterId++);
																		
															}
														}
														else {
															throw new NoViableAltException(_t);
														}
														}}}}}}}}}}}
													}
													catch (RecognitionException ex) {
														if (inputState.guessing==0) {
															reportError(ex);
															if (_t!=null) {_t = _t.getNextSibling();}
														} else {
														  throw ex;
														}
													}
													_retTree = _t;
													return resultRegister;
												}
												
	public final CodeAndReg  const_val(AST _t) throws RecognitionException {
		CodeAndReg constValue = null;
		
		AST const_val_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST f = null;
		AST id = null;
		AST str = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case CONST_INT:
			{
				AST __t228 = _t;
				AST tmp80_AST_in = (AST)_t;
				match(_t,CONST_INT);
				_t = _t.getFirstChild();
				i = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				_t = __t228;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new FInteger(new Integer(Integer.parseInt(i.toString())), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_FLOAT:
			{
				AST __t229 = _t;
				AST tmp81_AST_in = (AST)_t;
				match(_t,CONST_FLOAT);
				_t = _t.getFirstChild();
				f = (AST)_t;
				match(_t,FLOAT);
				_t = _t.getNextSibling();
				_t = __t229;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new FFloat(new Float(Float.parseFloat(f.toString())), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_IDENTIFIER:
			{
				AST __t238 = _t;
				AST tmp82_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				id = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t238;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new VarRef(id.toString(), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_STRING:
			{
				AST __t239 = _t;
				AST tmp83_AST_in = (AST)_t;
				match(_t,CONST_STRING);
				_t = _t.getFirstChild();
				str = (AST)_t;
				match(_t,STRING);
				_t = _t.getNextSibling();
				_t = __t239;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new FString(str.toString(), nextUniqueRegisterId++);
							
				}
				break;
			}
			default:
				boolean synPredMatched232 = false;
				if (_t==null) _t=ASTNULL;
				if (((_t.getType()==CONST_BOOLEAN))) {
					AST __t232 = _t;
					synPredMatched232 = true;
					inputState.guessing++;
					try {
						{
						AST __t231 = _t;
						AST tmp84_AST_in = (AST)_t;
						match(_t,CONST_BOOLEAN);
						_t = _t.getFirstChild();
						AST tmp85_AST_in = (AST)_t;
						match(_t,TRUE);
						_t = _t.getNextSibling();
						_t = __t231;
						_t = _t.getNextSibling();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched232 = false;
					}
					_t = __t232;
inputState.guessing--;
				}
				if ( synPredMatched232 ) {
					AST __t233 = _t;
					AST tmp86_AST_in = (AST)_t;
					match(_t,CONST_BOOLEAN);
					_t = _t.getFirstChild();
					AST tmp87_AST_in = (AST)_t;
					match(_t,TRUE);
					_t = _t.getNextSibling();
					_t = __t233;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							constValue = new FBoolean(new Boolean(true), nextUniqueRegisterId++);
								
					}
				}
				else {
					boolean synPredMatched236 = false;
					if (_t==null) _t=ASTNULL;
					if (((_t.getType()==CONST_BOOLEAN))) {
						AST __t236 = _t;
						synPredMatched236 = true;
						inputState.guessing++;
						try {
							{
							AST __t235 = _t;
							AST tmp88_AST_in = (AST)_t;
							match(_t,CONST_BOOLEAN);
							_t = _t.getFirstChild();
							AST tmp89_AST_in = (AST)_t;
							match(_t,FALSE);
							_t = _t.getNextSibling();
							_t = __t235;
							_t = _t.getNextSibling();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched236 = false;
						}
						_t = __t236;
inputState.guessing--;
					}
					if ( synPredMatched236 ) {
						AST __t237 = _t;
						AST tmp90_AST_in = (AST)_t;
						match(_t,CONST_BOOLEAN);
						_t = _t.getFirstChild();
						AST tmp91_AST_in = (AST)_t;
						match(_t,FALSE);
						_t = _t.getNextSibling();
						_t = __t237;
						_t = _t.getNextSibling();
						if ( inputState.guessing==0 ) {
								constValue = new FBoolean(new Boolean(false), nextUniqueRegisterId++);
									
						}
					}
				else {
					throw new NoViableAltException(_t);
				}
				}}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					if (_t!=null) {_t = _t.getNextSibling();}
				} else {
				  throw ex;
				}
			}
			_retTree = _t;
			return constValue;
		}
		
	public final ArrayList<CodeAndReg>  args(AST _t) throws RecognitionException {
		ArrayList<CodeAndReg> argumentList = new ArrayList<CodeAndReg>();
		
		AST args_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
			CodeAndReg argument;
		
		
		try {      // for error handling
			AST __t242 = _t;
			AST tmp92_AST_in = (AST)_t;
			match(_t,ARGUMENTS);
			_t = _t.getFirstChild();
			{
			_loop244:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_1.member(_t.getType()))) {
					argument=expr(_t);
					_t = _retTree;
					if ( inputState.guessing==0 ) {
						argumentList.add(argument);
					}
				}
				else {
					break _loop244;
				}
				
			} while (true);
			}
			_t = __t242;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
		return argumentList;
	}
	
	public final void operator(AST _t) throws RecognitionException {
		
		AST operator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				AST tmp93_AST_in = (AST)_t;
				match(_t,AND);
				_t = _t.getNextSibling();
				break;
			}
			case OR:
			{
				AST tmp94_AST_in = (AST)_t;
				match(_t,OR);
				_t = _t.getNextSibling();
				break;
			}
			case EQ:
			{
				AST tmp95_AST_in = (AST)_t;
				match(_t,EQ);
				_t = _t.getNextSibling();
				break;
			}
			case LT:
			{
				AST tmp96_AST_in = (AST)_t;
				match(_t,LT);
				_t = _t.getNextSibling();
				break;
			}
			case GT:
			{
				AST tmp97_AST_in = (AST)_t;
				match(_t,GT);
				_t = _t.getNextSibling();
				break;
			}
			case NE:
			{
				AST tmp98_AST_in = (AST)_t;
				match(_t,NE);
				_t = _t.getNextSibling();
				break;
			}
			case LTE:
			{
				AST tmp99_AST_in = (AST)_t;
				match(_t,LTE);
				_t = _t.getNextSibling();
				break;
			}
			case GTE:
			{
				AST tmp100_AST_in = (AST)_t;
				match(_t,GTE);
				_t = _t.getNextSibling();
				break;
			}
			case PLUS:
			{
				AST tmp101_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getNextSibling();
				break;
			}
			case MINUS:
			{
				AST tmp102_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getNextSibling();
				break;
			}
			case TIMES:
			{
				AST tmp103_AST_in = (AST)_t;
				match(_t,TIMES);
				_t = _t.getNextSibling();
				break;
			}
			case DIVIDE:
			{
				AST tmp104_AST_in = (AST)_t;
				match(_t,DIVIDE);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				if (_t!=null) {_t = _t.getNextSibling();}
			} else {
			  throw ex;
			}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"var\"",
		"\"return\"",
		"\"if\"",
		"\"else\"",
		"\"while\"",
		"\"function\"",
		"\"true\"",
		"\"false\"",
		"\"new\"",
		"LBRACE",
		"RBRACE",
		"SEMI",
		"COMMA",
		"LPAREN",
		"RPAREN",
		"ASSIGN",
		"DOT",
		"NOT",
		"AND",
		"OR",
		"EQ",
		"LT",
		"GT",
		"NE",
		"LTE",
		"GTE",
		"PLUS",
		"MINUS",
		"TIMES",
		"DIVIDE",
		"ID",
		"STRING",
		"INT",
		"FLOAT",
		"FLOAT_OR_INT",
		"WS",
		"COMMENT",
		"ARGUMENTS",
		"BINOP",
		"CONST_INT",
		"CONST_FLOAT",
		"CONST_BOOLEAN",
		"CONST_IDENTIFIER",
		"CONST_STRING",
		"ELSEF",
		"FIELD_LOOKUP",
		"FUNCTION_DEC",
		"FUNCTION_NAME",
		"FUNCTION_BODY",
		"FUNCTION_COLLECTION",
		"IFF",
		"INVOKE",
		"METHOD_CALL",
		"PROGRAM",
		"THENF"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 135948015707361584L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 108926417942614016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	}
	
