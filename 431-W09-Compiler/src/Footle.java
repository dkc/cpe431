import Environment.*;
import Expressions.*;
import Expressions.Const.*;
import LLVMObjects.*;
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
            
      //Static Pass initializes env
      /* Env env = new Env(0);
      ArrayList<String> stringdecs = new ArrayList<String>();
      Hashtable<String, Integer> fieldTable = new Hashtable<String, Integer>();
      ArrayList<FuncIDandParams> funcids = new ArrayList<FuncIDandParams>();
      compiledCode.staticPass(env, funcids, stringdecs);
      
      //compile creates llvm code
      ArrayList<LLVMLine> funcdecs = new ArrayList<LLVMLine>();
      compiledCode.compile(env, funcdecs, fieldTable);

      //write output
      writeLLVM(compiledCode, env, funcdecs, funcids, stringdecs);

      //convert to SPARC
      if(emitLLVM == false) {
    	  LLVMToSPARC.convertLLVM(funcdecs, compiledCode.getCode());
      }*/
   }
   
   private static void writeLLVM(CodeAndReg compiledCode, Env env, ArrayList<LLVMLine> funcdecs,
		ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
	   LLVMLine currentLine;
	   ArrayList<LLVMLine> setupCode = new ArrayList<LLVMLine>();
	   	ArrayList<LLVMLine> code = compiledCode.getCode();
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
	    	  output.write("@empty_slots = constant %slots undef\n");
	    	  
	    	  output.write("%eframe = type{%eframe*, i32, [0 x i32]}\n");
	    	  
	    	  output.write("%sobj = type{i32, %slots*, i8*}\n");
	    	  output.write("%fobj = type{i32, i32}\n");
	    	  output.write("%cobj = type{i32, %slots*, %eframe*}\n");
	    	  output.write("%pobj = type{i32, %slots*}\n");
	    	  
	    	  //field lookup fun
	    	  output.write("define i32* @field_lookup( i32 %fid, %slots* %slot){\n");
	    	  
	    	  //check for empty
	    	  output.write("%emptcmp = icmp eq %slots* %slot, @empty_slots\n");
	    	  output.write("br i1 %emptcmp, label %err, label %cont\n");
	    	  output.write("err:\n");
	    	  output.write("call void @print_fieldlookup_err()\n");
	    	  output.write("%pointless = inttoptr i32 %fid to i32*\n");
	    	  output.write("ret i32* %pointless\n");
	    	  output.write("cont:\n");
	    	  
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
	    	  output.write("define i32 @dispatch_fun(%cobj* %clos, i32 %len, i32* %args, i32 %parent){\n");
	    	  output.write("%frameptr = getelementptr %cobj* %clos, i32 0, i32 2\n");
	    	  //output.write("%envptr = load %eframe** %frameptr\n");
	    	  output.write("%envframe = load %eframe** %frameptr\n");
	    	  output.write("%fidptr = getelementptr %cobj* %clos, i32 0, i32 0\n");
	    	  output.write("%shftreg = load i32* %fidptr\n");
	    	  output.write("%fid = lshr i32 %shftreg, 2\n");
	    	  output.write("switch i32 %fid, label %default [");
	    	  //for(Integer i: funcids){
	    	  for(FuncIDandParams fandp: funcids){
	    		  int i = fandp.getId();
	    		  output.write(" i32 " + i + ", label %funccall" + i + " ");
	    	  }
	    	  output.write("]\n");
	    	  //for(Integer i: funcids){
	    	  for(FuncIDandParams fandp: funcids){
	    		  int i = fandp.getId();
	    		  output.write("funccall" + i + ":\n");
	    		  
	    		  //check #args
	    		  output.write("call void @args_check( i32 %len, i32 " + fandp.getNumparams() + " )\n");
	    		  
	    		  //build eframe from args, recursive helper fun? probably
	    		  output.write("%funcenv" + i + " = call %eframe* @createArgsList ( i32 %len, i32* %args, %eframe* %envframe, i32 0)\n");
	    		  
	    		  //branch on parent, 0 = void ie function call
	    		  output.write("%ftest" + i + " = icmp eq i32 %parent, 0\n");
	    		  output.write("br i1 %ftest" + i + ",  label %ffun" + i + ", label %fmet" + i + "\n");
	    		  
	    		  //fun call
	    		  output.write("ffun" + i + ":\n");
	    		  output.write("%rfun" + i + " = call i32 @footle_fun" + i + " ( %eframe* %envframe )\n");
	    		  output.write("ret i32 %rfun" + i + "\n");
	    		  
	    		  //met call
	    		  output.write("fmet" + i + ":\n");
	    		  output.write("%rmet" + i + " = call i32 @footle_met" + i + " ( %eframe* %envframe, i32 %parent )\n");
	    		  output.write("ret i32 %rmet" + i + "\n");
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
	    	  
	    	  output.write("declare void @print_fieldlookup_err()\n");
	    	  output.write("declare void @args_check(i32, i32)\n");
	    	  output.write("declare void @type_check(i32, i32)\n");
	    	  output.write("declare void @obj_type_check(i32, i32)\n");
	    	  output.write("declare void @neg_float_check(i32)\n");
	    	  output.write("declare void @footle_print(i32)\n");
	    	  output.write("declare i32 @string_len(i8*)\n");
	    	  output.write("declare i32 @instance_int_check(i32)\n");
	    	  output.write("declare i32 @instance_bool_check(i32)\n");
	    	  output.write("declare i32 @instance_void_check(i32)\n");
	    	  output.write("declare i32 @instance_obj_check(i32,i32)\n");
	    	  output.write("declare i32 @instance_of(i32,i32)\n");
	    	  output.write("declare i32 @string_eq(i32,i32)\n");
	    	  output.write("declare i32 @string_comp(i32,i32)\n");
	    	  output.write("declare i8* @string_sub(i32,i32,i32)\n");
	    	  output.write("declare i8* @read_line()\n");
	    	  
	    	  //write funcdecs
	    	  for(LLVMLine line : funcdecs){
	    		  output.write(line.getCode());
	  			}
	    	  
	    	  //write string decs
	    	  for(String line: stringdecs){
	    		  output.write(line);
	    	  }
	    	  
	    	  //output beginning of llvm_fun
	    	  
	   	   	  currentLine = new LLVMLine("define i32 @llvm_fun() {\n");
	   	   	  currentLine.setOperation("fundec");
	   	   	  currentLine.setLabel("llvm_fun");
	   	   	  setupCode.add(currentLine);
	    	  
	   	   	  currentLine = new LLVMLine(env.getMallocReg() + " = malloc {%eframe*, i32, [" + env.numIds() +
	   	   	  " x i32]}\n");
	   	   	  currentLine.setOperation("malloc");
	   	   	  currentLine.setRegisterDefined(env.getMallocReg());
	   	   	  currentLine.addConstantUsed(4 + 4 + 4 * env.numIds());
	   	   	  setupCode.add(currentLine);
	   	   	  
	   	   	  currentLine = new LLVMLine(env.getCurrentScope() + " = bitcast {%eframe*, i32, [" + env.numIds() + 
	   	   			  " x i32]}* " + env.getMallocReg() + " to %eframe*\n");
	    	  currentLine.setOperation("bitcast");
	    	  currentLine.setRegisterDefined(env.getCurrentScope());
	    	  currentLine.addRegisterUsed(env.getMallocReg());
	   	   	  setupCode.add(currentLine);
	   	   	  
	   	   	  //output code
	   	   	  code.addAll(0, setupCode);
	   	   	  
	   	   	  //output return
	    	  currentLine = new LLVMLine("ret i32 " + compiledCode.getReg() + "\n");
	    	  currentLine.setOperation("ret");
	    	  currentLine.addRegisterUsed(compiledCode.getReg());
	    	  code.add(currentLine);
	    	  
	    	  code.add(new LLVMLine("}\n"));
	   	   	  
	    	  for(LLVMLine line : code) {
	    		  output.write(line.getCode());
	    	  }
	    	  
	    	  
	      }catch(Exception e){
	    	  error("Could not write output to file llvm-code.s");
	      }
	      
	      try{
	      output.close();
	      }catch(Exception e){
	    	  error("Could not close llvm-code.s");
	      }
   }
 
   private static void error(String msg)
   {
      System.err.println(msg);
      System.exit(1);
   }
 
   private static final String DISPLAYAST = "-displayAST";
   private static final String EMITLLVM = "-emit-llvm";
 
   private static String inputFile = null;
   private static boolean displayAST = false;
   private static boolean emitLLVM = false;
 
   private static void parseParameters(String [] args)
   {
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals(DISPLAYAST))
         {
            displayAST = true;
         }
         else if(args[i].equals(EMITLLVM))
         {
        	 emitLLVM = true;
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
 