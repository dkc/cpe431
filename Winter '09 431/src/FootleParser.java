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

public class FootleParser extends antlr.LLkParser       implements FootleLexerTokenTypes
 {

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
		Token  name = null;
		AST name_AST = null;
		AST params_AST = null;
		AST body_AST = null;
		
		switch ( LA(1)) {
		case VAR:
		{
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp1_AST);
			match(VAR);
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(ID);
			match(ASSIGN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp5_AST);
			match(RETURN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp7_AST = null;
			tmp7_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp7_AST);
			match(IF);
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			stmtlist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RBRACE);
			{
			switch ( LA(1)) {
			case ELSE:
			{
				match(ELSE);
				match(LBRACE);
				stmtlist();
				astFactory.addASTChild(currentAST, returnAST);
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
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp15_AST);
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
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			match(FUNCTION);
			name = LT(1);
			name_AST = astFactory.create(name);
			match(ID);
			match(LPAREN);
			paramlist();
			params_AST = (AST)returnAST;
			match(RPAREN);
			match(LBRACE);
			stmtlist();
			body_AST = (AST)returnAST;
			match(RBRACE);
			if ( inputState.guessing==0 ) {
				stmt_AST = (AST)currentAST.root;
				stmt_AST = (AST)astFactory.make( (new ASTArray(4)).add(tmp20_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_NAME,"FUNCTION_NAME")).add(name_AST))).add(params_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_BODY,"FUNCTION_BODY")).add(body_AST))));
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
					match(ID);
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
				case DOT:
				{
					{
					match(DOT);
					AST tmp27_AST = null;
					tmp27_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp27_AST);
					match(ID);
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
	
	public final void stmtassign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmtassign_AST = null;
		
		AST tmp30_AST = null;
		tmp30_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp30_AST);
		match(ID);
		AST tmp31_AST = null;
		tmp31_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp31_AST);
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
		
		boolean synPredMatched64 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m64 = mark();
			synPredMatched64 = true;
			inputState.guessing++;
			try {
				{
				exprnr();
				match(DOT);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched64 = false;
			}
			rewind(_m64);
inputState.guessing--;
		}
		if ( synPredMatched64 ) {
			exprfield();
			astFactory.addASTChild(currentAST, returnAST);
			expr_AST = (AST)currentAST.root;
		}
		else {
			boolean synPredMatched66 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m66 = mark();
				synPredMatched66 = true;
				inputState.guessing++;
				try {
					{
					exprnr();
					operator();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched66 = false;
				}
				rewind(_m66);
inputState.guessing--;
			}
			if ( synPredMatched66 ) {
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
		
	public final void paramlist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paramlist_AST = null;
		
		{
		switch ( LA(1)) {
		case ID:
		{
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(ID);
			{
			_loop79:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp34_AST = null;
					tmp34_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp34_AST);
					match(COMMA);
					AST tmp35_AST = null;
					tmp35_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp35_AST);
					match(ID);
				}
				else {
					break _loop79;
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
		paramlist_AST = (AST)currentAST.root;
		returnAST = paramlist_AST;
	}
	
	public final void exprnr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprnr_AST = null;
		Token  s = null;
		AST s_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			AST tmp36_AST = null;
			tmp36_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp36_AST);
			match(INT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FLOAT:
		{
			AST tmp37_AST = null;
			tmp37_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp37_AST);
			match(FLOAT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		{
			AST tmp38_AST = null;
			tmp38_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp38_AST);
			match(TRUE);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FALSE:
		{
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp39_AST);
			match(FALSE);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			s = LT(1);
			s_AST = astFactory.create(s);
			astFactory.addASTChild(currentAST, s_AST);
			match(STRING);
			if ( inputState.guessing==0 ) {
				exprnr_AST = (AST)currentAST.root;
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STRING,"STRING")).add(s_AST));
				currentAST.root = exprnr_AST;
				currentAST.child = exprnr_AST!=null &&exprnr_AST.getFirstChild()!=null ?
					exprnr_AST.getFirstChild() : exprnr_AST;
				currentAST.advanceChildToEnd();
			}
			exprnr_AST = (AST)currentAST.root;
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
				match(LPAREN);
				arglist();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
				break;
			}
			case SEMI:
			case COMMA:
			case RPAREN:
			case DOT:
			case AND:
			case OR:
			case EQ:
			case LT:
			case GT:
			case NE:
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
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp44_AST);
			match(NOT);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp45_AST);
			match(NEW);
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(ID);
			{
			match(LPAREN);
			arglist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			}
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched72 = false;
			if (((LA(1)==ID))) {
				int _m72 = mark();
				synPredMatched72 = true;
				inputState.guessing++;
				try {
					{
					match(ID);
					arglist();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched72 = false;
				}
				rewind(_m72);
inputState.guessing--;
			}
			if ( synPredMatched72 ) {
				application();
				astFactory.addASTChild(currentAST, returnAST);
				exprnr_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID)) {
				AST tmp49_AST = null;
				tmp49_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp49_AST);
				match(ID);
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
		
		exprnr();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp50_AST = null;
		tmp50_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp50_AST);
		match(DOT);
		AST tmp51_AST = null;
		tmp51_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp51_AST);
		match(ID);
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			arglist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			break;
		}
		case SEMI:
		case COMMA:
		case RPAREN:
		case DOT:
		case AND:
		case OR:
		case EQ:
		case LT:
		case GT:
		case NE:
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
		exprfield_AST = (AST)currentAST.root;
		returnAST = exprfield_AST;
	}
	
	public final void operator() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST operator_AST = null;
		
		switch ( LA(1)) {
		case AND:
		{
			AST tmp54_AST = null;
			tmp54_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp54_AST);
			match(AND);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp55_AST = null;
			tmp55_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp55_AST);
			match(OR);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case EQ:
		{
			AST tmp56_AST = null;
			tmp56_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp56_AST);
			match(EQ);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp57_AST = null;
			tmp57_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp57_AST);
			match(LT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp58_AST = null;
			tmp58_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp58_AST);
			match(GT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case NE:
		{
			AST tmp59_AST = null;
			tmp59_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp59_AST);
			match(NE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp60_AST = null;
			tmp60_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp60_AST);
			match(LTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp61_AST = null;
			tmp61_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp61_AST);
			match(GTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case PLUS:
		{
			AST tmp62_AST = null;
			tmp62_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp62_AST);
			match(PLUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp63_AST);
			match(MINUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case TIMES:
		{
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
			match(TIMES);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case DIVIDE:
		{
			AST tmp65_AST = null;
			tmp65_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp65_AST);
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
		
		exprnr();
		astFactory.addASTChild(currentAST, returnAST);
		operator();
		astFactory.addASTChild(currentAST, returnAST);
		exprnr();
		astFactory.addASTChild(currentAST, returnAST);
		binexp_AST = (AST)currentAST.root;
		returnAST = binexp_AST;
	}
	
	public final void arglist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arglist_AST = null;
		
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
			_loop83:
			do {
				if ((LA(1)==COMMA)) {
					AST tmp66_AST = null;
					tmp66_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp66_AST);
					match(COMMA);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop83;
				}
				
			} while (true);
			}
			break;
		}
		case SEMI:
		case COMMA:
		case RPAREN:
		case DOT:
		case AND:
		case OR:
		case EQ:
		case LT:
		case GT:
		case NE:
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
		arglist_AST = (AST)currentAST.root;
		returnAST = arglist_AST;
	}
	
	public final void application() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST application_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST a_AST = null;
		
		id = LT(1);
		id_AST = astFactory.create(id);
		match(ID);
		arglist();
		a_AST = (AST)returnAST;
		if ( inputState.guessing==0 ) {
			application_AST = (AST)currentAST.root;
			application_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(INVOKE,"INVOKE")).add(id_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGLIST,"ARGLIST")).add(a_AST))));
			currentAST.root = application_AST;
			currentAST.child = application_AST!=null &&application_AST.getFirstChild()!=null ?
				application_AST.getFirstChild() : application_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = application_AST;
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
		"PROGRAM",
		"TYPES",
		"TYPE",
		"DECLS",
		"FUNCS",
		"DECL",
		"DECLLIST",
		"PARAMS",
		"RETTYPE",
		"BLOCK",
		"STMTS",
		"INVOKE",
		"ARGS",
		"NEG",
		"ARGLIST",
		"FUNCTION_NAME",
		"FUNCTION_BODY"
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
