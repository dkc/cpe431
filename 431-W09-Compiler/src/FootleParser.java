// $ANTLR 2.7.7 (20060906): "src/footle.g" -> "FootleParser.java"$


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
			program_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROGRAM,"Program")).add(s_AST));
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
			AST tmp89_AST = null;
			tmp89_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp89_AST);
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
			AST tmp92_AST = null;
			tmp92_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp92_AST);
			match(RETURN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp94_AST = null;
			tmp94_AST = astFactory.create(LT(1));
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
				stmt_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(IFF,"IF")).add(exp1_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(THENF,"THEN")).add(s1_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ELSEF,"ELSE")).add(s2_AST))));
				currentAST.root = stmt_AST;
				currentAST.child = stmt_AST!=null &&stmt_AST.getFirstChild()!=null ?
					stmt_AST.getFirstChild() : stmt_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case WHILE:
		{
			AST tmp102_AST = null;
			tmp102_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp102_AST);
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
				stmt_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_COLLECTION,"Function Collection")).add(f_AST));
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
			identifier_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_IDENTIFIER,"Identifier")).add(id_AST));
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
		AST tmp110_AST = null;
		tmp110_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp110_AST);
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
				exprnr();
				match(DOT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched68 = false;
			}
			rewind(_m68);
inputState.guessing--;
		}
		if ( synPredMatched68 ) {
			exprfield();
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
					operator();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched70 = false;
				}
				rewind(_m70);
inputState.guessing--;
			}
			if ( synPredMatched70 ) {
				binexp();
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
			}
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
		
		AST tmp112_AST = null;
		tmp112_AST = astFactory.create(LT(1));
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
			
						function_AST = (AST)astFactory.make( (new ASTArray(4)).add(tmp112_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_NAME,"Function Name")).add(name_AST))).add(params_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_BODY,"Function Body")).add(body_AST))));
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
			_loop86:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop86;
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
		
		switch ( LA(1)) {
		case INT:
		{
			i = LT(1);
			i_AST = astFactory.create(i);
			match(INT);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_INT,"INT")).add(i_AST));
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
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_FLOAT,"FLOAT")).add(f_AST));
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
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_BOOLEAN,"BOOLEAN")).add(b1_AST));
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
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_BOOLEAN,"BOOLEAN")).add(b2_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case STRING:
		{
			s = LT(1);
			s_AST = astFactory.create(s);
			match(STRING);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONST_STRING,"STRING")).add(s_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				arglist();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SEMI:
			case COMMA:
			case RPAREN:
			case ASSIGN:
			case DOT:
			case AND:
			case OR:
			case EQ:
			case LT:
			case GT:
			case LTE:
			case GTE:
			case PLUS:
			case MINUS:
			case TIMES:
			case DIVIDE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NOT:
		{
			AST tmp120_AST = null;
			tmp120_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp120_AST);
			match(NOT);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp121_AST = null;
			tmp121_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp121_AST);
			match(NEW);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			arglist();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched80 = false;
			if (((LA(1)==ID))) {
				int _m80 = mark();
				synPredMatched80 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					arglist();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched80 = false;
				}
				rewind(_m80);
inputState.guessing--;
			}
			if ( synPredMatched80 ) {
				application();
				astFactory.addASTChild(currentAST, returnAST);
				exprnr_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID)) {
				identifier();
				id_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				exprnr_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = exprnr_AST;
	}
	
	public final void exprfield() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprfield_AST = null;
		AST exp_AST = null;
		AST id_AST = null;
		
		boolean synPredMatched73 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m73 = mark();
			synPredMatched73 = true;
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
				synPredMatched73 = false;
			}
			rewind(_m73);
