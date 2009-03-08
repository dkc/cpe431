package LLVMObjects;

public class Mapping {
	private String originalLLVM, mappingSPARC;
	
	
	public Mapping(String r1, String r2) {
		originalLLVM = r1;
		mappingSPARC = r2;
	}
	
	@Override
	public String toString() {
		return originalLLVM + "->" + mappingSPARC;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.toString() == o.toString())
			return true;
		return false;
	}
}
