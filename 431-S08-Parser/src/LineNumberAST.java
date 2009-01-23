import antlr.*;

public class LineNumberAST
   extends CommonAST
{
   private int _line;

   public LineNumberAST()
   {
      super();
   }

   public LineNumberAST(Token tok)
   {
      super(tok);
      _line = tok.getLine();
   }

   @Override
public void initialize(Token tok)
   {
      super.initialize(tok);
      _line = tok.getLine();
   }

   public void setLine(int line)
   {
      _line = line;
   }

   @Override
public int getLine()
   {
      return _line;
   }
}
