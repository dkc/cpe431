
public abstract class AbstractCodeAndReg implements CodeAndReg{
	public String code;
	public String reg;
	
	public AbstractCodeAndReg(int regnum){
		this.reg = "%r" + regnum;
	}
	
	public abstract CodeAndReg compile(){
		return this;
	}
}
