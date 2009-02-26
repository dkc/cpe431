import Environment.Env;
import Expressions.Application;
import Expressions.CodeAndReg;
import Expressions.FReturn;
import Expressions.FuncDec;
import Expressions.Sequence;
import Expressions.VarRef;
import Expressions.Const.FInteger;
import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.io.*;
import java.util.ArrayList;

import java.util.Hashtable;
 
public class Footle
{
   public static void main(String[] args)
   {
      parseParameters(args);
 
      FootleParser parser = new FootleParser(createLexer());
      
      try
      {
         parser.setASTNodeClass("LineNumberAST");
         parser.program();
      }
      catch (antlr.TokenStreamException e)
      {
         error(e.toString());
      }
      catch (antlr.RecognitionException e)
      {
         error(e.toString());
      }
 
      AST t = parser.getAST();
      if (displayAST && t != null)
      {
         ASTFrame frame = new ASTFrame("AST", t);
         frame.setVisible(true);
      }
 
      // attempt to validate
      
      Sequence compiledCode = null;
      FootleTreeParser treeparser = new FootleTreeParser();
      
      try
      {
    	  compiledCode = treeparser.validate(t);
      }
      catch(RecognitionException e)
      {
       System.out.println("I don't really know what a RecognitionException is, but here you go, I guess.");
      }
      
      System.out.println("initial sequence: " + compiledCode.seq.toString());
      
      //Static Pass initializes env
      Env env = new Env(0);
      ArrayList<Integer> funcids = new ArrayList<Integer>();
      Hashtable<String, Integer> fieldTable = new Hashtable<String, Integer>();
      compiledCode.staticPass(env, funcids);
      
      //TEST CODE
      /*ArrayList<CodeAndReg> testseq = new ArrayList<CodeAndReg>();
      ArrayList<String> params = new ArrayList<String>();
      params.add("x");
      //FInteger retval = new FInteger(8,0);
      VarRef retval = new VarRef("x",0);
      FReturn ret = new FReturn(retval, 20);
      //ArrayList<CodeAndReg> bodylist = new ArrayList<CodeAndReg>();
      //bodylist.add(retval);
      //bodylist.add(ret);
      //Sequence body = new Sequence(bodylist, 21);
      testseq.add(new FuncDec("f",params,ret,1));
      ArrayList<CodeAndReg> argslist = new ArrayList<CodeAndReg>();
      argslist.add(new FInteger(1,50));
      testseq.add(new Application("f",argslist,2));
      Sequence seq = new Sequence(testseq,3);
      seq.staticPass(env, funcids);*/
      
      //compile creates llvm code
      ArrayList<String> funcdecs = new ArrayList<String>();
      compiledCode.compile(env, funcdecs, fieldTable);
      //seq.compile(env, funcdecs, fieldTable);
      
      //write output
      writeLLVM(compiledCode, env, funcdecs, funcids);
      //writeLLVM(seq, env, funcdecs, funcids);
      //writeDispatch(funcids, "If with Var Ref", 4);
   }
   
