import Environment.Env;
import Expressions.CodeAndReg;
import Expressions.Sequence;
import LLVMObjects.LLVMLine;
import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.io.*;
import java.util.ArrayList;
 
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
      //TODO setup regnum
      Env env = new Env();
      compiledCode.staticPass(env);
      
      //compile creates llvm code
      compiledCode.compile(env);
      
      //write output
      writeLLVM(compiledCode, env);
      
   }
   
   private static void writeLLVM(CodeAndReg compiledCode, Env env){
	   ArrayList<LLVMLine> code = compiledCode.getCode();
      Writer output = null;
      String outputFile = "llvm-code.s";
      try{
    	  output = new BufferedWriter(new FileWriter(outputFile));
      }catch(Exception e){
    	  error("Could not create and open llvm-code.s for writing");
      }
      
      try{
		//define types
		output.write("%eframe = type{%eframe*, i32, [ 0 x i32 ]}\n");
		  
		//output beginning of main
		output.write("define i32 @llvm_fun(){\n");
		  
		output.write(env.getCurrentScope() + " = malloc { %eframe*, i32, [ " + env.numIds() + " x i32 ] }\n");
		  
		//output code
		for(LLVMLine line : code)
			output.write(line.getCode());
		
		//output return from main
		output.write("ret i32 " + compiledCode.getReg() + "\n");
		output.write("}");
      } catch(Exception e){
    	  error("Could not write output to file llvm-code.s");
      }
      
      try{
    	  output.close();
      } catch(Exception e){
    	  error("Could not close " + outputFile);
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
 