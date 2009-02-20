// $ANTLR 2.7.7 (20060906): "src/footle.g" -> "FootleTreeParser.java"$


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
		AST id2 = null;
		
			CodeAndReg exprResult, stmtListResult, thenResult, elseResult;
			IfExp ifExpr;
		
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
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
						ifExpr = new IfExp(exprResult, thenResult, elseResult, nextUniqueRegisterId++);
								stmtResult = ifExpr;
								/* will need an extra register to store the phi of then and else for the return--is that what if should be returning? */
							
				}
				break;
			}
			case ASSIGN:
			{
				AST __t141 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				AST __t142 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				id = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t142;
				_t = _t.getNextSibling();
				exprResult=expr(_t);
				_t = _retTree;
				_t = __t141;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						/* binding to variables; VarMuts should always work if the static pass determines use before initialization */
								stmtResult = new VarMut(id.toString(), exprResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case NOT:
			case BINOP:
			case CONST_INT:
			case CONST_FLOAT:
			case CONST_BOOLEAN:
			case CONST_IDENTIFIER:
			case CONST_STRING:
			case FIELD_LOOKUP:
			case METHOD_CALL:
			{
				stmtResult=expr(_t);
				_t = _retTree;
				if ( inputState.guessing==0 ) {
						/* is this right? pretty sure we don't need more encapsulation */
							
				}
				break;
			}
			case WHILE:
			{
				AST __t143 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,WHILE);
				_t = _t.getFirstChild();
				exprResult=expr(_t);
				_t = _retTree;
				stmtListResult=sequence(_t);
				_t = _retTree;
				_t = __t143;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new WhileExp(exprResult, stmtListResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case VAR:
			{
				AST __t144 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,VAR);
				_t = _t.getFirstChild();
				AST __t145 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				id2 = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t145;
				_t = _t.getNextSibling();
				exprResult=expr(_t);
				_t = _retTree;
				_t = __t144;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new Bind(id2.toString(), exprResult, nextUniqueRegisterId++);
							
				}
				break;
			}
			case RETURN:
			{
				AST __t146 = _t;
				AST tmp10_AST_in = (AST)_t;
				match(_t,RETURN);
				_t = _t.getFirstChild();
				exprResult=expr(_t);
				_t = _retTree;
				_t = __t146;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						stmtResult = new FReturn(exprResult, nextUniqueRegisterId++);
							
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
		return stmtResult;
	}
	
	public final CodeAndReg  expr(AST _t) throws RecognitionException {
		CodeAndReg result = null;
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST fieldId = null;
		AST methodId = null;
		
			CodeAndReg expression;
		
		
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
				AST __t148 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,NOT);
				_t = _t.getFirstChild();
				expression=expr(_t);
				_t = _retTree;
				_t = __t148;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						result = new UnaryOperation("not", expression, nextUniqueRegisterId++);
							
				}
				break;
			}
			case FIELD_LOOKUP:
			{
				AST tmp12_AST_in = (AST)_t;
				match(_t,FIELD_LOOKUP);
				_t = _t.getNextSibling();
				expression=expr(_t);
				_t = _retTree;
				AST __t149 = _t;
				AST tmp13_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				fieldId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t149;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						
							
				}
				break;
			}
			case METHOD_CALL:
			{
				AST tmp14_AST_in = (AST)_t;
				match(_t,METHOD_CALL);
				_t = _t.getNextSibling();
				expression=expr(_t);
				_t = _retTree;
				AST __t150 = _t;
				AST tmp15_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				methodId = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t150;
				_t = _t.getNextSibling();
				AST __t151 = _t;
				AST tmp16_AST_in = (AST)_t;
				match(_t,ARGUMENTS);
				_t = _t.getFirstChild();
				args(_t);
				_t = _retTree;
				_t = __t151;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						
							
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
	
	public final CodeAndReg  binop(AST _t) throws RecognitionException {
		CodeAndReg resultRegister = null;
		
		AST binop_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
			CodeAndReg lhs, rhs;
		
		
		try {      // for error handling
			boolean synPredMatched155 = false;
			if (_t==null) _t=ASTNULL;
			if (((_t.getType()==BINOP))) {
				AST __t155 = _t;
				synPredMatched155 = true;
				inputState.guessing++;
				try {
					{
					AST __t154 = _t;
					AST tmp17_AST_in = (AST)_t;
					match(_t,BINOP);
					_t = _t.getFirstChild();
					AST tmp18_AST_in = (AST)_t;
					match(_t,AND);
					_t = _t.getNextSibling();
					expr(_t);
					_t = _retTree;
					expr(_t);
					_t = _retTree;
					_t = __t154;
					_t = _t.getNextSibling();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched155 = false;
				}
				_t = __t155;
inputState.guessing--;
			}
			if ( synPredMatched155 ) {
				AST __t156 = _t;
				AST tmp19_AST_in = (AST)_t;
				match(_t,BINOP);
				_t = _t.getFirstChild();
				AST tmp20_AST_in = (AST)_t;
				match(_t,AND);
				_t = _t.getNextSibling();
				lhs=expr(_t);
				_t = _retTree;
				rhs=expr(_t);
				_t = _retTree;
				_t = __t156;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						resultRegister = new BinaryOperation(lhs, "&&", rhs, nextUniqueRegisterId++);
							
				}
			}
			else {
				boolean synPredMatched159 = false;
				if (_t==null) _t=ASTNULL;
				if (((_t.getType()==BINOP))) {
					AST __t159 = _t;
					synPredMatched159 = true;
					inputState.guessing++;
					try {
						{
						AST __t158 = _t;
						AST tmp21_AST_in = (AST)_t;
						match(_t,BINOP);
						_t = _t.getFirstChild();
						AST tmp22_AST_in = (AST)_t;
						match(_t,OR);
						_t = _t.getNextSibling();
						expr(_t);
						_t = _retTree;
						expr(_t);
						_t = _retTree;
						_t = __t158;
						_t = _t.getNextSibling();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched159 = false;
					}
					_t = __t159;
inputState.guessing--;
				}
				if ( synPredMatched159 ) {
					AST __t160 = _t;
					AST tmp23_AST_in = (AST)_t;
					match(_t,BINOP);
					_t = _t.getFirstChild();
					AST tmp24_AST_in = (AST)_t;
					match(_t,OR);
					_t = _t.getNextSibling();
					lhs=expr(_t);
					_t = _retTree;
					rhs=expr(_t);
					_t = _retTree;
					_t = __t160;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							resultRegister = new BinaryOperation(lhs, "||", rhs, nextUniqueRegisterId++);
								
					}
				}
				else {
					boolean synPredMatched163 = false;
					if (_t==null) _t=ASTNULL;
					if (((_t.getType()==BINOP))) {
						AST __t163 = _t;
						synPredMatched163 = true;
						inputState.guessing++;
						try {
							{
							AST __t162 = _t;
							AST tmp25_AST_in = (AST)_t;
							match(_t,BINOP);
							_t = _t.getFirstChild();
							AST tmp26_AST_in = (AST)_t;
							match(_t,EQ);
							_t = _t.getNextSibling();
							expr(_t);
							_t = _retTree;
							expr(_t);
							_t = _retTree;
							_t = __t162;
							_t = _t.getNextSibling();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched163 = false;
						}
						_t = __t163;
inputState.guessing--;
					}
					if ( synPredMatched163 ) {
						AST __t164 = _t;
						AST tmp27_AST_in = (AST)_t;
						match(_t,BINOP);
						_t = _t.getFirstChild();
						AST tmp28_AST_in = (AST)_t;
						match(_t,EQ);
						_t = _t.getNextSibling();
						lhs=expr(_t);
						_t = _retTree;
						rhs=expr(_t);
						_t = _retTree;
						_t = __t164;
						_t = _t.getNextSibling();
						if ( inputState.guessing==0 ) {
								resultRegister = new BinaryOperation(lhs, "==", rhs, nextUniqueRegisterId++);
									
						}
					}
					else {
						boolean synPredMatched167 = false;
						if (_t==null) _t=ASTNULL;
						if (((_t.getType()==BINOP))) {
							AST __t167 = _t;
							synPredMatched167 = true;
							inputState.guessing++;
							try {
								{
								AST __t166 = _t;
								AST tmp29_AST_in = (AST)_t;
								match(_t,BINOP);
								_t = _t.getFirstChild();
								AST tmp30_AST_in = (AST)_t;
								match(_t,LT);
								_t = _t.getNextSibling();
								expr(_t);
								_t = _retTree;
								expr(_t);
								_t = _retTree;
								_t = __t166;
								_t = _t.getNextSibling();
								}
							}
							catch (RecognitionException pe) {
								synPredMatched167 = false;
							}
							_t = __t167;
inputState.guessing--;
						}
						if ( synPredMatched167 ) {
							AST __t168 = _t;
							AST tmp31_AST_in = (AST)_t;
							match(_t,BINOP);
							_t = _t.getFirstChild();
							AST tmp32_AST_in = (AST)_t;
							match(_t,LT);
							_t = _t.getNextSibling();
							lhs=expr(_t);
							_t = _retTree;
							rhs=expr(_t);
							_t = _retTree;
							_t = __t168;
							_t = _t.getNextSibling();
							if ( inputState.guessing==0 ) {
									resultRegister = new BinaryOperation(lhs, "<", rhs, nextUniqueRegisterId++);
										
							}
						}
						else {
							boolean synPredMatched171 = false;
							if (_t==null) _t=ASTNULL;
							if (((_t.getType()==BINOP))) {
								AST __t171 = _t;
								synPredMatched171 = true;
								inputState.guessing++;
								try {
									{
									AST __t170 = _t;
									AST tmp33_AST_in = (AST)_t;
									match(_t,BINOP);
									_t = _t.getFirstChild();
									AST tmp34_AST_in = (AST)_t;
									match(_t,GT);
									_t = _t.getNextSibling();
									expr(_t);
									_t = _retTree;
									expr(_t);
									_t = _retTree;
									_t = __t170;
									_t = _t.getNextSibling();
									}
								}
								catch (RecognitionException pe) {
									synPredMatched171 = false;
								}
								_t = __t171;
inputState.guessing--;
							}
							if ( synPredMatched171 ) {
								AST __t172 = _t;
								AST tmp35_AST_in = (AST)_t;
								match(_t,BINOP);
								_t = _t.getFirstChild();
								AST tmp36_AST_in = (AST)_t;
								match(_t,GT);
								_t = _t.getNextSibling();
								lhs=expr(_t);
								_t = _retTree;
								rhs=expr(_t);
								_t = _retTree;
								_t = __t172;
								_t = _t.getNextSibling();
								if ( inputState.guessing==0 ) {
										resultRegister = new BinaryOperation(lhs, ">", rhs, nextUniqueRegisterId++);
											
								}
							}
							else {
								boolean synPredMatched175 = false;
								if (_t==null) _t=ASTNULL;
								if (((_t.getType()==BINOP))) {
									AST __t175 = _t;
									synPredMatched175 = true;
									inputState.guessing++;
									try {
										{
										AST __t174 = _t;
										AST tmp37_AST_in = (AST)_t;
										match(_t,BINOP);
										_t = _t.getFirstChild();
										AST tmp38_AST_in = (AST)_t;
										match(_t,NE);
										_t = _t.getNextSibling();
										expr(_t);
										_t = _retTree;
										expr(_t);
										_t = _retTree;
										_t = __t174;
										_t = _t.getNextSibling();
										}
									}
									catch (RecognitionException pe) {
										synPredMatched175 = false;
									}
									_t = __t175;
inputState.guessing--;
								}
								if ( synPredMatched175 ) {
									AST __t176 = _t;
									AST tmp39_AST_in = (AST)_t;
									match(_t,BINOP);
									_t = _t.getFirstChild();
									AST tmp40_AST_in = (AST)_t;
									match(_t,NE);
									_t = _t.getNextSibling();
									lhs=expr(_t);
									_t = _retTree;
									rhs=expr(_t);
									_t = _retTree;
									_t = __t176;
									_t = _t.getNextSibling();
									if ( inputState.guessing==0 ) {
											resultRegister = new BinaryOperation(lhs, "!=", rhs, nextUniqueRegisterId++);
												
									}
								}
								else {
									boolean synPredMatched179 = false;
									if (_t==null) _t=ASTNULL;
									if (((_t.getType()==BINOP))) {
										AST __t179 = _t;
										synPredMatched179 = true;
										inputState.guessing++;
										try {
											{
											AST __t178 = _t;
											AST tmp41_AST_in = (AST)_t;
											match(_t,BINOP);
											_t = _t.getFirstChild();
											AST tmp42_AST_in = (AST)_t;
											match(_t,LTE);
											_t = _t.getNextSibling();
											expr(_t);
											_t = _retTree;
											expr(_t);
											_t = _retTree;
											_t = __t178;
											_t = _t.getNextSibling();
											}
										}
										catch (RecognitionException pe) {
											synPredMatched179 = false;
										}
										_t = __t179;
inputState.guessing--;
									}
									if ( synPredMatched179 ) {
										AST __t180 = _t;
										AST tmp43_AST_in = (AST)_t;
										match(_t,BINOP);
										_t = _t.getFirstChild();
										AST tmp44_AST_in = (AST)_t;
										match(_t,LTE);
										_t = _t.getNextSibling();
										lhs=expr(_t);
										_t = _retTree;
										rhs=expr(_t);
										_t = _retTree;
										_t = __t180;
										_t = _t.getNextSibling();
										if ( inputState.guessing==0 ) {
												resultRegister = new BinaryOperation(lhs, "<=", rhs, nextUniqueRegisterId++);
													
										}
									}
									else {
										boolean synPredMatched183 = false;
										if (_t==null) _t=ASTNULL;
										if (((_t.getType()==BINOP))) {
											AST __t183 = _t;
											synPredMatched183 = true;
											inputState.guessing++;
											try {
												{
												AST __t182 = _t;
												AST tmp45_AST_in = (AST)_t;
												match(_t,BINOP);
												_t = _t.getFirstChild();
												AST tmp46_AST_in = (AST)_t;
												match(_t,GTE);
												_t = _t.getNextSibling();
												expr(_t);
												_t = _retTree;
												expr(_t);
												_t = _retTree;
												_t = __t182;
												_t = _t.getNextSibling();
												}
											}
											catch (RecognitionException pe) {
												synPredMatched183 = false;
											}
											_t = __t183;
inputState.guessing--;
										}
										if ( synPredMatched183 ) {
											AST __t184 = _t;
											AST tmp47_AST_in = (AST)_t;
											match(_t,BINOP);
											_t = _t.getFirstChild();
											AST tmp48_AST_in = (AST)_t;
											match(_t,GTE);
											_t = _t.getNextSibling();
											lhs=expr(_t);
											_t = _retTree;
											rhs=expr(_t);
											_t = _retTree;
											_t = __t184;
											_t = _t.getNextSibling();
											if ( inputState.guessing==0 ) {
													resultRegister = new BinaryOperation(lhs, ">=", rhs, nextUniqueRegisterId++);
														
											}
										}
										else {
											boolean synPredMatched187 = false;
											if (_t==null) _t=ASTNULL;
											if (((_t.getType()==BINOP))) {
												AST __t187 = _t;
												synPredMatched187 = true;
												inputState.guessing++;
												try {
													{
													AST __t186 = _t;
													AST tmp49_AST_in = (AST)_t;
													match(_t,BINOP);
													_t = _t.getFirstChild();
													AST tmp50_AST_in = (AST)_t;
													match(_t,PLUS);
													_t = _t.getNextSibling();
													expr(_t);
													_t = _retTree;
													expr(_t);
													_t = _retTree;
													_t = __t186;
													_t = _t.getNextSibling();
													}
												}
												catch (RecognitionException pe) {
													synPredMatched187 = false;
												}
												_t = __t187;
inputState.guessing--;
											}
											if ( synPredMatched187 ) {
												AST __t188 = _t;
												AST tmp51_AST_in = (AST)_t;
												match(_t,BINOP);
												_t = _t.getFirstChild();
												AST tmp52_AST_in = (AST)_t;
												match(_t,PLUS);
												_t = _t.getNextSibling();
												lhs=expr(_t);
												_t = _retTree;
												rhs=expr(_t);
												_t = _retTree;
												_t = __t188;
												_t = _t.getNextSibling();
												if ( inputState.guessing==0 ) {
														resultRegister = new BinaryOperation(lhs, "+", rhs, nextUniqueRegisterId++);
															
												}
											}
											else {
												boolean synPredMatched191 = false;
												if (_t==null) _t=ASTNULL;
												if (((_t.getType()==BINOP))) {
													AST __t191 = _t;
													synPredMatched191 = true;
													inputState.guessing++;
													try {
														{
														AST __t190 = _t;
														AST tmp53_AST_in = (AST)_t;
														match(_t,BINOP);
														_t = _t.getFirstChild();
														AST tmp54_AST_in = (AST)_t;
														match(_t,MINUS);
														_t = _t.getNextSibling();
														expr(_t);
														_t = _retTree;
														expr(_t);
														_t = _retTree;
														_t = __t190;
														_t = _t.getNextSibling();
														}
													}
													catch (RecognitionException pe) {
														synPredMatched191 = false;
													}
													_t = __t191;
inputState.guessing--;
												}
												if ( synPredMatched191 ) {
													AST __t192 = _t;
													AST tmp55_AST_in = (AST)_t;
													match(_t,BINOP);
													_t = _t.getFirstChild();
													AST tmp56_AST_in = (AST)_t;
													match(_t,MINUS);
													_t = _t.getNextSibling();
													lhs=expr(_t);
													_t = _retTree;
													rhs=expr(_t);
													_t = _retTree;
													_t = __t192;
													_t = _t.getNextSibling();
													if ( inputState.guessing==0 ) {
															resultRegister = new BinaryOperation(lhs, "-", rhs, nextUniqueRegisterId++);
																
													}
												}
												else {
													boolean synPredMatched195 = false;
													if (_t==null) _t=ASTNULL;
													if (((_t.getType()==BINOP))) {
														AST __t195 = _t;
														synPredMatched195 = true;
														inputState.guessing++;
														try {
															{
															AST __t194 = _t;
															AST tmp57_AST_in = (AST)_t;
															match(_t,BINOP);
															_t = _t.getFirstChild();
															AST tmp58_AST_in = (AST)_t;
															match(_t,TIMES);
															_t = _t.getNextSibling();
															expr(_t);
															_t = _retTree;
															expr(_t);
															_t = _retTree;
															_t = __t194;
															_t = _t.getNextSibling();
															}
														}
														catch (RecognitionException pe) {
															synPredMatched195 = false;
														}
														_t = __t195;
inputState.guessing--;
													}
													if ( synPredMatched195 ) {
														AST __t196 = _t;
														AST tmp59_AST_in = (AST)_t;
														match(_t,BINOP);
														_t = _t.getFirstChild();
														AST tmp60_AST_in = (AST)_t;
														match(_t,TIMES);
														_t = _t.getNextSibling();
														lhs=expr(_t);
														_t = _retTree;
														rhs=expr(_t);
														_t = _retTree;
														_t = __t196;
														_t = _t.getNextSibling();
														if ( inputState.guessing==0 ) {
																resultRegister = new BinaryOperation(lhs, "*", rhs, nextUniqueRegisterId++);
																	
														}
													}
													else {
														boolean synPredMatched199 = false;
														if (_t==null) _t=ASTNULL;
														if (((_t.getType()==BINOP))) {
															AST __t199 = _t;
															synPredMatched199 = true;
															inputState.guessing++;
															try {
																{
																AST __t198 = _t;
																AST tmp61_AST_in = (AST)_t;
																match(_t,BINOP);
																_t = _t.getFirstChild();
																AST tmp62_AST_in = (AST)_t;
																match(_t,DIVIDE);
																_t = _t.getNextSibling();
																expr(_t);
																_t = _retTree;
																expr(_t);
																_t = _retTree;
																_t = __t198;
																_t = _t.getNextSibling();
																}
															}
															catch (RecognitionException pe) {
																synPredMatched199 = false;
															}
															_t = __t199;
inputState.guessing--;
														}
														if ( synPredMatched199 ) {
															AST __t200 = _t;
															AST tmp63_AST_in = (AST)_t;
															match(_t,BINOP);
															_t = _t.getFirstChild();
															AST tmp64_AST_in = (AST)_t;
															match(_t,DIVIDE);
															_t = _t.getNextSibling();
															lhs=expr(_t);
															_t = _retTree;
															rhs=expr(_t);
															_t = _retTree;
															_t = __t200;
															_t = _t.getNextSibling();
															if ( inputState.guessing==0 ) {
																	resultRegister = new BinaryOperation(lhs, "/", rhs, nextUniqueRegisterId++);
																		
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
				AST __t202 = _t;
				AST tmp65_AST_in = (AST)_t;
				match(_t,CONST_INT);
				_t = _t.getFirstChild();
				i = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				_t = __t202;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new FInteger(new Integer(Integer.parseInt(i.toString())), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_FLOAT:
			{
				AST __t203 = _t;
				AST tmp66_AST_in = (AST)_t;
				match(_t,CONST_FLOAT);
				_t = _t.getFirstChild();
				f = (AST)_t;
				match(_t,FLOAT);
				_t = _t.getNextSibling();
				_t = __t203;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new FFloat(new Float(Float.parseFloat(f.toString())), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_IDENTIFIER:
			{
				AST __t212 = _t;
				AST tmp67_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getFirstChild();
				id = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t212;
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						constValue = new VarRef(id.toString(), nextUniqueRegisterId++);
							
				}
				break;
			}
			case CONST_STRING:
			{
				AST tmp68_AST_in = (AST)_t;
				match(_t,CONST_STRING);
				_t = _t.getNextSibling();
				str = (AST)_t;
				match(_t,STRING);
				_t = _t.getNextSibling();
				if ( inputState.guessing==0 ) {
						// constValue = new SObject(str);
							
				}
				break;
			}
			default:
				boolean synPredMatched206 = false;
				if (_t==null) _t=ASTNULL;
				if (((_t.getType()==CONST_BOOLEAN))) {
					AST __t206 = _t;
					synPredMatched206 = true;
					inputState.guessing++;
					try {
						{
						AST __t205 = _t;
						AST tmp69_AST_in = (AST)_t;
						match(_t,CONST_BOOLEAN);
						_t = _t.getFirstChild();
						AST tmp70_AST_in = (AST)_t;
						match(_t,TRUE);
						_t = _t.getNextSibling();
						_t = __t205;
						_t = _t.getNextSibling();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched206 = false;
					}
					_t = __t206;
inputState.guessing--;
				}
				if ( synPredMatched206 ) {
					AST __t207 = _t;
					AST tmp71_AST_in = (AST)_t;
					match(_t,CONST_BOOLEAN);
					_t = _t.getFirstChild();
					AST tmp72_AST_in = (AST)_t;
					match(_t,TRUE);
					_t = _t.getNextSibling();
					_t = __t207;
					_t = _t.getNextSibling();
					if ( inputState.guessing==0 ) {
							constValue = new FBoolean(new Boolean(true), nextUniqueRegisterId++);
								
					}
				}
				else {
					boolean synPredMatched210 = false;
					if (_t==null) _t=ASTNULL;
					if (((_t.getType()==CONST_BOOLEAN))) {
						AST __t210 = _t;
						synPredMatched210 = true;
						inputState.guessing++;
						try {
							{
							AST __t209 = _t;
							AST tmp73_AST_in = (AST)_t;
							match(_t,CONST_BOOLEAN);
							_t = _t.getFirstChild();
							AST tmp74_AST_in = (AST)_t;
							match(_t,FALSE);
							_t = _t.getNextSibling();
							_t = __t209;
							_t = _t.getNextSibling();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched210 = false;
						}
						_t = __t210;
inputState.guessing--;
					}
					if ( synPredMatched210 ) {
						AST __t211 = _t;
						AST tmp75_AST_in = (AST)_t;
						match(_t,CONST_BOOLEAN);
						_t = _t.getFirstChild();
						AST tmp76_AST_in = (AST)_t;
						match(_t,FALSE);
						_t = _t.getNextSibling();
						_t = __t211;
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
		
	public final void args(AST _t) throws RecognitionException {
		
		AST args_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if ( inputState.guessing==0 ) {
					throw new RecognitionException();
					
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
	
	public final void operator(AST _t) throws RecognitionException {
		
		AST operator_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case AND:
			{
				AST tmp77_AST_in = (AST)_t;
				match(_t,AND);
				_t = _t.getNextSibling();
				break;
			}
			case OR:
			{
				AST tmp78_AST_in = (AST)_t;
				match(_t,OR);
				_t = _t.getNextSibling();
				break;
			}
			case EQ:
			{
				AST tmp79_AST_in = (AST)_t;
				match(_t,EQ);
				_t = _t.getNextSibling();
				break;
			}
			case LT:
			{
				AST tmp80_AST_in = (AST)_t;
				match(_t,LT);
				_t = _t.getNextSibling();
				break;
			}
			case GT:
			{
				AST tmp81_AST_in = (AST)_t;
				match(_t,GT);
				_t = _t.getNextSibling();
				break;
			}
			case NE:
			{
				AST tmp82_AST_in = (AST)_t;
				match(_t,NE);
				_t = _t.getNextSibling();
				break;
			}
			case LTE:
			{
				AST tmp83_AST_in = (AST)_t;
				match(_t,LTE);
				_t = _t.getNextSibling();
				break;
			}
			case GTE:
			{
				AST tmp84_AST_in = (AST)_t;
				match(_t,GTE);
				_t = _t.getNextSibling();
				break;
			}
			case PLUS:
			{
				AST tmp85_AST_in = (AST)_t;
				match(_t,PLUS);
				_t = _t.getNextSibling();
				break;
			}
			case MINUS:
			{
				AST tmp86_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getNextSibling();
				break;
			}
			case TIMES:
			{
				AST tmp87_AST_in = (AST)_t;
				match(_t,TIMES);
				_t = _t.getNextSibling();
				break;
			}
			case DIVIDE:
			{
				AST tmp88_AST_in = (AST)_t;
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
		"FUNCTION_NAME",
		"FUNCTION_BODY",
		"FUNCTION_COLLECTION",
		"IDENTIFIER",
		"IFF",
		"INVOKE",
		"METHOD_CALL",
		"PROGRAM",
		"THENF"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 90912019433652528L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