inputState.guessing--;
		}
		if ( synPredMatched73 ) {
			exprmethodcall();
			astFactory.addASTChild(currentAST, returnAST);
			exprfield_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			exprnr();
			exp_AST = (AST)returnAST;
			AST tmp122_AST = null;
			tmp122_AST = astFactory.create(LT(1));
			match(DOT);
			identifier();
			id_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				exprfield_AST = (AST)currentAST.root;
				exprfield_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(FIELD_LOOKUP,"Field Lookup")).add(exp_AST).add(id_AST));
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
	
	public final void operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST operator_AST = null;
		
		switch ( LA(1)) {
		case AND:
		{
			AST tmp123_AST = null;
			tmp123_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp123_AST);
			match(AND);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp124_AST = null;
			tmp124_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp124_AST);
			match(OR);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case EQ:
		{
			AST tmp125_AST = null;
			tmp125_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp125_AST);
			match(EQ);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp126_AST = null;
			tmp126_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp126_AST);
			match(LT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp127_AST = null;
			tmp127_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp127_AST);
			match(GT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case NE:
		{
			AST tmp128_AST = null;
			tmp128_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp128_AST);
			match(NE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp129_AST = null;
			tmp129_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp129_AST);
			match(LTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp130_AST = null;
			tmp130_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp130_AST);
			match(GTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case PLUS:
		{
			AST tmp131_AST = null;
			tmp131_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp131_AST);
			match(PLUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp132_AST = null;
			tmp132_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp132_AST);
			match(MINUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case TIMES:
		{
			AST tmp133_AST = null;
			tmp133_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp133_AST);
			match(TIMES);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case DIVIDE:
		{
			AST tmp134_AST = null;
			tmp134_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp134_AST);
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
	
	public final void arglist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arglist_AST = null;
		
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
			_loop90:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop90;
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
		arglist_AST = (AST)currentAST.root;
		returnAST = arglist_AST;
	}
	
	public final void exprmethodcall() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprmethodcall_AST = null;
		AST expm_AST = null;
		AST id1_AST = null;
		AST a1_AST = null;
		
		boolean synPredMatched76 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m76 = mark();
			synPredMatched76 = true;
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
				synPredMatched76 = false;
			}
			rewind(_m76);
inputState.guessing--;
		}
		if ( synPredMatched76 ) {
			loneexprmethodcall();
			expm_AST = (AST)returnAST;
			AST tmp138_AST = null;
			tmp138_AST = astFactory.create(LT(1));
			match(DOT);
			identifier();
			id1_AST = (AST)returnAST;
			arglist();
			a1_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				exprmethodcall_AST = (AST)currentAST.root;
				exprmethodcall_AST  = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(METHOD_CALL,"Method Call")).add(expm_AST).add(id1_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGUMENTS,"Arguments")).add(a1_AST))));
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
		AST tmp139_AST = null;
		tmp139_AST = astFactory.create(LT(1));
		match(DOT);
		identifier();
		id_AST = (AST)returnAST;
		arglist();
		a_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			loneexprmethodcall_AST = (AST)currentAST.root;
			loneexprmethodcall_AST  = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(METHOD_CALL,"Method Call")).add(exp_AST).add(id_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGUMENTS,"Arguments")).add(a_AST))));
			currentAST.root = loneexprmethodcall_AST;
			currentAST.child = loneexprmethodcall_AST!=null &&loneexprmethodcall_AST.getFirstChild()!=null ?
				loneexprmethodcall_AST.getFirstChild() : loneexprmethodcall_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = loneexprmethodcall_AST;
	}
	
	public final void application() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST application_AST = null;
		AST id_AST = null;
		AST args_AST = null;
		
		identifier();
		id_AST = (AST)returnAST;
		arglist();
		args_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			application_AST = (AST)currentAST.root;
			application_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(INVOKE,"Invoke")).add(id_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGUMENTS,"Arguments")).add(args_AST))));
			currentAST.root = application_AST;
			currentAST.child = application_AST!=null &&application_AST.getFirstChild()!=null ?
				application_AST.getFirstChild() : application_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = application_AST;
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
		
		boolean synPredMatched94 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m94 = mark();
			synPredMatched94 = true;
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
				synPredMatched94 = false;
			}
			rewind(_m94);
