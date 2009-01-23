// $ANTLR 2.7.7 (20070330): "evil.g" -> "EvilParser.java"$


import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class EvilParser extends antlr.LLkParser       implements EvilLexerTokenTypes
 {

protected EvilParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public EvilParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected EvilParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public EvilParser(TokenStream lexer) {
  this(lexer,1);
}

public EvilParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void program() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST program_AST = null;
		AST t_AST = null;
		AST d_AST = null;
		AST f_AST = null;
		
		types();
		t_AST = (AST)returnAST;
		declarations();
		d_AST = (AST)returnAST;
		functions();
		f_AST = (AST)returnAST;
		match(Token.EOF_TYPE);
		if ( inputState.guessing==0 ) {
			program_AST = (AST)currentAST.root;
			program_AST = (AST)astFactory.make( (new ASTArray(4)).add(astFactory.create(PROGRAM,"PROGRAM")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPES,"TYPES")).add(t_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DECLS,"DECLS")).add(d_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(FUNCS,"FUNCS")).add(f_AST))));
			currentAST.root = program_AST;
			currentAST.child = program_AST!=null &&program_AST.getFirstChild()!=null ?
				program_AST.getFirstChild() : program_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = program_AST;
	}
	
	public final void types() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST types_AST = null;
		
		boolean synPredMatched40 = false;
		if (((LA(1)==STRUCT))) {
			int _m40 = mark();
			synPredMatched40 = true;
			inputState.guessing++;
			try {
				{
				match(STRUCT);
				match(ID);
				match(LBRACE);
				}
			}
			catch (RecognitionException pe) {
				synPredMatched40 = false;
			}
			rewind(_m40);
inputState.guessing--;
		}
		if ( synPredMatched40 ) {
			type_declaration();
			astFactory.addASTChild(currentAST, returnAST);
			types();
			astFactory.addASTChild(currentAST, returnAST);
			types_AST = (AST)currentAST.root;
		}
		else if ((_tokenSet_0.member(LA(1)))) {
			types_AST = (AST)currentAST.root;
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = types_AST;
	}
	
	public final void declarations() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declarations_AST = null;
		
		{
		_loop49:
		do {
			if (((LA(1) >= STRUCT && LA(1) <= BOOL))) {
				declaration();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop49;
			}
			
		} while (true);
		}
		declarations_AST = (AST)currentAST.root;
		returnAST = declarations_AST;
	}
	
	public final void functions() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST functions_AST = null;
		
		{
		_loop56:
		do {
			if ((LA(1)==FUN)) {
				function();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop56;
			}
			
		} while (true);
		}
		functions_AST = (AST)currentAST.root;
		returnAST = functions_AST;
	}
	
	public final void type_declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_declaration_AST = null;
		
		AST tmp38_AST = null;
		tmp38_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp38_AST);
		match(STRUCT);
		AST tmp39_AST = null;
		tmp39_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp39_AST);
		match(ID);
		match(LBRACE);
		nested_decl();
		astFactory.addASTChild(currentAST, returnAST);
		match(RBRACE);
		match(SEMI);
		type_declaration_AST = (AST)currentAST.root;
		returnAST = type_declaration_AST;
	}
	
	public final void nested_decl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST nested_decl_AST = null;
		
		{
		int _cnt44=0;
		_loop44:
		do {
			if (((LA(1) >= STRUCT && LA(1) <= BOOL))) {
				decl();
				astFactory.addASTChild(currentAST, returnAST);
				match(SEMI);
			}
			else {
				if ( _cnt44>=1 ) { break _loop44; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt44++;
		} while (true);
		}
		nested_decl_AST = (AST)currentAST.root;
		returnAST = nested_decl_AST;
	}
	
	public final void decl() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST decl_AST = null;
		AST t_AST = null;
		Token  i = null;
		AST i_AST = null;
		
		type();
		t_AST = (AST)returnAST;
		i = LT(1);
		i_AST = astFactory.create(i);
		match(ID);
		if ( inputState.guessing==0 ) {
			decl_AST = (AST)currentAST.root;
			decl_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(DECL,"DECL")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPE,"TYPE")).add(t_AST))).add(i_AST));
			currentAST.root = decl_AST;
			currentAST.child = decl_AST!=null &&decl_AST.getFirstChild()!=null ?
				decl_AST.getFirstChild() : decl_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = decl_AST;
	}
	
	public final void type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST type_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(INT);
			type_AST = (AST)currentAST.root;
			break;
		}
		case BOOL:
		{
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(BOOL);
			type_AST = (AST)currentAST.root;
			break;
		}
		case STRUCT:
		{
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp46_AST);
			match(STRUCT);
			AST tmp47_AST = null;
			tmp47_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp47_AST);
			match(ID);
			type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = type_AST;
	}
	
	public final void declaration() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declaration_AST = null;
		AST t_AST = null;
		AST ilist_AST = null;
		
		type();
		t_AST = (AST)returnAST;
		id_list();
		ilist_AST = (AST)returnAST;
		match(SEMI);
		if ( inputState.guessing==0 ) {
			declaration_AST = (AST)currentAST.root;
			declaration_AST = (AST)astFactory.make( (new ASTArray(3)).add(astFactory.create(DECLLIST,"DECLLIST")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TYPE,"TYPE")).add(t_AST))).add(ilist_AST));
			currentAST.root = declaration_AST;
			currentAST.child = declaration_AST!=null &&declaration_AST.getFirstChild()!=null ?
				declaration_AST.getFirstChild() : declaration_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = declaration_AST;
	}
	
	public final void id_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST id_list_AST = null;
		
		AST tmp49_AST = null;
		tmp49_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp49_AST);
		match(ID);
		{
		_loop53:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				AST tmp51_AST = null;
				tmp51_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp51_AST);
				match(ID);
			}
			else {
				break _loop53;
			}
			
		} while (true);
		}
		id_list_AST = (AST)currentAST.root;
		returnAST = id_list_AST;
	}
	
	public final void function() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST p_AST = null;
		AST r_AST = null;
		AST d_AST = null;
		AST s_AST = null;
		
		AnnotatedFunctionAST tmp52_AST = null;
		tmp52_AST = (AnnotatedFunctionAST)astFactory.create(LT(1),"AnnotatedFunctionAST");
		match(FUN);
		id = LT(1);
		id_AST = astFactory.create(id);
		match(ID);
		parameters();
		p_AST = (AST)returnAST;
		return_type();
		r_AST = (AST)returnAST;
		AST tmp53_AST = null;
		tmp53_AST = astFactory.create(LT(1));
		match(LBRACE);
		declarations();
		d_AST = (AST)returnAST;
		statement_list();
		s_AST = (AST)returnAST;
		AST tmp54_AST = null;
		tmp54_AST = astFactory.create(LT(1));
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			function_AST = (AST)currentAST.root;
			function_AST = (AST)astFactory.make( (new ASTArray(6)).add(tmp52_AST).add(id_AST).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PARAMS,"PARAMS")).add(p_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(RETTYPE,"RETTYPE")).add(r_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DECLS,"DECLS")).add(d_AST))).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STMTS,"STMTS")).add(s_AST))));
			
			currentAST.root = function_AST;
			currentAST.child = function_AST!=null &&function_AST.getFirstChild()!=null ?
				function_AST.getFirstChild() : function_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = function_AST;
	}
	
	public final void parameters() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameters_AST = null;
		
		match(LPAREN);
		{
		switch ( LA(1)) {
		case STRUCT:
		case INT:
		case BOOL:
		{
			decl();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop61:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					decl();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop61;
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
		parameters_AST = (AST)currentAST.root;
		returnAST = parameters_AST;
	}
	
	public final void return_type() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST return_type_AST = null;
		
		switch ( LA(1)) {
		case STRUCT:
		case INT:
		case BOOL:
		{
			type();
			astFactory.addASTChild(currentAST, returnAST);
			return_type_AST = (AST)currentAST.root;
			break;
		}
		case VOID:
		{
			AST tmp58_AST = null;
			tmp58_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp58_AST);
			match(VOID);
			return_type_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = return_type_AST;
	}
	
	public final void statement_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_list_AST = null;
		
		{
		_loop71:
		do {
			if ((_tokenSet_1.member(LA(1)))) {
				statement();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop71;
			}
			
		} while (true);
		}
		statement_list_AST = (AST)currentAST.root;
		returnAST = statement_list_AST;
	}
	
	public final void statement() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST statement_AST = null;
		
		switch ( LA(1)) {
		case LBRACE:
		{
			block();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case PRINT:
		{
			print();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case READ:
		{
			read();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			conditional();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			loop();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case DELETE:
		{
			delete();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			ret();
			astFactory.addASTChild(currentAST, returnAST);
			statement_AST = (AST)currentAST.root;
			break;
		}
		default:
			boolean synPredMatched65 = false;
			if (((LA(1)==ID))) {
				int _m65 = mark();
				synPredMatched65 = true;
				inputState.guessing++;
				try {
					{
					lvalue();
					match(ASSIGN);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched65 = false;
				}
				rewind(_m65);
inputState.guessing--;
			}
			if ( synPredMatched65 ) {
				assignment();
				astFactory.addASTChild(currentAST, returnAST);
				statement_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched67 = false;
				if (((LA(1)==ID))) {
					int _m67 = mark();
					synPredMatched67 = true;
					inputState.guessing++;
					try {
						{
						match(ID);
						match(LPAREN);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched67 = false;
					}
					rewind(_m67);
inputState.guessing--;
				}
				if ( synPredMatched67 ) {
					invocation();
					astFactory.addASTChild(currentAST, returnAST);
					statement_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}}
			returnAST = statement_AST;
		}
		
	public final void block() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST block_AST = null;
		AST s_AST = null;
		
		match(LBRACE);
		statement_list();
		s_AST = (AST)returnAST;
		match(RBRACE);
		if ( inputState.guessing==0 ) {
			block_AST = (AST)currentAST.root;
			
			block_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(BLOCK,"BLOCK")).add((AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(STMTS,"STMTS")).add(s_AST))));
			
			currentAST.root = block_AST;
			currentAST.child = block_AST!=null &&block_AST.getFirstChild()!=null ?
				block_AST.getFirstChild() : block_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = block_AST;
	}
	
	public final void lvalue() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST lvalue_AST = null;
		
		AST tmp61_AST = null;
		tmp61_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp61_AST);
		match(ID);
		{
		_loop85:
		do {
			if ((LA(1)==DOT)) {
				DottedAST tmp62_AST = null;
				tmp62_AST = (DottedAST)astFactory.create(LT(1),"DottedAST");
				astFactory.makeASTRoot(currentAST, tmp62_AST);
				match(DOT);
				AST tmp63_AST = null;
				tmp63_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp63_AST);
				match(ID);
			}
			else {
				break _loop85;
			}
			
		} while (true);
		}
		lvalue_AST = (AST)currentAST.root;
		returnAST = lvalue_AST;
	}
	
	public final void assignment() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assignment_AST = null;
		
		lvalue();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp64_AST = null;
		tmp64_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp64_AST);
		match(ASSIGN);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		assignment_AST = (AST)currentAST.root;
		returnAST = assignment_AST;
	}
	
	public final void print() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST print_AST = null;
		
		AST tmp66_AST = null;
		tmp66_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp66_AST);
		match(PRINT);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case ENDL:
		{
			AST tmp67_AST = null;
			tmp67_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp67_AST);
			match(ENDL);
			break;
		}
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEMI);
		print_AST = (AST)currentAST.root;
		returnAST = print_AST;
	}
	
	public final void read() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST read_AST = null;
		
		AST tmp69_AST = null;
		tmp69_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp69_AST);
		match(READ);
		lvalue();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		read_AST = (AST)currentAST.root;
		returnAST = read_AST;
	}
	
	public final void conditional() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST conditional_AST = null;
		
		AST tmp71_AST = null;
		tmp71_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp71_AST);
		match(IF);
		match(LPAREN);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(RPAREN);
		block();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case ELSE:
		{
			match(ELSE);
			block();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case PRINT:
		case READ:
		case IF:
		case WHILE:
		case DELETE:
		case RETURN:
		case LBRACE:
		case RBRACE:
		case ID:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		conditional_AST = (AST)currentAST.root;
		returnAST = conditional_AST;
	}
	
	public final void loop() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST loop_AST = null;
		
		AST tmp75_AST = null;
		tmp75_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp75_AST);
		match(WHILE);
		match(LPAREN);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(RPAREN);
		block();
		astFactory.addASTChild(currentAST, returnAST);
		loop_AST = (AST)currentAST.root;
		returnAST = loop_AST;
	}
	
	public final void delete() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST delete_AST = null;
		
		AST tmp78_AST = null;
		tmp78_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp78_AST);
		match(DELETE);
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		match(SEMI);
		delete_AST = (AST)currentAST.root;
		returnAST = delete_AST;
	}
	
	public final void ret() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ret_AST = null;
		
		AST tmp80_AST = null;
		tmp80_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp80_AST);
		match(RETURN);
		{
		switch ( LA(1)) {
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case MINUS:
		case NOT:
		case ID:
		case INTEGER:
		{
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEMI);
		ret_AST = (AST)currentAST.root;
		returnAST = ret_AST;
	}
	
	public final void invocation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST invocation_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST a_AST = null;
		
		id = LT(1);
		id_AST = astFactory.create(id);
		match(ID);
		arguments();
		a_AST = (AST)returnAST;
		AST tmp82_AST = null;
		tmp82_AST = astFactory.create(LT(1));
		match(SEMI);
		if ( inputState.guessing==0 ) {
			invocation_AST = (AST)currentAST.root;
			
			invocation_AST = (AST)astFactory.make( (new ASTArray(3)).add((AST)astFactory.create(INVOKE,"INVOKE","AnnotatedExpressionAST")).add(id_AST).add(a_AST));
			
			currentAST.root = invocation_AST;
			currentAST.child = invocation_AST!=null &&invocation_AST.getFirstChild()!=null ?
				invocation_AST.getFirstChild() : invocation_AST;
			currentAST.advanceChildToEnd();
		}
		returnAST = invocation_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		boolterm();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop89:
		do {
			if ((LA(1)==AND||LA(1)==OR)) {
				{
				switch ( LA(1)) {
				case AND:
				{
					AnnotatedExpressionAST tmp83_AST = null;
					tmp83_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp83_AST);
					match(AND);
					break;
				}
				case OR:
				{
					AnnotatedExpressionAST tmp84_AST = null;
					tmp84_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp84_AST);
					match(OR);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				boolterm();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop89;
			}
			
		} while (true);
		}
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void arguments() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arguments_AST = null;
		
		match(LPAREN);
		arg_list();
		astFactory.addASTChild(currentAST, returnAST);
		match(RPAREN);
		arguments_AST = (AST)currentAST.root;
		returnAST = arguments_AST;
	}
	
	public final void boolterm() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST boolterm_AST = null;
		
		simple();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case EQ:
		case LT:
		case GT:
		case NE:
		case LE:
		case GE:
		{
			{
			switch ( LA(1)) {
			case EQ:
			{
				AnnotatedExpressionAST tmp87_AST = null;
				tmp87_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp87_AST);
				match(EQ);
				break;
			}
			case LT:
			{
				AnnotatedExpressionAST tmp88_AST = null;
				tmp88_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp88_AST);
				match(LT);
				break;
			}
			case GT:
			{
				AnnotatedExpressionAST tmp89_AST = null;
				tmp89_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp89_AST);
				match(GT);
				break;
			}
			case NE:
			{
				AnnotatedExpressionAST tmp90_AST = null;
				tmp90_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp90_AST);
				match(NE);
				break;
			}
			case LE:
			{
				AnnotatedExpressionAST tmp91_AST = null;
				tmp91_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp91_AST);
				match(LE);
				break;
			}
			case GE:
			{
				AnnotatedExpressionAST tmp92_AST = null;
				tmp92_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
				astFactory.makeASTRoot(currentAST, tmp92_AST);
				match(GE);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			simple();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case ENDL:
		case SEMI:
		case COMMA:
		case RPAREN:
		case AND:
		case OR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		boolterm_AST = (AST)currentAST.root;
		returnAST = boolterm_AST;
	}
	
	public final void simple() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST simple_AST = null;
		
		term();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop96:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AnnotatedExpressionAST tmp93_AST = null;
					tmp93_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp93_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AnnotatedExpressionAST tmp94_AST = null;
					tmp94_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp94_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				term();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop96;
			}
			
		} while (true);
		}
		simple_AST = (AST)currentAST.root;
		returnAST = simple_AST;
	}
	
	public final void term() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST term_AST = null;
		
		unary();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop100:
		do {
			if ((LA(1)==TIMES||LA(1)==DIVIDE)) {
				{
				switch ( LA(1)) {
				case TIMES:
				{
					AnnotatedExpressionAST tmp95_AST = null;
					tmp95_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp95_AST);
					match(TIMES);
					break;
				}
				case DIVIDE:
				{
					AnnotatedExpressionAST tmp96_AST = null;
					tmp96_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
					astFactory.makeASTRoot(currentAST, tmp96_AST);
					match(DIVIDE);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				unary();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop100;
			}
			
		} while (true);
		}
		term_AST = (AST)currentAST.root;
		returnAST = term_AST;
	}
	
	public final void unary() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unary_AST = null;
		
		switch ( LA(1)) {
		case NOT:
		{
			match(NOT);
			odd_not();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		case MINUS:
		{
			match(MINUS);
			odd_neg();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case ID:
		case INTEGER:
		{
			selector();
			astFactory.addASTChild(currentAST, returnAST);
			unary_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = unary_AST;
	}
	
	public final void odd_not() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST odd_not_AST = null;
		AST s_AST = null;
		
		switch ( LA(1)) {
		case NOT:
		{
			match(NOT);
			even_not();
			astFactory.addASTChild(currentAST, returnAST);
			odd_not_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case ID:
		case INTEGER:
		{
			selector();
			s_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				odd_not_AST = (AST)currentAST.root;
				odd_not_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(NOT,"!","AnnotatedExpressionAST")).add(s_AST));
				currentAST.root = odd_not_AST;
				currentAST.child = odd_not_AST!=null &&odd_not_AST.getFirstChild()!=null ?
					odd_not_AST.getFirstChild() : odd_not_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = odd_not_AST;
	}
	
	public final void odd_neg() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST odd_neg_AST = null;
		AST s_AST = null;
		
		switch ( LA(1)) {
		case MINUS:
		{
			match(MINUS);
			even_neg();
			astFactory.addASTChild(currentAST, returnAST);
			odd_neg_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case ID:
		case INTEGER:
		{
			selector();
			s_AST = (AST)returnAST;
			if ( inputState.guessing==0 ) {
				odd_neg_AST = (AST)currentAST.root;
				odd_neg_AST = (AST)astFactory.make( (new ASTArray(2)).add((AST)astFactory.create(NEG,"NEG","AnnotatedExpressionAST")).add(s_AST));
				currentAST.root = odd_neg_AST;
				currentAST.child = odd_neg_AST!=null &&odd_neg_AST.getFirstChild()!=null ?
					odd_neg_AST.getFirstChild() : odd_neg_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = odd_neg_AST;
	}
	
	public final void selector() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selector_AST = null;
		
		factor();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop108:
		do {
			if ((LA(1)==DOT)) {
				DottedAST tmp101_AST = null;
				tmp101_AST = (DottedAST)astFactory.create(LT(1),"DottedAST");
				astFactory.makeASTRoot(currentAST, tmp101_AST);
				match(DOT);
				AST tmp102_AST = null;
				tmp102_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp102_AST);
				match(ID);
			}
			else {
				break _loop108;
			}
			
		} while (true);
		}
		selector_AST = (AST)currentAST.root;
		returnAST = selector_AST;
	}
	
	public final void even_not() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST even_not_AST = null;
		
		switch ( LA(1)) {
		case NOT:
		{
			match(NOT);
			odd_not();
			astFactory.addASTChild(currentAST, returnAST);
			even_not_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case ID:
		case INTEGER:
		{
			selector();
			astFactory.addASTChild(currentAST, returnAST);
			even_not_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = even_not_AST;
	}
	
	public final void even_neg() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST even_neg_AST = null;
		
		switch ( LA(1)) {
		case MINUS:
		{
			match(MINUS);
			odd_neg();
			astFactory.addASTChild(currentAST, returnAST);
			even_neg_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case ID:
		case INTEGER:
		{
			selector();
			astFactory.addASTChild(currentAST, returnAST);
			even_neg_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = even_neg_AST;
	}
	
	public final void factor() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST factor_AST = null;
		Token  id = null;
		AnnotatedExpressionAST id_AST = null;
		AST a_AST = null;
		
		switch ( LA(1)) {
		case LPAREN:
		{
			match(LPAREN);
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case ID:
		{
			id = LT(1);
			id_AST = (AnnotatedExpressionAST)astFactory.create(id,"AnnotatedExpressionAST");
			match(ID);
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				arguments();
				a_AST = (AST)returnAST;
				break;
			}
			case ENDL:
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
			case LE:
			case GE:
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
				factor_AST = (AST)currentAST.root;
				
				if (a_AST != null)
				{
				factor_AST = (AST)astFactory.make( (new ASTArray(3)).add((AST)astFactory.create(INVOKE,"INVOKE","AnnotatedExpressionAST")).add(id_AST).add(a_AST));
				}
				else
				{
				factor_AST = id_AST;
				}
				
				currentAST.root = factor_AST;
				currentAST.child = factor_AST!=null &&factor_AST.getFirstChild()!=null ?
					factor_AST.getFirstChild() : factor_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		case INTEGER:
		{
			AnnotatedExpressionAST tmp107_AST = null;
			tmp107_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.addASTChild(currentAST, tmp107_AST);
			match(INTEGER);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		{
			AnnotatedExpressionAST tmp108_AST = null;
			tmp108_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.addASTChild(currentAST, tmp108_AST);
			match(TRUE);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case FALSE:
		{
			AnnotatedExpressionAST tmp109_AST = null;
			tmp109_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.addASTChild(currentAST, tmp109_AST);
			match(FALSE);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AnnotatedExpressionAST tmp110_AST = null;
			tmp110_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.makeASTRoot(currentAST, tmp110_AST);
			match(NEW);
			AnnotatedExpressionAST tmp111_AST = null;
			tmp111_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.addASTChild(currentAST, tmp111_AST);
			match(ID);
			factor_AST = (AST)currentAST.root;
			break;
		}
		case NULL:
		{
			AnnotatedExpressionAST tmp112_AST = null;
			tmp112_AST = (AnnotatedExpressionAST)astFactory.create(LT(1),"AnnotatedExpressionAST");
			astFactory.addASTChild(currentAST, tmp112_AST);
			match(NULL);
			factor_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = factor_AST;
	}
	
	public final void arg_list() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arg_list_AST = null;
		
		switch ( LA(1)) {
		case TRUE:
		case FALSE:
		case NEW:
		case NULL:
		case LPAREN:
		case MINUS:
		case NOT:
		case ID:
		case INTEGER:
		{
			expression();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop114:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					expression();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop114;
				}
				
			} while (true);
			}
			if ( inputState.guessing==0 ) {
				arg_list_AST = (AST)currentAST.root;
				arg_list_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ARGS,"ARGS")).add(arg_list_AST));
				currentAST.root = arg_list_AST;
				currentAST.child = arg_list_AST!=null &&arg_list_AST.getFirstChild()!=null ?
					arg_list_AST.getFirstChild() : arg_list_AST;
				currentAST.advanceChildToEnd();
			}
			arg_list_AST = (AST)currentAST.root;
			break;
		}
		case RPAREN:
		{
			if ( inputState.guessing==0 ) {
				arg_list_AST = (AST)currentAST.root;
				arg_list_AST = (AST)astFactory.make( (new ASTArray(1)).add(astFactory.create(ARGS,"ARGS")));
				currentAST.root = arg_list_AST;
				currentAST.child = arg_list_AST!=null &&arg_list_AST.getFirstChild()!=null ?
					arg_list_AST.getFirstChild() : arg_list_AST;
				currentAST.advanceChildToEnd();
			}
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = arg_list_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"struct\"",
		"\"int\"",
		"\"bool\"",
		"\"fun\"",
		"\"void\"",
		"\"print\"",
		"\"endl\"",
		"\"read\"",
		"\"if\"",
		"\"else\"",
		"\"while\"",
		"\"delete\"",
		"\"return\"",
		"\"true\"",
		"\"false\"",
		"\"new\"",
		"\"null\"",
		"LBRACE",
		"RBRACE",
		"SEMI",
		"COMMA",
		"LPAREN",
		"RPAREN",
		"ASSIGN",
		"DOT",
		"AND",
		"OR",
		"EQ",
		"LT",
		"GT",
		"NE",
		"LE",
		"GE",
		"PLUS",
		"MINUS",
		"TIMES",
		"DIVIDE",
		"NOT",
		"ID",
		"INTEGER",
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
		"NEG"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 242L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 4398048729600L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	
	}
