package LLVMObjects;

public class Mapping {
	protected String originalLLVM, mappingSPARC;
	
	
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
		if(!o.getClass().equals(this.getClass()))
			return false;
		
		Mapping m = (Mapping)o;
		if(this.originalLLVM.equals(m.originalLLVM))
			return true;
		return false;
	}
}
