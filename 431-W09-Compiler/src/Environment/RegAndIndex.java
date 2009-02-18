package Environment;

import java.util.ArrayList;

public class RegAndIndex {
	public String reg;
	public int index;
	public ArrayList<String> code;
	
	public RegAndIndex(){
		reg = "%reg0";
		index = 0;
		code = new ArrayList<String>();
	}
}
