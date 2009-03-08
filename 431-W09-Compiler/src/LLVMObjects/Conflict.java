package LLVMObjects;

public class Conflict {
	private String reg1, reg2;
	
	public Conflict(String r1, String r2) {
		if(r1.compareTo(r2) < 0) {
			reg1 = r1;
			reg2 = r2;
		}
		else {
			reg1 = r2;
			reg2 = r1;
		}
	}
	
	@Override
	public String toString() {
		return reg1 + " " + reg2;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.toString() == o.toString())
			return true;
		return false;
	}
}
