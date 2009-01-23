import antlr.*;
import antlr.collections.*;
import antlr.debug.misc.ASTFrame;
import java.io.*;
import java.util.ArrayList;

public class Evil
{
   public static void main(String[] args)
   {
      parseParameters(args);

      EvilParser parser = new EvilParser(createLexer());
      
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

      /*************
         call to tree parser goes here
      **************/
      EvilTypechecker treeparser = new EvilTypechecker();
      
      ArrayList<evilStruct> structureDefs = new ArrayList<evilStruct>();
      ArrayList<symbolBindings> bindingList = new ArrayList<symbolBindings>();
      try
      {
    	  treeparser.validate(t, structureDefs, bindingList);
      }
      catch(RecognitionException e)
      {
    	  System.out.println("I don't really know what a RecognitionException is, but here you go, I guess.");
      }
      
      validateMain(bindingList);
      
      for(evilStruct existingstruct : structureDefs)
    	  System.out.println(existingstruct.toString());
      System.out.println(bindingList);
   }
   
   private static void validateMain (ArrayList<symbolBindings> stable)
   {
	   for(symbolBindings someBinding : stable)
		   if(someBinding.id().equals("main") &&
		      someBinding.returntype().equals("int") &&
		      someBinding.type().equals("fun") &&
		      !someBinding.hasParams())
			   return;
	   error("no valid definition of function main found");      
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

   private static EvilLexer createLexer()
   {
      if (inputFile == null)
      {
         return new EvilLexer(System.in);
      }
      else
      {
         try
         {
            return new EvilLexer(new BufferedReader(new FileReader(inputFile)));
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
