import antlr.*;

public class AnnotatedExpressionAST
   extends LineNumberAST
{
   private int _num;

   public AnnotatedExpressionAST()
   {
      super();
   }

   public AnnotatedExpressionAST(Token tok)
   {
      super(tok);
   }

   public void setNumberOfRegisters(int num)
   {
      _num = num;
   }

   public int getNumberOfRegisters()
   {
      return _num;
   }
}
