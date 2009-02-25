package Environment;

import java.util.ArrayList;

import LLVMObjects.LLVMLine;

public class RegAndIndex {
	public String reg;
	public int index;
	public ArrayList<LLVMLine> code;
	
	public RegAndIndex(){
		reg = "%reg0";
		index = 0;
		code = new ArrayList<LLVMLine>();
	}
}