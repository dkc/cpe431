// $ANTLR 2.7.7 (20070330): "evil.g" -> "EvilTypechecker.java"$


import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.collections.impl.BitSet;
import java.util.ArrayList;


public class EvilTypechecker extends antlr.TreeParser       implements EvilLexerTokenTypes
 {

    private void error (String errormsg)
    {
        System.out.println(errormsg);
        System.exit(1);
    }

    private void funadd (ArrayList<symbolBindings> stable, String funname, symbolBindings toAdd)
    {
        for(symbolBindings findfun : stable)
            if(findfun.id().equals(funname))
            {
                findfun.add(toAdd); return;
            }
        stable.add(new symbolBindings(funname, toAdd));
    }

    private String getType (ArrayList<symbolBindings> stable, String curFunction, String targetId)
    {
       for(symbolBindings currentBinding : stable)
       	if(currentBinding.id().equals(curFunction))
			if(currentBinding.contains(targetId))
				return currentBinding.getFieldType(targetId);
	for(symbolBindings currentBinding : stable)
       	if(currentBinding.id().equals(targetId))
			return currentBinding.type();
	return null;
    }
public EvilTypechecker() {
	tokenNames = _tokenNames;
}

	public final boolean  validate(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable
	) throws RecognitionException {
		boolean foundMain = false;;
		
		AST validate_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t116 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,PROGRAM);
			_t = _t.getFirstChild();
			types(_t,stypes);
			_t = _retTree;
			declarations(_t,stypes,stable);
			_t = _retTree;
			foundMain=functions(_t,stypes,stable);
			_t = _retTree;
			_t = __t116;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return foundMain;
	}
	
	public final void types(AST _t,
		ArrayList<evilStruct> stypes
	) throws RecognitionException {
		
		AST types_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t118 = _t;
			AST tmp2_AST_in = (AST)_t;
			match(_t,TYPES);
			_t = _t.getFirstChild();
			{
			_loop120:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==STRUCT)) {
					struct(_t,stypes);
					_t = _retTree;
				}
				else {
					break _loop120;
				}
				
			} while (true);
			}
			_t = __t118;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void declarations(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable
	) throws RecognitionException {
		
		AST declarations_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t136 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,DECLS);
			_t = _t.getFirstChild();
			{
			_loop138:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==DECLLIST)) {
					decllist(_t,stypes, stable);
					_t = _retTree;
				}
				else {
					break _loop138;
				}
				
			} while (true);
			}
			_t = __t136;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final boolean  functions(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable
	) throws RecognitionException {
		boolean ihatewarnings = true;
		
		AST functions_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t158 = _t;
			AST tmp4_AST_in = (AST)_t;
			match(_t,FUNCS);
			_t = _t.getFirstChild();
			{
			int _cnt160=0;
			_loop160:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==FUN)) {
					fun(_t,stypes, stable);
					_t = _retTree;
				}
				else {
					if ( _cnt160>=1 ) { break _loop160; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt160++;
			} while (true);
			}
			_t = __t158;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return ihatewarnings;
	}
	
	public final void struct(AST _t,
		ArrayList<evilStruct> stypes
	) throws RecognitionException {
		
		AST struct_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		String targetName;
		
		try {      // for error handling
			AST __t122 = _t;
			AST tmp5_AST_in = (AST)_t;
			match(_t,STRUCT);
			_t = _t.getFirstChild();
			targetName=buildstruct(_t,stypes);
			_t = _retTree;
			{
			int _cnt124=0;
			_loop124:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==DECL)) {
					structdecl(_t,stypes, targetName);
					_t = _retTree;
				}
				else {
					if ( _cnt124>=1 ) { break _loop124; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt124++;
			} while (true);
			}
			_t = __t122;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final String  buildstruct(AST _t,
		ArrayList<evilStruct> stypes
	) throws RecognitionException {
		String dependency = "wark";
		
		AST buildstruct_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST structName = null;
		
		try {      // for error handling
			structName = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
			dependency = structName.toString();
			for(evilStruct alreadyDefinedStruct : stypes)
			if(alreadyDefinedStruct.structtype().equals(structName.toString()))
			error("Struct type " + structName.toString() + " has already been defined; exiting");
			stypes.add(new evilStruct(structName.toString()));
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return dependency;
	}
	
	public final void structdecl(AST _t,
		ArrayList<evilStruct> stypes, String structName
	) throws RecognitionException {
		
		AST structdecl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST r = null;
		AST fieldName = null;
		
		try {      // for error handling
			AST __t127 = _t;
			AST tmp6_AST_in = (AST)_t;
			match(_t,DECL);
			_t = _t.getFirstChild();
			AST __t128 = _t;
			AST tmp7_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			r = _t==ASTNULL ? null : (AST)_t;
			type(_t);
			_t = _retTree;
			_t = __t128;
			_t = _t.getNextSibling();
			fieldName = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t127;
			_t = _t.getNextSibling();
			
			boolean fieldadded = false;
			if(r.toString().equals("struct"))
			{
			String structvar = r.getFirstChild().toString();
			for(evilStruct existingstruct : stypes)
			if(structvar.equals(existingstruct.structtype()))
			for(evilStruct existingstruct2 : stypes)
			if(existingstruct2.insertField(structName, fieldName.toString(), structvar))
			{
			fieldadded = true;
			break;
			}
			if(!fieldadded)
				error("Error binding struct " + fieldName.toString() + ": struct type " + structvar + " undeclared");
			}
			else
			{
			for(evilStruct existingstruct : stypes)
			if(existingstruct.insertField(structName, fieldName.toString(), r.toString()))
			{
			fieldadded = true;
			break;
			}
			if(!fieldadded)
				              error("Failed to add field " + fieldName.toString() + " to struct " + structName.toString());
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void type(AST _t) throws RecognitionException {
		
		AST type_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case INT:
			{
				AST tmp8_AST_in = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				break;
			}
			case BOOL:
			{
				AST tmp9_AST_in = (AST)_t;
				match(_t,BOOL);
				_t = _t.getNextSibling();
				break;
			}
			case STRUCT:
			{
				typestruct(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void decl(AST _t) throws RecognitionException {
		
		AST decl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST declName = null;
		
		try {      // for error handling
			AST __t130 = _t;
			AST tmp10_AST_in = (AST)_t;
			match(_t,DECL);
			_t = _t.getFirstChild();
			AST __t131 = _t;
			AST tmp11_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			type(_t);
			_t = _retTree;
			_t = __t131;
			_t = _t.getNextSibling();
			declName = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t130;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void typestruct(AST _t) throws RecognitionException {
		
		AST typestruct_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST structName = null;
		
		try {      // for error handling
			AST __t134 = _t;
			AST tmp12_AST_in = (AST)_t;
			match(_t,STRUCT);
			_t = _t.getFirstChild();
			structName = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			_t = __t134;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void decllist(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable
	) throws RecognitionException {
		
		AST decllist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST r = null;
		
		try {      // for error handling
			AST __t140 = _t;
			AST tmp13_AST_in = (AST)_t;
			match(_t,DECLLIST);
			_t = _t.getFirstChild();
			AST __t141 = _t;
			AST tmp14_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			r = _t==ASTNULL ? null : (AST)_t;
			type(_t);
			_t = _retTree;
			_t = __t141;
			_t = _t.getNextSibling();
			{
			int _cnt143=0;
			_loop143:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					typedecls(_t,stypes, stable, r);
					_t = _retTree;
				}
				else {
					if ( _cnt143>=1 ) { break _loop143; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt143++;
			} while (true);
			}
			_t = __t140;
			_t = _t.getNextSibling();
			
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void typedecls(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST r
	) throws RecognitionException {
		
		AST typedecls_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST declname = null;
		
		try {      // for error handling
			declname = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
			if(r.toString().equals("struct"))
			{
			String structvar = r.getFirstChild().toString();
			boolean structtypeexists = false;
			for(evilStruct existingstruct : stypes)
			if(structvar.equals(existingstruct.structtype()))
			{
			structtypeexists = true;
			break;
			}
			if(!structtypeexists)
			error("Error binding struct " + declname + ": struct type " + structvar + " undeclared");
			for(symbolBindings binding : stable)
			if((binding.id()).equals(declname.toString()))
			error("Binding " + declname.toString() + " already exists in the symbol table; exiting");
			stable.add(new symbolBindings(declname.toString(), structvar));
			}
			else
			{
			for(symbolBindings binding : stable)
			if((binding.id()).equals(declname.toString()))
			error("Binding " + declname.toString() + " already exists in the symbol table; exiting");
			stable.add(new symbolBindings(declname.toString(), r.toString()));
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void fundecllist(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST funname
	) throws RecognitionException {
		
		AST fundecllist_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST r = null;
		
		try {      // for error handling
			AST __t146 = _t;
			AST tmp15_AST_in = (AST)_t;
			match(_t,DECLLIST);
			_t = _t.getFirstChild();
			AST __t147 = _t;
			AST tmp16_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			r = _t==ASTNULL ? null : (AST)_t;
			type(_t);
			_t = _retTree;
			_t = __t147;
			_t = _t.getNextSibling();
			{
			int _cnt149=0;
			_loop149:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					funtypedecls(_t,stypes, stable, r, funname);
					_t = _retTree;
				}
				else {
					if ( _cnt149>=1 ) { break _loop149; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt149++;
			} while (true);
			}
			_t = __t146;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void funtypedecls(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST r, AST funname
	) throws RecognitionException {
		
		AST funtypedecls_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST declname = null;
		
		try {      // for error handling
			declname = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			
			if(r.toString().equals("struct"))
			{
			String structvar = r.getFirstChild().toString();
			boolean structtypeexists = false;
			for(evilStruct existingstruct : stypes)
			if(structvar.equals(existingstruct.structtype()))
			{
			structtypeexists = true;
			break;
			}
			if(!structtypeexists)
			error("Error binding struct " + declname + ": struct type " + structvar + " undeclared");
			for(symbolBindings binding : stable)
			if(binding.id().equals(funname.toString()) && binding.contains(declname.toString()))
			error("Binding " + declname.toString() + " already exists in the symbol table; exiting");
			funadd(stable, funname.toString(), new symbolBindings(declname.toString(), structvar));
			}
			else
			{
			for(symbolBindings binding : stable)
			if(binding.id().equals(funname.toString()) && binding.contains(declname.toString()))
			error("Binding " + declname.toString() + " already exists in the symbol table; exiting");
			funadd(stable, funname.toString(), new symbolBindings(declname.toString(), r.toString()));
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void funparams(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST funname
	) throws RecognitionException {
		
		AST funparams_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST t = null;
		
		try {      // for error handling
			AST __t151 = _t;
			AST tmp17_AST_in = (AST)_t;
			match(_t,DECL);
			_t = _t.getFirstChild();
			AST __t152 = _t;
			AST tmp18_AST_in = (AST)_t;
			match(_t,TYPE);
			_t = _t.getFirstChild();
			t = _t==ASTNULL ? null : (AST)_t;
			type(_t);
			_t = _retTree;
			_t = __t152;
			_t = _t.getNextSibling();
			{
			_loop154:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==ID)) {
					funtypedecls(_t,stypes, stable, t, funname);
					_t = _retTree;
				}
				else {
					break _loop154;
				}
				
			} while (true);
			}
			_t = __t151;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void returntype(AST _t) throws RecognitionException {
		
		AST returntype_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case STRUCT:
			case INT:
			case BOOL:
			{
				type(_t);
				_t = _retTree;
				break;
			}
			case VOID:
			{
				AST tmp19_AST_in = (AST)_t;
				match(_t,VOID);
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
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void fun(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable
	) throws RecognitionException {
		
		AST fun_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST funname = null;
		boolean doesthisreturn = false; String returnType;
		
		try {      // for error handling
			AST __t162 = _t;
			AST tmp20_AST_in = (AST)_t;
			match(_t,FUN);
			_t = _t.getFirstChild();
			funname = (AST)_t;
			match(_t,ID);
			_t = _t.getNextSibling();
			params(_t,stypes, stable, funname);
			_t = _retTree;
			returnType=rettype(_t,stypes, stable, funname);
			_t = _retTree;
			{
			int _cnt164=0;
			_loop164:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==DECLS)) {
					fundecl(_t,stypes, stable, funname);
					_t = _retTree;
				}
				else {
					if ( _cnt164>=1 ) { break _loop164; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt164++;
			} while (true);
			}
			doesthisreturn=statements(_t,stypes, stable, funname.toString(), returnType);
			_t = _retTree;
			_t = __t162;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void params(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST funname
	) throws RecognitionException {
		
		AST params_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t166 = _t;
			AST tmp21_AST_in = (AST)_t;
			match(_t,PARAMS);
			_t = _t.getFirstChild();
			{
			_loop168:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==DECL)) {
					funparams(_t,stypes, stable, funname);
					_t = _retTree;
				}
				else {
					break _loop168;
				}
				
			} while (true);
			}
			_t = __t166;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final String  rettype(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST funname
	) throws RecognitionException {
		String returnTypeString = "";
		
		AST rettype_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST r = null;
		
		try {      // for error handling
			AST __t170 = _t;
			AST tmp22_AST_in = (AST)_t;
			match(_t,RETTYPE);
			_t = _t.getFirstChild();
			r = _t==ASTNULL ? null : (AST)_t;
			returntype(_t);
			_t = _retTree;
			_t = __t170;
			_t = _t.getNextSibling();
			
					returnTypeString = r.toString();
					if(returnTypeString.equals("struct"))
				returnTypeString = r.getFirstChild().toString();
					System.out.println(returnTypeString);
					boolean found = false;
					for(symbolBindings s : stable)
						if(s.id().equals(funname.toString()))
							{ s.setReturnType(returnTypeString); found = true; break; }
					if (!found)
					stable.add(new symbolBindings(funname.toString(), "fun").setReturnType(r.toString()));
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returnTypeString;
	}
	
	public final void fundecl(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, AST funname
	) throws RecognitionException {
		
		AST fundecl_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t172 = _t;
			AST tmp23_AST_in = (AST)_t;
			match(_t,DECLS);
			_t = _t.getFirstChild();
			{
			_loop174:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_t.getType()==DECLLIST)) {
					fundecllist(_t,stypes, stable, funname);
					_t = _retTree;
				}
				else {
					break _loop174;
				}
				
			} while (true);
			}
			_t = __t172;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final boolean  statements(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		boolean returned = false;
		
		AST statements_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		String t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case BLOCK:
			{
				returned=block(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case ASSIGN:
			{
				assignment(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case PRINT:
			{
				print(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case READ:
			{
				read(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case IF:
			{
				returned=conditional(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case WHILE:
			{
				returned=loop(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case DELETE:
			{
				delete(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case RETURN:
			{
				returned=ret(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case INVOKE:
			{
				t=invocation(_t,stypes,stable,functionName,returnType);
				_t = _retTree;
				break;
			}
			case STMTS:
			{
				AST __t176 = _t;
				AST tmp24_AST_in = (AST)_t;
				match(_t,STMTS);
				_t = _t.getFirstChild();
				{
				_loop178:
				do {
					if (_t==null) _t=ASTNULL;
					if ((_tokenSet_0.member(_t.getType()))) {
						returned=statements(_t,stypes,stable,functionName,returnType);
						_t = _retTree;
					}
					else {
						break _loop178;
					}
					
				} while (true);
				}
				_t = __t176;
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
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returned;
	}
	
	public final boolean  block(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		boolean returned = false;
		
		AST block_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t180 = _t;
			AST tmp25_AST_in = (AST)_t;
			match(_t,BLOCK);
			_t = _t.getFirstChild();
			AST __t181 = _t;
			AST tmp26_AST_in = (AST)_t;
			match(_t,STMTS);
			_t = _t.getFirstChild();
			{
			_loop183:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					returned=statements(_t,stypes,stable,functionName,returnType);
					_t = _retTree;
				}
				else {
					break _loop183;
				}
				
			} while (true);
			}
			_t = __t181;
			_t = _t.getNextSibling();
			_t = __t180;
			_t = _t.getNextSibling();
			
					System.out.println("test: block");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returned;
	}
	
	public final void assignment(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		
		AST assignment_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp27_AST_in = (AST)_t;
			match(_t,ASSIGN);
			_t = _t.getNextSibling();
			
					System.out.println("test: assignment");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void print(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		
		AST print_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp28_AST_in = (AST)_t;
			match(_t,PRINT);
			_t = _t.getNextSibling();
			
					System.out.println("test: print");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void read(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		
		AST read_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp29_AST_in = (AST)_t;
			match(_t,READ);
			_t = _t.getNextSibling();
			
					System.out.println("test: read");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final boolean  conditional(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		boolean returned = false;
		
		AST conditional_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp30_AST_in = (AST)_t;
			match(_t,IF);
			_t = _t.getNextSibling();
			
					System.out.println("test: conditional");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returned;
	}
	
	public final boolean  loop(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		boolean returned = false;
		
		AST loop_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp31_AST_in = (AST)_t;
			match(_t,WHILE);
			_t = _t.getNextSibling();
			
					System.out.println("test: loop");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returned;
	}
	
	public final void delete(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		
		AST delete_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp32_AST_in = (AST)_t;
			match(_t,DELETE);
			_t = _t.getNextSibling();
			
					System.out.println("test: delete");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final boolean  ret(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		boolean returned = false;
		
		AST ret_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST e = null;
		String expReturnType = "void";
		
		try {      // for error handling
			AST __t191 = _t;
			AST tmp33_AST_in = (AST)_t;
			match(_t,RETURN);
			_t = _t.getFirstChild();
			{
			e = _t==ASTNULL ? null : (AST)_t;
			expReturnType=unary(_t,stypes,stable,functionName);
			_t = _retTree;
			}
			_t = __t191;
			_t = _t.getNextSibling();
			
					// System.out.println("test: ret " + expReturnType);
					if (expReturnType.equals("") && !returnType.equals("void"))
						error("function " + functionName + " expects return type " + returnType + ", found void");
					else if(!returnType.equals(expReturnType))
						error("function " + functionName + " expects return type " + returnType + ", found " + expReturnType);
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return returned;
	}
	
	public final String  invocation(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName, String returnType
	) throws RecognitionException {
		String type = "";
		
		AST invocation_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST tmp34_AST_in = (AST)_t;
			match(_t,INVOKE);
			_t = _t.getNextSibling();
			
					System.out.println("test: invocation");
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return type;
	}
	
	public final String  unary(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName
	) throws RecognitionException {
		String expressionType = "void";
		
		AST unary_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST type1 = null;
		AST type2 = null;
		AST type3 = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NOT:
			{
				AST __t194 = _t;
				AST tmp35_AST_in = (AST)_t;
				match(_t,NOT);
				_t = _t.getFirstChild();
				type1 = _t==ASTNULL ? null : (AST)_t;
				expressionType=unary(_t,stypes,stable,functionName);
				_t = _retTree;
				_t = __t194;
				_t = _t.getNextSibling();
				
						if(expressionType != "bool")
							error("unary operator inappropriately applied to " + expressionType);
					
				break;
			}
			case MINUS:
			{
				AST __t195 = _t;
				AST tmp36_AST_in = (AST)_t;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				type2 = _t==ASTNULL ? null : (AST)_t;
				expressionType=unary(_t,stypes,stable,functionName);
				_t = _retTree;
				_t = __t195;
				_t = _t.getNextSibling();
				
						if(expressionType != "int")
							error("negative operator inappropriately applied to " + expressionType);
					
				break;
			}
			case TRUE:
			case FALSE:
			case DOT:
			case ID:
			case INTEGER:
			{
				type3 = _t==ASTNULL ? null : (AST)_t;
				expressionType=selector(_t,stypes,stable,functionName);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return expressionType;
	}
	
	public final String  selector(AST _t,
		ArrayList<evilStruct> stypes, ArrayList<symbolBindings> stable, String functionName
	) throws RecognitionException {
		String expressionType = "void";
		
		AST selector_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		AST integer = null;
		AST boolybool1 = null;
		AST boolybool2 = null;
		AST structfound = null;
		AST field = null;
		String foundStructType = "true";
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case ID:
			{
				id = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
					expressionType = getType(stable, functionName, id.toString());
						
				break;
			}
			case INTEGER:
			{
				integer = (AST)_t;
				match(_t,INTEGER);
				_t = _t.getNextSibling();
					expressionType = "int";
						
				break;
			}
			case TRUE:
			{
				boolybool1 = (AST)_t;
				match(_t,TRUE);
				_t = _t.getNextSibling();
					expressionType = "bool";
						
				break;
			}
			case FALSE:
			{
				boolybool2 = (AST)_t;
				match(_t,FALSE);
				_t = _t.getNextSibling();
					expressionType = "bool";
						
				break;
			}
			case DOT:
			{
				AST __t197 = _t;
				structfound = _t==ASTNULL ? null :(AST)_t;
				match(_t,DOT);
				_t = _t.getFirstChild();
				foundStructType=selector(_t,stypes,stable,functionName);
				_t = _retTree;
				field = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				_t = __t197;
				_t = _t.getNextSibling();
					for(evilStruct currentBinding : stypes)
								if(currentBinding.structtype().equals(foundStructType))
									if(currentBinding.containsField(field.toString()) != null)
										expressionType = currentBinding.containsField(field.toString());
							if(expressionType.equals(""))
								error("invalid field " + field.toString() + " for struct type " + foundStructType + " specified");
						
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return expressionType;
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
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 252201579267086848L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
