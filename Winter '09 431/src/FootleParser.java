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
		AST name_AST = null;
		AST params_AST = null;
		AST body_AST = null;
		System.out.println("stmt");
		
		switch ( LA(1)) {
		case VAR:
		{
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp1_AST);
			match(VAR);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			match(ASSIGN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
					System.out.println("stmt:var dec");
						
			}
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			AST tmp4_AST = null;
			tmp4_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp4_AST);
			match(RETURN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			if ( inputState.guessing==0 ) {
					System.out.println("stmt:return");
						
			}
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp6_AST);
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
			if ( inputState.guessing==0 ) {
					System.out.println("stmt:if");
						
			}
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			AST tmp14_AST = null;
			tmp14_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp14_AST);
			match(WHILE);
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			stmtlist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RBRACE);
			if ( inputState.guessing==0 ) {
					System.out.println("stmt:while");
						
			}
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case FUNCTION:
		{
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
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
				stmt_AST = (AST)currentAST.root;
					System.out.println("stmt:function dec");
							stmt_AST = (AST)astFactory.make( (new ASTArray(4)).add(tmp19_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_NAME,"Function Name")).add(name_AST))).add(params_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCTION_BODY,"Function Body")).add(body_AST))));
						
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
				if ( inputState.guessing==0 ) {
						System.out.println("stmt:assignment");
							
				}
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
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
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
				if ( inputState.guessing==0 ) {
						System.out.println("stmt:expression");
							
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
			identifier_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ID,"Identifier")).add(id_AST));
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
		AST tmp26_AST = null;
		tmp26_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp26_AST);
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
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case ID:
		{
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop77:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					identifier();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop77;
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
		Token  s = null;
		AST s_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			AST tmp31_AST = null;
			tmp31_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp31_AST);
			match(INT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FLOAT:
		{
			AST tmp32_AST = null;
			tmp32_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp32_AST);
			match(FLOAT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		{
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(TRUE);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FALSE:
		{
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
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
				exprnr_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STRING,"String")).add(s_AST));
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
				arglist();
				astFactory.addASTChild(currentAST, returnAST);
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
			AST tmp37_AST = null;
			tmp37_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp37_AST);
			match(NOT);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp38_AST = null;
			tmp38_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp38_AST);
			match(NEW);
			identifier();
			astFactory.addASTChild(currentAST, returnAST);
			arglist();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched71 = false;
			if (((LA(1)==ID))) {
				int _m71 = mark();
				synPredMatched71 = true;
				inputState.guessing++;
				try {
					{
					identifier();
					arglist();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched71 = false;
				}
				rewind(_m71);
inputState.guessing--;
			}
			if ( synPredMatched71 ) {
				application();
				astFactory.addASTChild(currentAST, returnAST);
				exprnr_AST = (AST)currentAST.root;
			}
			else if ((LA(1)==ID)) {
				identifier();
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
		AST a_AST = null;
		
		exprnr();
		exp_AST = (AST)returnAST;
		AST tmp39_AST = null;
		tmp39_AST = astFactory.create(LT(1));
		match(DOT);
		identifier();
		id_AST = (AST)returnAST;
		{
		switch ( LA(1)) {
		case LPAREN:
		{
			arglist();
			a_AST = (AST)returnAST;
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
		if ( inputState.guessing==0 ) {
			exprfield_AST = (AST)currentAST.root;
			exprfield_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(FIELD_LOOKUP,"Field Lookup")).add(exp_AST).add(id_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGUMENTS,"Arguments")).add(a_AST))));
			currentAST.root = exprfield_AST;
			currentAST.child = exprfield_AST!=null &&exprfield_AST.getFirstChild()!=null ?
				exprfield_AST.getFirstChild() : exprfield_AST;
			currentAST.advanceChildToEnd();
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
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(AND);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp41_AST = null;
			tmp41_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp41_AST);
			match(OR);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case EQ:
		{
			AST tmp42_AST = null;
			tmp42_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp42_AST);
			match(EQ);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp43_AST = null;
			tmp43_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp43_AST);
			match(LT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GT:
		{
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(GT);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case NE:
		{
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(NE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(LTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp47_AST = null;
			tmp47_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp47_AST);
			match(GTE);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case PLUS:
		{
			AST tmp48_AST = null;
			tmp48_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(PLUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp49_AST = null;
			tmp49_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp49_AST);
			match(MINUS);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case TIMES:
		{
			AST tmp50_AST = null;
			tmp50_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp50_AST);
			match(TIMES);
			operator_AST = (AST)currentAST.root;
			break;
		}
		case DIVIDE:
		{
			AST tmp51_AST = null;
			tmp51_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp51_AST);
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
		if ( inputState.guessing==0 ) {
			System.out.println("binexp");
		}
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
			_loop81:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop81;
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
		
		boolean synPredMatched85 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m85 = mark();
			synPredMatched85 = true;
			inputState.guessing++;
			try {
				{
				binexplvl7();
				match(EQ);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched85 = false;
			}
			rewind(_m85);
inputState.guessing--;
		}
		if ( synPredMatched85 ) {
			binexplvl7();
			astFactory.addASTChild(currentAST, returnAST);
			AST tmp55_AST = null;
			tmp55_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp55_AST);
			match(EQ);
			binexplvl8();
			astFactory.addASTChild(currentAST, returnAST);
			binexplvl8_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_1.member(LA(1)))) {
			binexplvl7();
			astFactory.addASTChild(currentAST, returnAST);
			binexplvl8_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
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
		
		boolean synPredMatched88 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m88 = mark();
			synPredMatched88 = true;
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
				synPredMatched88 = false;
			}
			rewind(_m88);
inputState.guessing--;
		}
		if ( synPredMatched88 ) {
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
					binexplvl7_AST = (AST)astFactory.make( (new ASTArray(3)).add(op2_AST).add((AST)astFactory.make( (new ASTArray(3)).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl7_AST;
				currentAST.child = binexplvl7_AST!=null &&binexplvl7_AST.getFirstChild()!=null ?
					binexplvl7_AST.getFirstChild() : binexplvl7_AST;
				currentAST.advanceChildToEnd();
			}
		}
		else {
			boolean synPredMatched90 = false;
			if (((_tokenSet_1.member(LA(1))))) {
				int _m90 = mark();
				synPredMatched90 = true;
				inputState.guessing++;
				try {
					{
					binexplvl6();
					l7op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched90 = false;
				}
				rewind(_m90);
inputState.guessing--;
			}
			if ( synPredMatched90 ) {
				binexplvl6();
				exp4_AST = (AST)returnAST;
				l7op();
				op3_AST = (AST)returnAST;
				binexplvl7();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl7_AST = (AST)currentAST.root;
						binexplvl7_AST = (AST)astFactory.make( (new ASTArray(3)).add(op3_AST).add(exp4_AST).add(exp5_AST));
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
		
		boolean synPredMatched94 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m94 = mark();
			synPredMatched94 = true;
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
				synPredMatched94 = false;
			}
			rewind(_m94);
inputState.guessing--;
		}
		if ( synPredMatched94 ) {
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
					binexplvl6_AST = (AST)astFactory.make( (new ASTArray(3)).add(op2_AST).add((AST)astFactory.make( (new ASTArray(3)).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl6_AST;
				currentAST.child = binexplvl6_AST!=null &&binexplvl6_AST.getFirstChild()!=null ?
					binexplvl6_AST.getFirstChild() : binexplvl6_AST;
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
					binexplvl5();
					l6op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched96 = false;
				}
				rewind(_m96);
inputState.guessing--;
			}
			if ( synPredMatched96 ) {
				binexplvl5();
				exp4_AST = (AST)returnAST;
				l6op();
				op3_AST = (AST)returnAST;
				binexplvl6();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl6_AST = (AST)currentAST.root;
						binexplvl6_AST = (AST)astFactory.make( (new ASTArray(3)).add(op3_AST).add(exp4_AST).add(exp5_AST));
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
			AST tmp56_AST = null;
			tmp56_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp56_AST);
			match(AND);
			l7op_AST = (AST)currentAST.root;
			break;
		}
		case OR:
		{
			AST tmp57_AST = null;
			tmp57_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp57_AST);
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
		
		boolean synPredMatched100 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m100 = mark();
			synPredMatched100 = true;
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
				synPredMatched100 = false;
			}
			rewind(_m100);
inputState.guessing--;
		}
		if ( synPredMatched100 ) {
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
					binexplvl5_AST = (AST)astFactory.make( (new ASTArray(3)).add(op2_AST).add((AST)astFactory.make( (new ASTArray(3)).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl5_AST;
				currentAST.child = binexplvl5_AST!=null &&binexplvl5_AST.getFirstChild()!=null ?
					binexplvl5_AST.getFirstChild() : binexplvl5_AST;
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
					binexplvl4();
					l5op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched102 = false;
				}
				rewind(_m102);
inputState.guessing--;
			}
			if ( synPredMatched102 ) {
				binexplvl4();
				exp4_AST = (AST)returnAST;
				l5op();
				op3_AST = (AST)returnAST;
				binexplvl5();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl5_AST = (AST)currentAST.root;
						binexplvl5_AST = (AST)astFactory.make( (new ASTArray(3)).add(op3_AST).add(exp4_AST).add(exp5_AST));
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
			AST tmp58_AST = null;
			tmp58_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp58_AST);
			match(GT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case GTE:
		{
			AST tmp59_AST = null;
			tmp59_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp59_AST);
			match(GTE);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LT:
		{
			AST tmp60_AST = null;
			tmp60_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp60_AST);
			match(LT);
			l6op_AST = (AST)currentAST.root;
			break;
		}
		case LTE:
		{
			AST tmp61_AST = null;
			tmp61_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp61_AST);
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
		
		boolean synPredMatched106 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m106 = mark();
			synPredMatched106 = true;
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
				synPredMatched106 = false;
			}
			rewind(_m106);
inputState.guessing--;
		}
		if ( synPredMatched106 ) {
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
					binexplvl4_AST = (AST)astFactory.make( (new ASTArray(3)).add(op2_AST).add((AST)astFactory.make( (new ASTArray(3)).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl4_AST;
				currentAST.child = binexplvl4_AST!=null &&binexplvl4_AST.getFirstChild()!=null ?
					binexplvl4_AST.getFirstChild() : binexplvl4_AST;
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
					binexplvl3();
					l4op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched108 = false;
				}
				rewind(_m108);
inputState.guessing--;
			}
			if ( synPredMatched108 ) {
				binexplvl3();
				exp4_AST = (AST)returnAST;
				l4op();
				op3_AST = (AST)returnAST;
				binexplvl4();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl4_AST = (AST)currentAST.root;
						binexplvl4_AST = (AST)astFactory.make( (new ASTArray(3)).add(op3_AST).add(exp4_AST).add(exp5_AST));
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
		
		AST tmp62_AST = null;
		tmp62_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp62_AST);
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
		
		boolean synPredMatched112 = false;
		if (((_tokenSet_1.member(LA(1))))) {
			int _m112 = mark();
			synPredMatched112 = true;
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
				synPredMatched112 = false;
			}
			rewind(_m112);
inputState.guessing--;
		}
		if ( synPredMatched112 ) {
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
					binexplvl3_AST = (AST)astFactory.make( (new ASTArray(3)).add(op2_AST).add((AST)astFactory.make( (new ASTArray(3)).add(op1_AST).add(exp1_AST).add(exp2_AST))).add(exp3_AST));
				currentAST.root = binexplvl3_AST;
				currentAST.child = binexplvl3_AST!=null &&binexplvl3_AST.getFirstChild()!=null ?
					binexplvl3_AST.getFirstChild() : binexplvl3_AST;
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
					exprnr();
					l3op();
					}
				}
				catch (RecognitionException pe) {
					synPredMatched114 = false;
				}
				rewind(_m114);
inputState.guessing--;
			}
			if ( synPredMatched114 ) {
				exprnr();
				exp4_AST = (AST)returnAST;
				l3op();
				op3_AST = (AST)returnAST;
				binexplvl3();
				exp5_AST = (AST)returnAST;
				if ( inputState.guessing==0 ) {
					binexplvl3_AST = (AST)currentAST.root;
						binexplvl3_AST = (AST)astFactory.make( (new ASTArray(3)).add(op3_AST).add(exp4_AST).add(exp5_AST));
					currentAST.root = binexplvl3_AST;
					currentAST.child = binexplvl3_AST!=null &&binexplvl3_AST.getFirstChild()!=null ?
						binexplvl3_AST.getFirstChild() : binexplvl3_AST;
					currentAST.advanceChildToEnd();
				}
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				exprnr();
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
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp63_AST);
			match(PLUS);
			l4op_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
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
		
		AST tmp65_AST = null;
		tmp65_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp65_AST);
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
		"PROGRAM",
		"INVOKE",
		"ARGUMENTS",
		"FUNCTION_NAME",
		"FUNCTION_BODY",
		"FIELD_LOOKUP"
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
