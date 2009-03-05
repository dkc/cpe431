package Expressions.Const;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;

public class FString extends AbstractCodeAndReg {
	public String content;
	
	private String objreg = "%objreg";
	private String idptr = "%idptr";
	private String slotsptr = "%slotsptr";
	private String castreg = "%castreg";
	private String shftreg = "%shftreg";
	private String constptr = "%constptr";
	private String stringptr = "%stringptr";
	
	public FString(String content, int regnum){
		super(regnum);
		this.content = content;
		
		this.objreg += regnum;
		this.idptr += regnum;
		this.slotsptr += regnum;
		this.castreg += regnum;
		this.shftreg += regnum;
		this.constptr += regnum;
		this.stringptr += regnum;
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs,
			Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		currentLine = new LLVMLine(this.objreg + " = malloc %sobj\n");
		currentLine.setOperation("malloc");
		currentLine.setRegisterDefined(this.objreg);
		this.code.add(currentLine);
		
		//store id
		currentLine = new LLVMLine(this.idptr + " = getelementptr %sobj* " + this.objreg + 
				", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.idptr);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 3, i32* " + this.idptr + "\n");//tag 2 for string
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.idptr);
		this.code.add(currentLine);
		
		//store empty slots
		currentLine = new LLVMLine(this.slotsptr + " = getelementptr %sobj* " + this.objreg + 
				", i32 0, i32 1\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.slotsptr);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store %slots* @empty_slots, %slots** " + this.slotsptr + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.slotsptr);
		this.code.add(currentLine);
		
		//store string to obj
		currentLine = new LLVMLine(this.stringptr + " = getelementptr %sobj* " + this.objreg + 
				", i32 0, i32 2\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.stringptr);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.constptr + " = getelementptr [" + this.content.length()
				+ " x i8]* @strconst" + this.regnum + ", i32 0, i32 0\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.constptr);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i8* " + this.constptr + ", i8** " + this.stringptr + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.constptr);
		currentLine.addRegisterUsed(this.stringptr);
		this.code.add(currentLine);
		
		//store sobj to env
		currentLine = new LLVMLine(this.castreg + " = ptrtoint %sobj* " + this.objreg + " to i32\n");
		currentLine.setOperation("ptrtoint");
		currentLine.setRegisterDefined(this.castreg);
		currentLine.addRegisterUsed(this.objreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.shftreg + " = shl i32 " + this.castreg + ", 2\n");
		currentLine.setOperation("shl");
		currentLine.setRegisterDefined(this.shftreg);
		currentLine.addRegisterUsed(this.castreg);
		currentLine.addConstantUsed(2);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine(this.reg + " = add i32 1, " + this.shftreg + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(this.shftreg);
		currentLine.addConstantUsed(1);
		this.code.add(currentLine);
		
		return this;
	}

	@Override
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs) {
		stringdecs.add("@strconst" + this.regnum + " = internal constant [" + this.content.length() + 
				" x i8] c\"" + this.content + "\"");
	}

}
