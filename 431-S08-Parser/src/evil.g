/*
   Lexer
*/
header
{
}
class EvilLexer extends Lexer;

options
{
   k = 2;
   charVocabulary='\u0000'..'\u007F';
}

tokens
{
   STRUCT         =  "struct";
   INT            =  "int";
   BOOL           =  "bool";
   FUN            =  "fun";
   VOID           =  "void";
   PRINT          =  "print";
   ENDL           =  "endl";
   READ           =  "read";
   IF             =  "if";
   ELSE           =  "else";
   WHILE          =  "while";
   DELETE         =  "delete";
   RETURN         =  "return";
   TRUE           =  "true";
   FALSE          =  "false";
   NEW            =  "new";
   NULL           =  "null";
}

LBRACE   :  "{"   ;
RBRACE   :  "}"   ;
SEMI     :  ";"   ;
COMMA    :  ","   ;
LPAREN   :  "("   ;
RPAREN   :  ")"   ;
ASSIGN   :  "="   ;
DOT      :  "."   ;
AND      :  "&&"  ;
OR       :  "||"  ;
EQ       :  "=="  ;
LT       :  "<"   ;
GT       :  ">"   ;
NE       :  "!="  ;
LE       :  "<="  ;
GE       :  ">="  ;
PLUS     :  "+"   ;
MINUS    :  "-"   ;
TIMES    :  "*"   ;
DIVIDE   :  "/"   ;
NOT      :  "!"   ;

ID options {testLiterals=true;}
         :  ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;

INTEGER      :  '0' | ('1'..'9') ('0'..'9')* ;

WS       :  (  ' '
            |  '\t'
            |  '\f'
               // handle newlines
            |  (  options {generateAmbigWarnings=false;}
               :  "\r\n"   // Evil DOS
               |  '\r'     // Macintosh
               |  '\n'     // Unix (the right way)
               )
               { newline(); }
            )+
            { $setType(Token.SKIP); }
         ;

COMMENT  :  "#"(~'\n')* '\n'
            { newline(); $setType(Token.SKIP); }
         ;

/*
   Parser
*/
class EvilParser extends Parser;

options
{
   buildAST=true;
   defaultErrorHandler=false;
}

tokens
{
   PROGRAM;
   TYPES;
   TYPE;
   DECLS;
   FUNCS;
   DECL;
   DECLLIST;
   PARAMS;
   RETTYPE;
   BLOCK;
   STMTS;
   INVOKE;
   ARGS;
   NEG;
}

