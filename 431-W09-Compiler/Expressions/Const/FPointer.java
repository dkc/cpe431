
public class FPointer extends AbstractCodeAndReg{
	public int address;
	
	public FInteger(int address, int regnum){
		super(regnum);
		this.address = address;
	}
	
	public CodeAndReg compile(){
		this.code = this.reg + " = add i32 0, " + ((number << 2) + 1) + "\n";
		return this;
	}
}