inputState.guessing--;
		}
		if ( synPredMatched94 ) {
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
			boolean synPredMatched96 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m96 = mark();
				synPredMatched96 = true;
				inputState.guessing++;
				try {
					{
					binexplvl7();
					l8op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched96 = false;
				}
				rewind(_m96);
inputState.guessing--;
			}
			if ( synPredMatched96 ) {
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
		
		boolean synPredMatched100 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m100 = mark();
			synPredMatched100 = true;
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
				synPredMatched100 = false;
			}
			rewind(_m100);
inputState.guessing--;
		}
		if ( synPredMatched100 ) {
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
			boolean synPredMatched102 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m102 = mark();
				synPredMatched102 = true;
				inputState.guessing++;
				try {
					{
					binexplvl6();
					l7op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched102 = false;
				}
				rewind(_m102);
inputState.guessing--;
			}
			if ( synPredMatched102 ) {
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
		Token  op = null;
		AST op_AST = null;
		
		op = LT(1);
		op_AST = astFactory.create(op);
		astFactory.addASTChild(currentAST, op_AST);
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
		
		boolean synPredMatched106 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m106 = mark();
			synPredMatched106 = true;
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
				synPredMatched106 = false;
			}
			rewind(_m106);
inputState.guessing--;
		}
		if ( synPredMatched106 ) {
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
			boolean synPredMatched108 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m108 = mark();
				synPredMatched108 = true;
				inputState.guessing++;
				try {
					{
					binexplvl5();
					l6op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched108 = false;
				}
				rewind(_m108);
inputState.guessing--;
			}
			if ( synPredMatched108 ) {
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
			AST tmp140_AST = null;
			tmp140_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp140_AST);
			match(AND);
			l7op_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp141_AST = null;
			tmp141_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp141_AST);
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
		
		boolean synPredMatched112 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m112 = mark();
			synPredMatched112 = true;
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
				synPredMatched112 = false;
			}
			rewind(_m112);
inputState.guessing--;
		}
		if ( synPredMatched112 ) {
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
			boolean synPredMatched114 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m114 = mark();
				synPredMatched114 = true;
				inputState.guessing++;
				try {
					{
					binexplvl4();
					l5op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched114 = false;
				}
				rewind(_m114);
inputState.guessing--;
			}
			if ( synPredMatched114 ) {
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
			AST tmp142_AST = null;
			tmp142_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp142_AST);
			match(GT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp143_AST = null;
			tmp143_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp143_AST);
			match(GTE);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp144_AST = null;
			tmp144_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp144_AST);
			match(LT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp145_AST = null;
			tmp145_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp145_AST);
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
		
		boolean synPredMatched118 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m118 = mark();
			synPredMatched118 = true;
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
				synPredMatched118 = false;
			}
			rewind(_m118);
inputState.guessing--;
		}
		if ( synPredMatched118 ) {
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
			boolean synPredMatched120 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m120 = mark();
				synPredMatched120 = true;
				inputState.guessing++;
				try {
					{
					binexplvl3();
					l4op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched120 = false;
				}
				rewind(_m120);
inputState.guessing--;
			}
			if ( synPredMatched120 ) {
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
		
		AST tmp146_AST = null;
		tmp146_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp146_AST);
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
		
		boolean synPredMatched124 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m124 = mark();
			synPredMatched124 = true;
			inputState.guessing++;
			try {
				{
				exprnr();
				l3op();
				exprnr();
				l3op();
				}
			}
			catch (RecognitionException pe) {
				synPredMatched124 = false;
			}
			rewind(_m124);
inputState.guessing--;
		}
		if ( synPredMatched124 ) {
			exprnr();
			exp1_AST = (AST)returnAST;
			l3op();
			op1_AST = (AST)returnAST;
			exprnr();
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
			boolean synPredMatched126 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m126 = mark();
				synPredMatched126 = true;
				inputState.guessing++;
				try {
					{
					exprnr();
					l3op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched126 = false;
				}
				rewind(_m126);
inputState.guessing--;
			}
			if ( synPredMatched126 ) {
				exprnr();
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
			else {
				boolean synPredMatched128 = false;
				if (((_tokenSet_1.member(LA(1))))) {
					int _m128 = mark();
					synPredMatched128 = true;
					inputState.guessing++;
					try {
						{
						exprnr();
						match(DOT);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched128 = false;
					}
					rewind(_m128);
inputState.guessing--;
				}
				if ( synPredMatched128 ) {
					exprfield();
					astFactory.addASTChild(currentAST, returnAST);
					binexplvl3_AST = (AST)currentAST.root;
				}
				else if ((_tokenSet_1.member(LA(1)))) {
					exprnr();
					astFactory.addASTChild(currentAST, returnAST);
					binexplvl3_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}}
				returnAST = binexplvl3_AST;
			}
			
	public final void l4op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l4op_AST = null;
		
		switch ( LA(1)) {
		case PLUS:
		{
			AST tmp147_AST = null;
			tmp147_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp147_AST);
			match(PLUS);
			l4op_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp148_AST = null;
			tmp148_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp148_AST);
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
	
	public final void l3op() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST l3op_AST = null;
		
		AST tmp149_AST = null;
		tmp149_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp149_AST);
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