program
   :!  t:types d:declarations f:functions EOF!
      { #program = #([PROGRAM,"PROGRAM"],
                     #([TYPES,"TYPES"], #t),
                     #([DECLS,"DECLS"], #d),
                     #([FUNCS,"FUNCS"], #f)); }
   ;
types
   :  (STRUCT ID LBRACE) => type_declaration types
   |
   ;
type_declaration
   :  STRUCT^ ID LBRACE! nested_decl RBRACE! SEMI!
   ;
nested_decl
   :  (decl SEMI!)+
   ;
decl
   :! t:type i:ID { #decl = #([DECL,"DECL"], #([TYPE,"TYPE"],#t), #i); }
   ;
type
   :  INT
   |  BOOL
   |  STRUCT^ ID
   ;
declarations
   :  (declaration)*
   ;
declaration
   :!  t:type ilist:id_list SEMI!
      { #declaration = #([DECLLIST,"DECLLIST"], #([TYPE,"TYPE"], #t), #ilist); }
   ;
id_list
   :  ID (COMMA! ID)*
   ;
functions
   :  (function)*
   ;
function
   :!  FUN<AST=AnnotatedFunctionAST> id:ID p:parameters r:return_type LBRACE d:declarations s:statement_list RBRACE
      { #function = #(FUN,
                        #id,
                        #([PARAMS,"PARAMS"], #p),
                        #([RETTYPE, "RETTYPE"], #r),
                        #([DECLS,"DECLS"], #d),
                        #([STMTS,"STMTS"], #s)
                     );
      }
   ;
parameters
   :  LPAREN! (decl (COMMA! decl)*)? RPAREN!
   ;
return_type
   :  type
   |  VOID
   ;
statement
   :  block
   |  (lvalue ASSIGN) => assignment
   |  print
   |  read
   |  conditional
   |  loop
   |  delete
   |  ret
   |  (ID LPAREN) => invocation
   ;
block
   :!  LBRACE! s:statement_list RBRACE!
         {
            #block = #([BLOCK, "BLOCK"],
                                    #([STMTS,"STMTS"], #s)
                                   );
         }
   ;
statement_list
   :  (statement)*
   ;
assignment
   :  lvalue ASSIGN^ expression SEMI!
   ;
print
   :  PRINT^ expression (ENDL)? SEMI!
   ;
read
   :  READ^ lvalue SEMI!
   ;
conditional
   :  IF^ LPAREN! expression RPAREN! block (ELSE! block)?
   ;
loop
   :  WHILE^ LPAREN! expression RPAREN! block
   ;
delete
   :  DELETE^ expression SEMI!
   ;
ret
   :  RETURN^ (expression)? SEMI!
   ;
invocation
   :! id:ID a:arguments SEMI
      {
         #invocation = #([INVOKE,"INVOKE","AnnotatedExpressionAST"], #id, #a);
      }
   ;
lvalue
   :  ID (DOT^<AST=DottedAST> ID)*
   ;
expression
   :  boolterm ((AND^<AST=AnnotatedExpressionAST> | OR^<AST=AnnotatedExpressionAST>) boolterm)*
   ;
boolterm
   :  simple ((EQ^<AST=AnnotatedExpressionAST> | LT^<AST=AnnotatedExpressionAST> | GT^<AST=AnnotatedExpressionAST> | NE^<AST=AnnotatedExpressionAST> | LE^<AST=AnnotatedExpressionAST> | GE^<AST=AnnotatedExpressionAST>) simple)?
   ;
simple
   :  term ((PLUS^<AST=AnnotatedExpressionAST> | MINUS^<AST=AnnotatedExpressionAST>) term)*
   ;
term
   :  unary ((TIMES^<AST=AnnotatedExpressionAST> | DIVIDE^<AST=AnnotatedExpressionAST>) unary)*
   ;
unary
   :  NOT! odd_not
   |  MINUS! odd_neg
   |  selector
   ;
odd_not
   :  NOT! even_not
   |!  s:selector { #odd_not = #([NOT,"!","AnnotatedExpressionAST"], #s); }
   ;
even_not
   :  NOT! odd_not
   |  selector
   ;
odd_neg
   :  MINUS! even_neg
   |!  s:selector { #odd_neg = #([NEG,"NEG","AnnotatedExpressionAST"], #s); }
   ;
even_neg
   :  MINUS! odd_neg
   |  selector
   ;
selector
   :  factor (DOT^<AST=DottedAST> ID)*
   ;
factor
   :  LPAREN! expression RPAREN!
   |!  id:ID<AST=AnnotatedExpressionAST> (a:arguments)?
      {
         if (#a != null)
         {
            #factor = #([INVOKE,"INVOKE","AnnotatedExpressionAST"], #id, #a);
         }
         else
         {
            #factor = #id;
         }
      }
   |  INTEGER<AST=AnnotatedExpressionAST>
   |  TRUE<AST=AnnotatedExpressionAST>
   |  FALSE<AST=AnnotatedExpressionAST>
   |  NEW^<AST=AnnotatedExpressionAST> ID<AST=AnnotatedExpressionAST>
   |  NULL<AST=AnnotatedExpressionAST>
   ;
arguments
   :  LPAREN! arg_list RPAREN!
   ;
arg_list
   : expression (COMMA! expression)*
      { #arg_list = #([ARGS,"ARGS"], #arg_list); }
   |! { #arg_list = #([ARGS,"ARGS"]); }
   ;





/*
   StaticTypechecker
*/

{
import java.util.ArrayList;
}

class EvilTypechecker extends TreeParser;

{
    private void error (String errormsg)
    {
        System.out.println(errormsg);
        System.exit(1);
    }
}

validate [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
returns [boolean foundMain = false;]
    : #(PROGRAM types[stypes] declarations[stypes,stable]
        foundMain=functions[stypes,stable])
;

/* types */

types [ArrayList<evilStruct> stypes]
    : #(TYPES (struct [stypes])*)
;

struct [ArrayList<evilStruct> stypes]
    { String targetName; }
    : #(STRUCT targetName=buildstruct[stypes] (structdecl[stypes, targetName])+)
;

buildstruct [ArrayList<evilStruct> stypes]
returns [String dependency = "wark"]
    : structName:ID
      {
          dependency = structName.toString();
          for(evilStruct alreadyDefinedStruct : stypes)
              if(alreadyDefinedStruct.structtype().equals(structName.toString()))
                  error("Struct type " + structName.toString() + " has already been defined; exiting");
          stypes.add(new evilStruct(structName.toString()));
      }
;

structdecl [ArrayList<evilStruct> stypes, String structName]
    : #(DECL #(TYPE r:type) fieldName:ID)
      {
          if(r.toString().equals("struct"))
          {
              String structvar = r.getFirstChild().toString();
              for(evilStruct existingstruct : stypes)
                  if(structvar.equals(existingstruct.structtype()))
                  {
                      existingstruct.insertField(structName, fieldName.toString(), structvar);
                      return;
                  }
              error("Error binding struct " + fieldName.toString() + ": struct type " + structvar + " undeclared");
          }
          else
          {
              boolean fieldadded = false;
              for(evilStruct definedStruct : stypes)
                  if(definedStruct.insertField(structName, fieldName.toString(), r.toString()))
                  {
                      fieldadded = true;
                      break;
                  }
              if(!fieldadded)
              error("Failed to add field " + fieldName.toString() + " to struct " + structName.toString());
          }
    }
;

decl
    : #(DECL #(TYPE type) declName:ID)
;

type: INT
    | BOOL
    | typestruct
;

typestruct: #(STRUCT structName:ID)
;

/* declarations 
   how do we determine the validity of a struct declaration? 
   how do these declarations persist (symbol table)? */

declarations [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(DECLS (decllist[stypes, stable])*)
;

decllist [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(DECLLIST #(TYPE r:type) (typedecls[stypes, stable, r])+)
      {
      }
      
      /* what about for struct declarations? */

;

typedecls [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST r]
    : declname:ID
      {
          if(r.toString().equals("struct"))
          {
              System.out.println("a bright sunshiny day");
              String structvar = r.getFirstChild().toString();
              boolean structtypeexists = false;
              for(evilStruct existingstruct : stypes)
                  if(structvar.equals(existingstruct.structtype()))
                  {
                      structtypeexists = true;
                      break;
                  }
              if(!structtypeexists)
              {
                  error("Error binding struct " + declname + ": struct type " + structvar + " undeclared");
              }
          }
          System.out.println("checkpoint");
          for(symbolBindings binding : stable)
              if((binding.id()).equals(declname))
                  error("Binding " + r + " already exists in the symbol table; exiting");
          stable.add(new symbolBindings(declname.toString(), r.toString()));
          System.out.println("nyoron: " + declname.toString() + ", " + r.toString());
      }
;

/* functions */

returntype:
    type
  | VOID
;

functions [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
  returns [boolean foundmain = false]
    : #(FUNCS (fun[stypes, stable])+)
;

fun [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(FUN
        funname:ID
        /* params */ params[stypes, stable]
        /* return type */ rettype[stypes, stable]
        /* fundecls */ (fundecl[stypes, stable])+ /* need to keep these isolated to functions; not done */
        /* statements */ statements[stypes, stable]
        )
      {
          /* debugcode System.out.println("Entering function: " + funname); debugcode */
      }
;

params [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(PARAMS (decl)*)
;

rettype [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(RETTYPE returntype)
;

fundecl [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : #(DECLS (decllist[stypes, stable])*)
;

statements [ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable]
    : (STMTS)*
;