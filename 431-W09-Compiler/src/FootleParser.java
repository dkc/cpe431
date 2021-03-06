// $ANTLR 2.7.7 (20060906): "431-W09-Compiler/src/footle.g" -> "FootleParser.java"$


import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

/*******************************************************************************
   Parser
*/
public class FootleParser extends antlr.LLkParser       implements FootleLexerTokenTypes
 {

	private int functionCounter = 0;

protected FootleParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FootleParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected FootleParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FootleParser(TokenStream lexer) {
  this(lexer,1);
}

public FootleParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void program() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST program_AST = null;
		AST s_AST = null;
		
		stmtlist();
		s_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			program_AST = (AST)currentAST.root;
			program_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROGRAM,"PROGRAM")).add(s_AST));
			currentAST.root = program_AST;
			currentAST.child = program_AST!=null &&program_AST.getFirstChild()!=null ?
				program_AST.getFirstChild() : program_AST;
			currentAST.advanceChildToEnd();
		}
		program_AST = (AST)currentAST.root;
		returnAST = program_AST;
	}
	
	public final void stmtlist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmtlist_AST = null;
		
		{
		_loop54:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				stmt();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop54;
			}
			
		} while (true);
		}
		stmtlist_AST = (AST)currentAST.root;
		returnAST = stmtlist_AST;
	}
	
	public final void stmt() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmt_AST = null;
		AST exp1_AST = null;
		AST s1_AST = null;
		AST s2_AST = null;
		AST f_AST = null;
		
		switch ( LA(1)) {
		case VAR:
		{
			AST tmp109_AST = null;
			tmp109_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp109_AST);
			match(VAR);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			match(ASSIGN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			AST tmp112_AST = null;
			tmp112_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp112_AST);
			match(RETURN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp114_AST = null;
			tmp114_AST = astFactory.create(LT(1));
			match(IF);
			match(LPAREN);
			expr();
			exp1_AST = (AST)returnAST;
			match(RPAREN);
			match(LBRACE);
			stmtlist();
			s1_AST = (AST)returnAST;
			match(RBRACE);
			{
			switch ( LA(1)) {
			case ELSE:
			{
				match(ELSE);
				match(LBRACE);
				stmtlist();
				s2_AST = (AST)returnAST;
				match(RBRACE);
				break;
			}
			case EOF:
			case VAR:
			case RETURN:
			case IF:
			case WHILE:
			case FUNCTION:
			case TRUE:
			case FALSE:
			case NEW:
			case RBRACE:
			case LPAREN:
			case NOT:
			case ID:
			case STRING:
			case INT:
			case FLOAT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			if ( inputState.guessing==0 ) {
				stmt_AST = (AST)currentAST.root;
				stmt_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(IFF,"IFF")).add(exp1_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(THENF,"THENF")).add(s1_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ELSEF,"ELSEF")).add(s2_AST))));
				currentAST.root = stmt_AST;
				currentAST.child = stmt_AST!=null &&stmt_AST.getFirstChild()!=null ?
					stmt_AST.getFirstChild() : stmt_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case WHILE:
		{
			AST tmp122_AST = null;
			tmp122_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp122_AST);
			match(WHILE);
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			stmtlist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RBRACE);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case FUNCTION:
		{
			functions();
			f_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				stmt_AST = (AST)currentAST.root;
				stmt_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_COLLECTION,"FUNCTION_COLLECTION")).add(f_AST));
				currentAST.root = stmt_AST;
				currentAST.child = stmt_AST!=null &&stmt_AST.getFirstChild()!=null ?
					stmt_AST.getFirstChild() : stmt_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
			boolean synPredMatched57 = false;
			if (((LA(1)==ID))) {
				int _m57 = mark();
				synPredMatched57 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					match(ASSIGN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched57 = false;
				}
				rewind(_m57);
inputState.guessing--;
			}
			if ( synPredMatched57 ) {
				stmtassign();
				astFactory.addASTChild(currentAST, returnAST);
				stmt_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case SEMI:
				{
					match(SEMI);
					break;
				}
				case ASSIGN:
				{
					{
					AST tmp128_AST = null;
					tmp128_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp128_AST);
					match(ASSIGN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					match(SEMI);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				stmt_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = stmt_AST;
	}
	
	public final void identifier() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST identifier_AST = null;
		Token  id = null;
		AST id_AST = null;
		
		id = LT(1);
		id_AST = astFactory.create(id);
		astFactory.addASTChild(currentAST, id_AST);
		match(ID);
		if ( inputState.guessing==0 ) {
			identifier_AST = (AST)currentAST.root;
			identifier_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_IDENTIFIER,"CONST_IDENTIFIER")).add(id_AST));
			currentAST.root = identifier_AST;
			currentAST.child = identifier_AST!=null &&identifier_AST.getFirstChild()!=null ?
				identifier_AST.getFirstChild() : identifier_AST;
			currentAST.advanceChildToEnd();
		}
		identifier_AST = (AST)currentAST.root;
		returnAST = identifier_AST;
	}
	
	public final void stmtassign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmtassign_AST = null;
		
		identifier();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp130_AST = null;
		tmp130_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp130_AST);
		match(ASSIGN);
		expr();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		stmtassign_AST = (AST)currentAST.root;
		returnAST = stmtassign_AST;
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		boolean synPredMatched68 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m68 = mark();
			synPredMatched68 = true;
			inputState.guessing++;
			try {
				{
				match(LPAREN);
				expr();
				match(RPAREN);
				arglist();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched68 = false;
			}
			rewind(_m68);
inputState.guessing--;
		}
		if ( synPredMatched68 ) {
			application();
			astFactory.addASTChild(currentAST, returnAST);
			expr_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched70 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m70 = mark();
				synPredMatched70 = true;
				inputState.guessing++;
				try {
					{
					exprnr();
					arglist();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched70 = false;
				}
				rewind(_m70);
inputState.guessing--;
			}
			if ( synPredMatched70 ) {
				application();
				astFactory.addASTChild(currentAST, returnAST);
				expr_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched72 = false;
				if (((_tokenSet_1.member(LA(1))))) {
					int _m72 = mark();
					synPredMatched72 = true;
					inputState.guessing++;
					try {
						{
						exprnr();
						match(DOT);
						identifier();
						operator();
						}
					}
					catch (RecognitionException pe) {
						synPredMatched72 = false;
					}
					rewind(_m72);
inputState.guessing--;
				}
				if ( synPredMatched72 ) {
					binexp();
					astFactory.addASTChild(currentAST, returnAST);
					expr_AST = (AST)currentAST.root;
				}
				else {
					boolean synPredMatched74 = false;
					if (((_tokenSet_1.member(LA(1))))) {
						int _m74 = mark();
						synPredMatched74 = true;
						inputState.guessing++;
						try {
							{
							exprnr();
							operator();
							}
						}
						catch (RecognitionException pe) {
							synPredMatched74 = false;
						}
						rewind(_m74);
inputState.guessing--;
					}
					if ( synPredMatched74 ) {
						binexp();
						astFactory.addASTChild(currentAST, returnAST);
						expr_AST = (AST)currentAST.root;
					}
					else {
						boolean synPredMatched76 = false;
						if (((_tokenSet_1.member(LA(1))))) {
							int _m76 = mark();
							synPredMatched76 = true;
							inputState.guessing++;
							try {
								{
								exprnr();
								match(DOT);
								}
							}
							catch (RecognitionException pe) {
								synPredMatched76 = false;
							}
							rewind(_m76);
inputState.guessing--;
						}
						if ( synPredMatched76 ) {
							exprfield();
							astFactory.addASTChild(currentAST, returnAST);
							expr_AST = (AST)currentAST.root;
						}
						else if ((_tokenSet_1.member(LA(1)))) {
							exprnr();
							astFactory.addASTChild(currentAST, returnAST);
							expr_AST = (AST)currentAST.root;
						}
						else {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}}}}
						returnAST = expr_AST;
					}
					
	public final void functions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST functions_AST = null;
		
		boolean synPredMatched64 = false;
		if (((LA(1)==FUNCTION))) {
			int _m64 = mark();
			synPredMatched64 = true;
			inputState.guessing++;
			try {
				{
				function();
				match(FUNCTION);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched64 = false;
			}
			rewind(_m64);
inputState.guessing--;
		}
		if ( synPredMatched64 ) {
			function();
			astFactory.addASTChild(currentAST, returnAST);
			functions();
			astFactory.addASTChild(currentAST, returnAST);
			functions_AST = (AST)currentAST.root;
		}
		else if ((LA(1)==FUNCTION)) {
			function();
			astFactory.addASTChild(currentAST, returnAST);
			functions_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = functions_AST;
	}
	
	public final void function() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_AST = null;
		AST name_AST = null;
		AST params_AST = null;
		AST body_AST = null;
		
		AST tmp132_AST = null;
		tmp132_AST = astFactory.create(LT(1));
		match(FUNCTION);
		identifier();
		name_AST = (AST)returnAST;
		paramlist();
		params_AST = (AST)returnAST;
		match(LBRACE);
		stmtlist();
		body_AST = (AST)returnAST;
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			function_AST = (AST)currentAST.root;
			
						function_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(FUNCTION_DEC,"FUNCTION_DEC")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_NAME,"FUNCTION_NAME")).add(name_AST))).add(params_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_BODY,"FUNCTION_BODY")).add(body_AST))));
						functionCounter++;
					
			currentAST.root = function_AST;
			currentAST.child = function_AST!=null &&function_AST.getFirstChild()!=null ?
				function_AST.getFirstChild() : function_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = function_AST;
	}
	
	public final void paramlist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paramlist_AST = null;
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case ID:
		{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop92:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop92;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		paramlist_AST = (AST)currentAST.root;
		returnAST = paramlist_AST;
	}
	
	public final void arglist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arglist_AST = null;
		AST args_AST = null;
		
		subarglist();
		args_AST = (AST)returnAST;
		astFactory.addASTChild(currentAST, returnAST);
		if ( inputState.guessing==0 ) {
			arglist_AST = (AST)currentAST.root;
				arglist_AST =  (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGUMENTS,"ARGUMENTS")).add(args_AST));
			currentAST.root = arglist_AST;
			currentAST.child = arglist_AST!=null &&arglist_AST.getFirstChild()!=null ?
				arglist_AST.getFirstChild() : arglist_AST;
			currentAST.advanceChildToEnd();
		}
		arglist_AST = (AST)currentAST.root;
		returnAST = arglist_AST;
	}
	
	public final void application() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST application_AST = null;
		AST app_AST = null;
		AST args2_AST = null;
		
		boolean synPredMatched87 = false;
		if (((LA(1)==LPAREN))) {
			int _m87 = mark();
			synPredMatched87 = true;
			inputState.guessing++;
			try {
				{
				match(LPAREN);
				applicationnr();
				match(RPAREN);
				arglist();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched87 = false;
			}
			rewind(_m87);
inputState.guessing--;
		}
		if ( synPredMatched87 ) {
			AST tmp138_AST = null;
			tmp138_AST = astFactory.create(LT(1));
			match(LPAREN);
			applicationnr();
			app_AST = (AST)returnAST;
			AST tmp139_AST = null;
			tmp139_AST = astFactory.create(LT(1));
			match(RPAREN);
			arglist();
			args2_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				application_AST = (AST)currentAST.root;
				application_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(INVOKE,"INVOKE")).add(app_AST).add(args2_AST));
				currentAST.root = application_AST;
				currentAST.child = application_AST!=null &&application_AST.getFirstChild()!=null ?
					application_AST.getFirstChild() : application_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			applicationnr();
			astFactory.addASTChild(currentAST, returnAST);
			application_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = application_AST;
	}
	
	public final void exprnr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprnr_AST = null;
		Token  i = null;
		AST i_AST = null;
		Token  f = null;
		AST f_AST = null;
		Token  b1 = null;
		AST b1_AST = null;
		Token  b2 = null;
		AST b2_AST = null;
		AST id_AST = null;
		Token  s = null;
		AST s_AST = null;
		Token  n = null;
		AST n_AST = null;
		AST id2_AST = null;
		AST args_AST = null;
		
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case INT:
		{
			i = LT(1);
			i_AST = astFactory.create(i);
			match(INT);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_INT,"CONST_INT")).add(i_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case FLOAT:
		{
			f = LT(1);
			f_AST = astFactory.create(f);
			match(FLOAT);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_FLOAT,"CONST_FLOAT")).add(f_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case TRUE:
		{
			b1 = LT(1);
			b1_AST = astFactory.create(b1);
			match(TRUE);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_BOOLEAN,"CONST_BOOLEAN")).add(b1_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case FALSE:
		{
			b2 = LT(1);
			b2_AST = astFactory.create(b2);
			match(FALSE);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_BOOLEAN,"CONST_BOOLEAN")).add(b2_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case ID:
		{
			identifier();
			id_AST = (AST)returnAST;
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			s = LT(1);
			s_AST = astFactory.create(s);
			match(STRING);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_STRING,"CONST_STRING")).add(s_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case NOT:
		{
			AST tmp142_AST = null;
			tmp142_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp142_AST);
			match(NOT);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			n = LT(1);
			n_AST = astFactory.create(n);
			match(NEW);
			identifier();
			id2_AST = (AST)returnAST;
			arglist();
			args_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(3)).add(n_AST).add(id2_AST).add(args_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = exprnr_AST;
	}
	
	public final void operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST operator_AST = null;
		
		switch ( LA(1)) {
		case AND:
		{
			AST tmp143_AST = null;
			tmp143_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp143_AST);
			match(AND);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp144_AST = null;
			tmp144_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp144_AST);
			match(OR);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case EQ:
		{
			AST tmp145_AST = null;
			tmp145_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp145_AST);
			match(EQ);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp146_AST = null;
			tmp146_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp146_AST);
			match(LT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp147_AST = null;
			tmp147_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp147_AST);
			match(GT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case NE:
		{
			AST tmp148_AST = null;
			tmp148_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp148_AST);
			match(NE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp149_AST = null;
			tmp149_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp149_AST);
			match(LTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp150_AST = null;
			tmp150_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp150_AST);
			match(GTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case PLUS:
		{
			AST tmp151_AST = null;
			tmp151_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp151_AST);
			match(PLUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp152_AST = null;
			tmp152_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp152_AST);
			match(MINUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case TIMES:
		{
			AST tmp153_AST = null;
			tmp153_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp153_AST);
			match(TIMES);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case DIVIDE:
		{
			AST tmp154_AST = null;
			tmp154_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp154_AST);
			match(DIVIDE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = operator_AST;
	}
	
	public final void binexp() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexp_AST = null;
		
		binexplvl8();
		astFactory.addASTChild(currentAST, returnAST);
		binexp_AST = (AST)currentAST.root;
		returnAST = binexp_AST;
	}
	
	public final void exprfield() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprfield_AST = null;
		AST exp_AST = null;
		AST id_AST = null;
		
		boolean synPredMatched79 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m79 = mark();
			synPredMatched79 = true;
			inputState.guessing++;
			try {
				{
				exprnr();
				match(DOT);
				identifier();
				arglist();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched79 = false;
			}
			rewind(_m79);
inputState.guessing--;
		}
		if ( synPredMatched79 ) {
			exprmethodcall();
			astFactory.addASTChild(currentAST, returnAST);
			exprfield_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			exprnr();
			exp_AST = (AST)returnAST;
			AST tmp155_AST = null;
			tmp155_AST = astFactory.create(LT(1));
			match(DOT);
			identifier();
			id_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				exprfield_AST = (AST)currentAST.root;
				exprfield_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(FIELD_LOOKUP,"FIELD_LOOKUP")).add(exp_AST).add(id_AST));
				currentAST.root = exprfield_AST;
				currentAST.child = exprfield_AST!=null &&exprfield_AST.getFirstChild()!=null ?
					exprfield_AST.getFirstChild() : exprfield_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = exprfield_AST;
	}
	
	public final void exprmethodcall() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprmethodcall_AST = null;
		AST expm_AST = null;
		AST id1_AST = null;
		AST a1_AST = null;
		
		boolean synPredMatched82 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m82 = mark();
			synPredMatched82 = true;
			inputState.guessing++;
			try {
				{
				exprnr();
				match(DOT);
				identifier();
				arglist();
				match(DOT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched82 = false;
			}
			rewind(_m82);
inputState.guessing--;
		}
		if ( synPredMatched82 ) {
			loneexprmethodcall();
			expm_AST = (AST)returnAST;
			AST tmp156_AST = null;
			tmp156_AST = astFactory.create(LT(1));
			match(DOT);
			identifier();
			id1_AST = (AST)returnAST;
			arglist();
			a1_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				exprmethodcall_AST = (AST)currentAST.root;
				exprmethodcall_AST  = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(METHOD_CALL,"METHOD_CALL")).add(expm_AST).add(id1_AST).add(a1_AST));
				currentAST.root = exprmethodcall_AST;
				currentAST.child = exprmethodcall_AST!=null &&exprmethodcall_AST.getFirstChild()!=null ?
					exprmethodcall_AST.getFirstChild() : exprmethodcall_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			loneexprmethodcall();
			astFactory.addASTChild(currentAST, returnAST);
			exprmethodcall_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = exprmethodcall_AST;
	}
	
	public final void loneexprmethodcall() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST loneexprmethodcall_AST = null;
		AST exp_AST = null;
		AST id_AST = null;
		AST a_AST = null;
		
		exprnr();
		exp_AST = (AST)returnAST;
		AST tmp157_AST = null;
		tmp157_AST = astFactory.create(LT(1));
		match(DOT);
		identifier();
		id_AST = (AST)returnAST;
		arglist();
		a_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			loneexprmethodcall_AST = (AST)currentAST.root;
			loneexprmethodcall_AST  = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(METHOD_CALL,"METHOD_CALL")).add(exp_AST).add(id_AST).add(a_AST));
			currentAST.root = loneexprmethodcall_AST;
			currentAST.child = loneexprmethodcall_AST!=null &&loneexprmethodcall_AST.getFirstChild()!=null ?
				loneexprmethodcall_AST.getFirstChild() : loneexprmethodcall_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = loneexprmethodcall_AST;
	}
	
	public final void applicationnr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST applicationnr_AST = null;
		AST id_AST = null;
		AST args_AST = null;
		
		exprnr();
		id_AST = (AST)returnAST;
		arglist();
		args_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			applicationnr_AST = (AST)currentAST.root;
			applicationnr_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(INVOKE,"INVOKE")).add(id_AST).add(args_AST));
			currentAST.root = applicationnr_AST;
			currentAST.child = applicationnr_AST!=null &&applicationnr_AST.getFirstChild()!=null ?
				applicationnr_AST.getFirstChild() : applicationnr_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = applicationnr_AST;
	}
	
	public final void subarglist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subarglist_AST = null;
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case TRUE:
		case FALSE:
		case NEW:
		case LPAREN:
		case NOT:
		case ID:
		case STRING:
		case INT:
		case FLOAT:
		{
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop97:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop97;
				}
				
			} while (true);
			}
			break;
		}
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAREN);
		subarglist_AST = (AST)currentAST.root;
		returnAST = subarglist_AST;
	}
	
	public final void binexplvl8() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl8_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched101 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m101 = mark();
			synPredMatched101 = true;
			inputState.guessing++;
			try {
				{
				binexplvl7();
				l8op();
				binexplvl7();
				l8op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched101 = false;
			}
			rewind(_m101);
inputState.guessing--;
		}
		if ( synPredMatched101 ) {
			binexplvl7();
			exp1_AST = (AST)returnAST;
			l8op();
			op1_AST = (AST)returnAST;
			binexplvl7();
			exp2_AST = (AST)returnAST;
			l8op();
			op2_AST = (AST)returnAST;
			binexplvl8();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl8_AST = (AST)currentAST.root;
					binexplvl8_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl8_AST;
				currentAST.child = binexplvl8_AST!=null &&binexplvl8_AST.getFirstChild()!=null ?
					binexplvl8_AST.getFirstChild() : binexplvl8_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched103 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m103 = mark();
				synPredMatched103 = true;
				inputState.guessing++;
				try {
					{
					binexplvl7();
					l8op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched103 = false;
				}
				rewind(_m103);
inputState.guessing--;
			}
			if ( synPredMatched103 ) {
				binexplvl7();
				exp4_AST = (AST)returnAST;
				l8op();
				op3_AST = (AST)returnAST;
				binexplvl8();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl8_AST = (AST)currentAST.root;
						binexplvl8_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl8_AST;
					currentAST.child = binexplvl8_AST!=null &&binexplvl8_AST.getFirstChild()!=null ?
						binexplvl8_AST.getFirstChild() : binexplvl8_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				binexplvl7();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl8_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl8_AST;
		}
		
	public final void binexplvl7() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl7_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched107 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m107 = mark();
			synPredMatched107 = true;
			inputState.guessing++;
			try {
				{
				binexplvl6();
				l7op();
				binexplvl6();
				l7op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched107 = false;
			}
			rewind(_m107);
inputState.guessing--;
		}
		if ( synPredMatched107 ) {
			binexplvl6();
			exp1_AST = (AST)returnAST;
			l7op();
			op1_AST = (AST)returnAST;
			binexplvl6();
			exp2_AST = (AST)returnAST;
			l7op();
			op2_AST = (AST)returnAST;
			binexplvl7();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl7_AST = (AST)currentAST.root;
					binexplvl7_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl7_AST;
				currentAST.child = binexplvl7_AST!=null &&binexplvl7_AST.getFirstChild()!=null ?
					binexplvl7_AST.getFirstChild() : binexplvl7_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched109 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m109 = mark();
				synPredMatched109 = true;
				inputState.guessing++;
				try {
					{
					binexplvl6();
					l7op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched109 = false;
				}
				rewind(_m109);
inputState.guessing--;
			}
			if ( synPredMatched109 ) {
				binexplvl6();
				exp4_AST = (AST)returnAST;
				l7op();
				op3_AST = (AST)returnAST;
				binexplvl7();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl7_AST = (AST)currentAST.root;
						binexplvl7_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl7_AST;
					currentAST.child = binexplvl7_AST!=null &&binexplvl7_AST.getFirstChild()!=null ?
						binexplvl7_AST.getFirstChild() : binexplvl7_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				binexplvl6();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl7_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl7_AST;
		}
		
	public final void l8op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l8op_AST = null;
		
		AST tmp161_AST = null;
		tmp161_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp161_AST);
		match(EQ);
		l8op_AST = (AST)currentAST.root;
		returnAST = l8op_AST;
	}
	
	public final void binexplvl6() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl6_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched113 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m113 = mark();
			synPredMatched113 = true;
			inputState.guessing++;
			try {
				{
				binexplvl5();
				l6op();
				binexplvl5();
				l6op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched113 = false;
			}
			rewind(_m113);
inputState.guessing--;
		}
		if ( synPredMatched113 ) {
			binexplvl5();
			exp1_AST = (AST)returnAST;
			l6op();
			op1_AST = (AST)returnAST;
			binexplvl5();
			exp2_AST = (AST)returnAST;
			l6op();
			op2_AST = (AST)returnAST;
			binexplvl6();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl6_AST = (AST)currentAST.root;
					binexplvl6_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl6_AST;
				currentAST.child = binexplvl6_AST!=null &&binexplvl6_AST.getFirstChild()!=null ?
					binexplvl6_AST.getFirstChild() : binexplvl6_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched115 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m115 = mark();
				synPredMatched115 = true;
				inputState.guessing++;
				try {
					{
					binexplvl5();
					l6op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched115 = false;
				}
				rewind(_m115);
inputState.guessing--;
			}
			if ( synPredMatched115 ) {
				binexplvl5();
				exp4_AST = (AST)returnAST;
				l6op();
				op3_AST = (AST)returnAST;
				binexplvl6();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl6_AST = (AST)currentAST.root;
						binexplvl6_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl6_AST;
					currentAST.child = binexplvl6_AST!=null &&binexplvl6_AST.getFirstChild()!=null ?
						binexplvl6_AST.getFirstChild() : binexplvl6_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				binexplvl5();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl6_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl6_AST;
		}
		
	public final void l7op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l7op_AST = null;
		
		switch ( LA(1)) {
		case AND:
		{
			AST tmp162_AST = null;
			tmp162_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp162_AST);
			match(AND);
			l7op_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp163_AST = null;
			tmp163_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp163_AST);
			match(OR);
			l7op_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = l7op_AST;
	}
	
	public final void binexplvl5() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl5_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched119 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m119 = mark();
			synPredMatched119 = true;
			inputState.guessing++;
			try {
				{
				binexplvl4();
				l5op();
				binexplvl4();
				l5op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched119 = false;
			}
			rewind(_m119);
inputState.guessing--;
		}
		if ( synPredMatched119 ) {
			binexplvl4();
			exp1_AST = (AST)returnAST;
			l5op();
			op1_AST = (AST)returnAST;
			binexplvl4();
			exp2_AST = (AST)returnAST;
			l5op();
			op2_AST = (AST)returnAST;
			binexplvl5();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl5_AST = (AST)currentAST.root;
					binexplvl5_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl5_AST;
				currentAST.child = binexplvl5_AST!=null &&binexplvl5_AST.getFirstChild()!=null ?
					binexplvl5_AST.getFirstChild() : binexplvl5_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched121 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m121 = mark();
				synPredMatched121 = true;
				inputState.guessing++;
				try {
					{
					binexplvl4();
					l5op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched121 = false;
				}
				rewind(_m121);
inputState.guessing--;
			}
			if ( synPredMatched121 ) {
				binexplvl4();
				exp4_AST = (AST)returnAST;
				l5op();
				op3_AST = (AST)returnAST;
				binexplvl5();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl5_AST = (AST)currentAST.root;
						binexplvl5_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl5_AST;
					currentAST.child = binexplvl5_AST!=null &&binexplvl5_AST.getFirstChild()!=null ?
						binexplvl5_AST.getFirstChild() : binexplvl5_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				binexplvl4();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl5_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl5_AST;
		}
		
	public final void l6op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l6op_AST = null;
		
		switch ( LA(1)) {
		case GT:
		{
			AST tmp164_AST = null;
			tmp164_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp164_AST);
			match(GT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp165_AST = null;
			tmp165_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp165_AST);
			match(GTE);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp166_AST = null;
			tmp166_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp166_AST);
			match(LT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp167_AST = null;
			tmp167_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp167_AST);
			match(LTE);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = l6op_AST;
	}
	
	public final void binexplvl4() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl4_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched125 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m125 = mark();
			synPredMatched125 = true;
			inputState.guessing++;
			try {
				{
				binexplvl3();
				l4op();
				binexplvl3();
				l4op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched125 = false;
			}
			rewind(_m125);
inputState.guessing--;
		}
		if ( synPredMatched125 ) {
			binexplvl3();
			exp1_AST = (AST)returnAST;
			l4op();
			op1_AST = (AST)returnAST;
			binexplvl3();
			exp2_AST = (AST)returnAST;
			l4op();
			op2_AST = (AST)returnAST;
			binexplvl4();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl4_AST = (AST)currentAST.root;
					binexplvl4_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl4_AST;
				currentAST.child = binexplvl4_AST!=null &&binexplvl4_AST.getFirstChild()!=null ?
					binexplvl4_AST.getFirstChild() : binexplvl4_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched127 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m127 = mark();
				synPredMatched127 = true;
				inputState.guessing++;
				try {
					{
					binexplvl3();
					l4op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched127 = false;
				}
				rewind(_m127);
inputState.guessing--;
			}
			if ( synPredMatched127 ) {
				binexplvl3();
				exp4_AST = (AST)returnAST;
				l4op();
				op3_AST = (AST)returnAST;
				binexplvl4();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl4_AST = (AST)currentAST.root;
						binexplvl4_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl4_AST;
					currentAST.child = binexplvl4_AST!=null &&binexplvl4_AST.getFirstChild()!=null ?
						binexplvl4_AST.getFirstChild() : binexplvl4_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				binexplvl3();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl4_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl4_AST;
		}
		
	public final void l5op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l5op_AST = null;
		
		AST tmp168_AST = null;
		tmp168_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp168_AST);
		match(DIVIDE);
		l5op_AST = (AST)currentAST.root;
		returnAST = l5op_AST;
	}
	
	public final void binexplvl3() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST binexplvl3_AST = null;
		AST exp1_AST = null;
		AST op1_AST = null;
		AST exp2_AST = null;
		AST op2_AST = null;
		AST exp3_AST = null;
		AST exp4_AST = null;
		AST op3_AST = null;
		AST exp5_AST = null;
		
		boolean synPredMatched131 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m131 = mark();
			synPredMatched131 = true;
			inputState.guessing++;
			try {
				{
				exprnr2();
				l3op();
				exprnr2();
				l3op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched131 = false;
			}
			rewind(_m131);
inputState.guessing--;
		}
		if ( synPredMatched131 ) {
			exprnr2();
			exp1_AST = (AST)returnAST;
			l3op();
			op1_AST = (AST)returnAST;
			exprnr2();
			exp2_AST = (AST)returnAST;
			l3op();
			op2_AST = (AST)returnAST;
			binexplvl3();
			exp3_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				binexplvl3_AST = (AST)currentAST.root;
					binexplvl3_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op2_AST).add((AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl3_AST;
				currentAST.child = binexplvl3_AST!=null &&binexplvl3_AST.getFirstChild()!=null ?
					binexplvl3_AST.getFirstChild() : binexplvl3_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched133 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m133 = mark();
				synPredMatched133 = true;
				inputState.guessing++;
				try {
					{
					exprnr2();
					l3op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched133 = false;
				}
				rewind(_m133);
inputState.guessing--;
			}
			if ( synPredMatched133 ) {
				exprnr2();
				exp4_AST = (AST)returnAST;
				l3op();
				op3_AST = (AST)returnAST;
				binexplvl3();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl3_AST = (AST)currentAST.root;
						binexplvl3_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(BINOP,"BINOP")).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl3_AST;
					currentAST.child = binexplvl3_AST!=null &&binexplvl3_AST.getFirstChild()!=null ?
						binexplvl3_AST.getFirstChild() : binexplvl3_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				exprnr2();
				astFactory.addASTChild(currentAST, returnAST);
				binexplvl3_AST = (AST)currentAST.root;
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			returnAST = binexplvl3_AST;
		}
		
	public final void l4op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l4op_AST = null;
		
		switch ( LA(1)) {
		case PLUS:
		{
			AST tmp169_AST = null;
			tmp169_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp169_AST);
			match(PLUS);
			l4op_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp170_AST = null;
			tmp170_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp170_AST);
			match(MINUS);
			l4op_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = l4op_AST;
	}
	
	public final void exprnr2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprnr2_AST = null;
		
		boolean synPredMatched137 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m137 = mark();
			synPredMatched137 = true;
			inputState.guessing++;
			try {
				{
				exprnr();
				match(DOT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched137 = false;
			}
			rewind(_m137);
inputState.guessing--;
		}
		if ( synPredMatched137 ) {
			exprfield();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr2_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			exprnr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr2_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = exprnr2_AST;
	}
	
	public final void l3op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l3op_AST = null;
		
		AST tmp171_AST = null;
		tmp171_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp171_AST);
		match(TIMES);
		l3op_AST = (AST)currentAST.root;
		returnAST = l3op_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 257700274032L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 257700273152L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
