package LLVMObjects;

public class Mapping implements Comparable {
	protected String originalLLVM, mappingSPARC;
	
	
	public Mapping(String r1, String r2) {
		if(r1 == null) {
			System.err.println("what the fuck " + r1 + r2);
			System.exit(1);
		}
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
	
	public int compareTo(Object m) {
		Mapping q = (Mapping)m;
		return ((Integer)this.originalLLVM.length()).compareTo(((Integer)q.originalLLVM.length()));
	}
}
