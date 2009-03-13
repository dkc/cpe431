package Environment;

public class FuncIDandParams {
	protected int id;
	protected int numparams;
	
	public FuncIDandParams(int id, int numparams){
		this.id = id;
		this.numparams = numparams;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumparams() {
		return numparams;
	}

	public void setNumparams(int numparams) {
		this.numparams = numparams;
	}
}
