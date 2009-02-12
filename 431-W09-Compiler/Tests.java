import java.util.ArrayList;

import Environment.Env;
import Expressions.*;
import Expressions.Const.*;
import Expressions.FuncExp.*;
import Expressions.Objects.*;

import Values.*;

public class Tests {
	  public static void main(String[] argy) {
        Interp interp = new Interp();
        
        //Vars used as arguments to expressions
        ArrayList<Expression> args = new ArrayList<Expression>();
        ArrayList<Expression> args2 = new ArrayList<Expression>();
        ArrayList<Expression> args3 = new ArrayList<Expression>();
        ArrayList<String> params = new ArrayList<String>();
        ArrayList<Expression> seq = new ArrayList<Expression>();
        Env env;
        
        //Test Integer
        //Expression: 1
        printTest(1,interp.interp(new FInteger(1)),"Integer Expression");
        
        //Test Float
        //Expression: 1.1
        printTest(1.1f,interp.interp(new FFloat(1.1f)),"Float Expression");
        
        //Test Boolean
        //Expression: false
        printTest(false,interp.interp(new FBoolean(false)),"Boolean Expression");
        
        //Test Sequence
        seq.clear();
        seq.add(new FInteger(55));
        seq.add(new FBoolean(true));
        printTest(true,interp.interp(new Sequence(seq)),"Sequence");
        
        //Test Empty Sequence
        seq.clear();
        printTest(null,interp.interp(new Sequence(seq)),"Sequence of Zero Size");
        
        //Test While
         printTest(null,interp.interp(new Bind("test",new FBoolean(true),new WhileExp(new VarRef("test"),
        		new VarMut("test",new FBoolean(false))))),"While Expression");
        
        //Test If true test expression
        printTest(true,interp.interp(new IfExp(new FBoolean(true),new FBoolean(true),new FBoolean(false))),"If With True Test Exp");
        
        //Test If false test expression
        printTest(false,interp.interp(new IfExp(new FBoolean(false),new FBoolean(true),new FBoolean(false))),"If With False Test Exp");

        //Test Bind and VarRef
        printTest(true,interp.interp(new Bind("var",new FBoolean(true),new VarRef("var"))),"Binding and VarRef");

        //Test Double bind, only get the newest value
        printTest(true,interp.interp(
        		new Bind("var",new FBoolean(false),new Bind("var",new FBoolean(true),new VarRef("var")))),"Double Binding and VarRef");

        //Test VarMut Basic
        printTest(9,interp.interp(
        		new Bind("var",new FBoolean(true),new VarMut("var",new FInteger(9)))),
        		"Variable Mutation");
        
        //Test VarMut with Bind and VarRef
        seq.clear();
        seq.add(new VarMut("var",new FBoolean(false)));
        seq.add(new VarRef("var"));
        printTest(false,interp.interp(
        		new Bind("var",new FBoolean(true),new Sequence(seq))),"Variable Mutation With VarRef");

        
        //Test New Object
        //Expression:
        params.clear();
        params.add("y");
        args.clear();
        args.add(new FInteger(42));
        FuncDec fd = new FuncDec("constructor",params,new FieldMut("x",new VarRef("y"),new VarRef("this")));
        NewObj nobj = new NewObj(fd,args);
        Env slots = Env.add(new Env("constructor",fd.interp(null)),null);
        slots = Env.add(new Env("x",new VInteger(42)),slots);
        PObject pobjv = new PObject(slots);
        printTest(pobjv,interp.interp(nobj),"New Object");
        
        //Test Field Lookup
        //Expression 
        env = Env.add(new Env("x",new VInteger(42)), null);
        PObjectExp pobj = new PObjectExp(env);
        printTest(42,interp.interp(new FieldLookup("x",pobj)),"Field Lookup");
        
        //Test Field Mutation
        printTest(24,interp.interp(new FieldMut("x",new FInteger(24),pobj)),"Field Mutation With Field Present");
        
        printTest(42,interp.interp(new FieldMut("z",new FInteger(42),pobj)),"Field Mutation Without Field Present");
        
        //Test Method Call
        params.clear();
        params.add("x");
        params.add("y");
        //interp.interp(new FieldMut("func",new FuncDec("func",mparams,new VarRef("y")),pobj));
        Value fval = new FuncDec("func",params,new FReturn(new VarRef("y"))).interp(null);
        args.clear();
        args.add(new FInteger(42));
        args.add(new FInteger(24));
        env = Env.add(new Env("func",fval), null);
        pobj = new PObjectExp(env);
        printTest(24,interp.interp(new MethodCall(pobj,"func", args)),"Method Call");
        
        //Test FuncBind
        //params
        //args.clear();
        //args.add(new FuncDec("func",));
        
        //Application and Primops
        params.clear();
        params.add("y");
        args.clear();
        args.add(new FFloat(4.2f));
        printTest(null,interp.interp(new Application(new FuncDec("func",params,new VarRef("y")),args)),
        		"Application Returns Void/Null");
        
        args.clear();
        args.add(new FInteger(6));
        printTest(true, interp.interp(new Application(new UOp("integer?"),args)),"Primitive Op: integer?");
        printTest(false, interp.interp(new Application(new UOp("floating-point?"),args)),"Primitive Op: floating-point?");
        printTest(false, interp.interp(new Application(new UOp("boolean?"),args)),"Primitive Op: boolean?");
        printTest(false, interp.interp(new Application(new UOp("void?"),args)),"Primitive Op: void?");
        printTest(false, interp.interp(new Application(new UOp("string?"),args)),"Primitive Op: string?");
        printTest(false, interp.interp(new Application(new UOp("closure?"),args)),"Primitive Op: closure?");
        printTest(false, interp.interp(new Application(new UOp("plain?"),args)),"Primitive Op: plain?");
        
        args.add(new FInteger(2));
        printTest(6+2,interp.interp(new Application(new BOp("+"),args)),"Primitive Op: + Integer");
        printTest(6-2,interp.interp(new Application(new BOp("-"),args)),"Primitive Op: - Integer");
        printTest(6*2,interp.interp(new Application(new BOp("*"),args)),"Primitive Op: * Integer");
        printTest(6/2,interp.interp(new Application(new BOp("/"),args)),"Primitive Op: / Integer");
        printTest(6>2,interp.interp(new Application(new BOp(">"),args)),"Primitive Op: > Integer");
        printTest(6<2,interp.interp(new Application(new BOp("<"),args)),"Primitive Op: < Integer");
        printTest(6>=2,interp.interp(new Application(new BOp(">="),args)),"Primitive Op: >= Integer");
        printTest(6<=2,interp.interp(new Application(new BOp("<="),args)),"Primitive Op: <= Integer");
        printTest(6==2,interp.interp(new Application(new BOp("=="),args)),"Primitive Op: == Integer");
        
        args.clear();
        args.add(new FFloat(6.3f));
        printTest(true, interp.interp(new Application(new UOp("floating-point?"),args)),"Primitive Op: floating-point?");
        args.add(new FFloat(1.1f));
        printTest(6.3f+1.1f,interp.interp(new Application(new BOp("+"),args)),"Primitive Op: + Float");
        printTest(6.3f-1.1f,interp.interp(new Application(new BOp("-"),args)),"Primitive Op: - Float");
        printTest(6.3f*1.1f,interp.interp(new Application(new BOp("*"),args)),"Primitive Op: * Float");
        printTest(6.3f/1.1f,interp.interp(new Application(new BOp("/"),args)),"Primitive Op: / Float");
        printTest(6.3f>1.1f,interp.interp(new Application(new BOp(">"),args)),"Primitive Op: > Float");
        printTest(6.3f<1.1f,interp.interp(new Application(new BOp("<"),args)),"Primitive Op: < Float");
        printTest(6.3f>=1.1f,interp.interp(new Application(new BOp(">="),args)),"Primitive Op: >= Float");
        printTest(6.3f<=1.1f,interp.interp(new Application(new BOp("<="),args)),"Primitive Op: <= Float");
        printTest(6.3f==1.1f,interp.interp(new Application(new BOp("=="),args)),"Primitive Op: == Float");
        
        args.clear();
        args.add(new FBoolean(false));
        printTest(!false,interp.interp(new Application(new BOp("not"), args)),"Primitive Op: Boolean Not");
        args.add(new FBoolean(true));
        printTest(true && false,interp.interp(new Application(new BOp("and"), args)),"Primitive Op: Boolean And");
        printTest(true || false,interp.interp(new Application(new BOp("or"), args)),"Primitive Op: Boolean Or");
        printTest(true == false,interp.interp(new Application(new BOp("=="), args)),"Primitive Op: Boolean ==");
        
        args.clear();
        args.add(new FString("chimichanga"));
        printTest("chimichanga".length(),interp.interp(new Application(new UOp("string-length"), args)),"Primitive Op: String Length");
        args.add(new FString("quesadilla"));
        printTest("chimichanga".equals("quesadilla"),interp.interp(new Application(new BOp("string=?"), args)),"Primitive Op: String Equals");
        printTest("chimichanga".compareTo("quesadilla")<0,interp.interp(new Application(new BOp("string<?"), args)),"Primitive Op: String Less Than");
        printTest("chimichanga"=="quesadilla",interp.interp(new Application(new BOp("=="), args)),"Primitive Op: String ==");
        
        args.clear();
        args.add(new FString("chimichanga"));
        args.add(new FInteger(4));
        args.add(new FInteger(8));
        printTest(new SObject("chimichanga".substring(4,8),null),interp.interp(new Application(new BOp("substring"), args)),"Primitive Op: Substring");
        
        // Slightly Larger Test Case: bindings (both vals and funcs), boolean logic, closure application, print
        // var f(x,y,z) = (x+y-z)
        // var a = 1==(11-10) <-- evals to true
        // if(a and true)
        // then print f(3,2,1)
        // else print "no"
        params.clear(); // x, y, z -- the parameters of f
        args.clear(); // 1 in 1==(11-10)
        args2.clear(); // 11, 10 in 1==(11-10)
        ArrayList<Expression> xy = new ArrayList<Expression>(); // x, y in (x+y-z)
        params.add("x");
        params.add("y");
        params.add("z");
        args.add(new FInteger(1));
        args2.add(new FInteger(11));
        args2.add(new FInteger(10));
        args.add(new Application(new BOp("-"), args2));
        args3.add(new FInteger(3));
        args3.add(new FInteger(2));
        args3.add(new FInteger(1));
        xy.add(new VarRef("x"));
        xy.add(new VarRef("y"));
        Expression fApplication = new Application(new VarRef("f"), args3);
        Expression iffy = new IfExp(new VarRef("a"), new UnaryOperation(new UOp("print"), fApplication), new UnaryOperation(new UOp("print"), new FString("no")));
        Expression fBody = new Bind("a", new Application(new BOp("=="),args), iffy);
        ArrayList<FuncDec> fDecList = new ArrayList<FuncDec>();
        fDecList.add(new FuncDec("f",params, new FReturn(new BinaryOperation(new Application(new BOp("+"), xy), new BOp("-"), new VarRef("z")))));
        FuncBind functionBinding = new FuncBind(fDecList, fBody);
        
        printTest(null,interp.interp(functionBinding),"larger test case -- should print 4 and return null");
      }
	  
	  private static void printTest(Object expected, Object result, String desc){
		    System.out.println("******************************");
		    System.out.println("Test "+ desc);
	        System.out.println("Expected Result: " + expected);
	        System.out.println("Result: " + result);
	        if(expected == null){
	        	if(result == null){
		        	System.out.println("Test Passed");
		        }else{
		        	System.out.println("Test Failed");
		        }
	        }else{
	        	if(expected.equals(result)){
	        		System.out.println("Test Passed");
	        	}else{
	        		System.out.println("Test Failed");
	        	}
	        }
	        System.out.println("******************************");
	        System.out.println(" ");
	  }
}