   private static void writeLLVM(CodeAndReg compiledCode, Env env, ArrayList<String> funcdecs,
		   ArrayList<Integer> funcids){
	   ArrayList<String> code = compiledCode.getCode();
	      Writer output = null;
	      try{
	      output = new BufferedWriter(new FileWriter("llvm-code.s"));
	      }catch(Exception e){
	    	  error("Could not create and open llvm-code.s for writing");
	      }
	      
	      try{
	    	  //define types
	    	  output.write("%field = type {i32, i32}\n");
	    	  output.write("%slots = type {%field, %slots*}\n");
	    	  output.write("@emptyslots = constant %slots undef\n");
	    	  
	    	  output.write("%eframe = type{%eframe*, i32, [0 x i32]}\n");
	    	  
	    	  output.write("%strobj = type{i32, %slots*, i8*}\n");
	    	  output.write("%floatobj = type{i32, float}\n");
	    	  output.write("%cobj = type{i32, %slots*, %eframe*}\n");
	    	  output.write("%pobj = type{i32, %slots*}\n");
	    	  
	    	  //TODO no error on end of list, just seg fault =(
	    	  //field lookup fun
	    	  output.write("define i32* @field_lookup( i32 %fid, %slots* %slot){\n");
	    	  output.write("%r0 = getelementptr %slots* %slot, i32 0, i32 0\n");
	    	  output.write("%r1 = getelementptr %field* %r0, i32 0, i32 0\n");
	    	  output.write("%r2 = load i32* %r1\n");
	    	  output.write("%r3 = icmp eq i32 %fid, %r2\n");
	    	  output.write("br i1 %r3, label %eqret, label %neqrec\n");
	    	  output.write("neqrec:\n");
	    	  output.write("%r4 = getelementptr %slots* %slot, i32 0, i32 1\n");
	    	  output.write("%r5 = load %slots** %r4\n");
	    	  output.write("%r14 = call i32* @field_lookup( i32 %fid, %slots* %r5)\n");
	    	  output.write("ret i32* %r14\n");
	    	  output.write("eqret:\n");
	    	  output.write("%r15 = getelementptr %field* %r0, i32 0, i32 1\n");
	    	  output.write("ret i32* %r15\n");
	    	  output.write("}\n");
	    	  
	    	  //dispatch fun
	    	  output.write("define i32 @dispatch_fun(%cobj* %clos, i32 %len, i32* %args){\n");
	    	  output.write("%frameptr = getelementptr %cobj* %clos, i32 0, i32 2\n");
	    	  //output.write("%envptr = load %eframe** %frameptr\n");
	    	  output.write("%envframe = load %eframe** %frameptr\n");
	    	  output.write("%fidptr = getelementptr %cobj* %clos, i32 0, i32 0\n");
	    	  output.write("%shftreg = load i32* %fidptr\n");
	    	  output.write("%fid = lshr i32 %shftreg, 2\n");
	    	  output.write("switch i32 %fid, label %default [");
	    	  for(Integer i: funcids){
	    		  output.write(" i32 " + i + ", label %funccall" + i + " ");
	    	  }
	    	  output.write("]\n");
	    	  for(Integer i: funcids){
	    		  output.write("funccall" + i + ":\n");
	    		  //build eframe from args, recursive helper fun? probably
	    		  output.write("%funcenv = call %eframe* @createArgsList ( i32 %len, i32* %args, %eframe* %envframe, i32 0)\n");
	    		  output.write("%r" + i + " = call i32 @footle_fun" + i + " ( %eframe* %envframe )\n");
	    		  output.write("ret i32 %r" + i + "\n");
	    	  }
	    	  output.write("default:\n");
	    	  output.write("ret i32 0\n");
	    	  output.write("}\n");
	    	  
	    	  //create args fun, no need to return really... other than a need to stop
	    	  output.write("define %eframe* @createArgsList( i32 %len, i32* %args, %eframe* %env, i32 %index){\n");
	    	  output.write("%tst = icmp eq i32 0, %len\n");
	    	  output.write("br i1 %tst, label %end, label %rec\n");
	    	  output.write("rec:\n");
	    	  output.write("%r1 = getelementptr i32* %args, i32 %index\n");
	    	  output.write("%r2 = load i32* %r1\n");
	    	  output.write("%r3 = getelementptr %eframe* %env, i32 0, i32 2, i32 %index\n");
	    	  output.write("store i32 %r2, i32* %r3\n");
	    	  output.write("%nexti = add i32 1, %index\n");
	    	  output.write("%newlen = add i32 -1, %len\n");
	    	  output.write("%r5 = call %eframe* @createArgsList( i32 %newlen, i32* %args, %eframe* %env, i32 %nexti )\n");
	    	  output.write("ret %eframe* %r5\n");
	    	  output.write("end:\n");
	    	  output.write("ret %eframe* %env\n");  
	    	  output.write("}\n");
	    	  
	    	  output.write("declare void @type_check(i32, i32)\n");
	    	  
	    	  //write funcdecs
	    	  for(String line : funcdecs){
	    		  output.write(line);
	  			}
	    	  
	    	  //output beginning of llvm_fun
	    	  output.write("define i32 @llvm_fun(){\n");
	   	   	  
	   	   	  output.write(env.getMallocReg() + " = malloc {%eframe*, i32, [" + env.ids.size() +
	   	   	  " x i32]}\n");
	   	   	  output.write(env.getCurrentScope() + " = bitcast {%eframe*, i32, [" + env.ids.size() + 
	   	   			  " x i32]}* " + env.getMallocReg() + " to %eframe*\n");
	    	  
	   	   	  //output code
	    	  for(String line : code){
	    		  output.write(line);
	  			}
	    	  
	    	  //output return
	    	  output.write("ret i32 " + compiledCode.getReg() + "\n");
	    	  output.write("}");
	      }catch(Exception e){
	    	  error("Could not write output to file llvm-code.s");
	      }
	      
	      try{
	      output.close();
	      }catch(Exception e){
	    	  error("Could not close llvm-code.s");
	      }
   }
   
   private static void writeDispatch(ArrayList<Integer> funcids, String msg, int expVal){
	   	Writer output = null;
	      try{
	      output = new BufferedWriter(new FileWriter("dispatch.c"));
	      }catch(Exception e){
	    	  error("Could not create and open dispatch.c for writing");
	      }
	      //write output
	   try{
		   //output main
		   output.write("#include <stdio>\n");
		   output.write("extern unsigned int llvm_fun();\n");
		   output.write("int main(){\n");
		   output.write("unsigned int retVal = llvm_fun();\n");
		   output.write("printf(\"Test: " + msg + "\\n\");\n");
		   output.write("printf(\"Expected Output: " + expVal + "\\n\");\n");
		   output.write("printf(\"Test: %d\\n\", retVal);\n");
		   output.write("return 0;\n");
		   output.write("}\n");
		   
		   output.write("void type_check(unsigned int type, unsigned int test){\n");
		   output.write("unsigned int shfttype = type & 3;\n");
		   output.write("if(shfttype == test){\n");
		   output.write("}else{\n");
		   output.write("fprintf(stderr,\"Type error, expected but got \\n\");\n");
		   output.write("exit(-1);\n");
		   output.write("}\n");
		   output.write("}\n");
	   }catch(Exception e){
	    	  error("Could not write output to file llvm-code.s");
	      }
	   try{
		      output.close();
		      }catch(Exception e){
		    	  error("Could not close dispatch.c");
		      }
   }
 
   private static void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
 
   private static final String DISPLAYAST = "-displayAST";
 
   private static String inputFile = null;
   private static boolean displayAST = true;
 
   private static void parseParameters(String [] args)
   {
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals(DISPLAYAST))
         {
            displayAST = true;
         }
         else if (args[i].charAt(0) == '-')
         {
            System.err.println("unexpected option: " + args[i]);
            System.exit(1);
         }
         else if (inputFile != null)
         {
            System.err.println("too many files specified");
            System.exit(1);
         }
         else
         {
            inputFile = args[i];
         }
      }
   }
 
   private static FootleLexer createLexer()
   {
      if (inputFile == null)
      {
         return new FootleLexer(System.in);
      }
      else
      {
         try
         {
            return new FootleLexer(new BufferedReader(new FileReader(inputFile)));
         }
         catch (FileNotFoundException e)
         {
            System.err.println("file not found: " + inputFile);
            System.exit(1);
            return null;
         }
      }
   }
}
 