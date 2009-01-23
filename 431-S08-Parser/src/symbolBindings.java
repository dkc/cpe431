import java.util.ArrayList;

public class symbolBindings {
	private boolean hasParams = false;
	private String symbolid, symboltype, returntype = null;
	private ArrayList<symbolBindings> functionBindingList = new ArrayList<symbolBindings>();
	
	public symbolBindings(String id, String type)
	{
		symbolid = id;
		symboltype = type;
	}
	
	public symbolBindings(String id, symbolBindings toAdd)
	{
		symbolid = id;
		symboltype = "fun";
		functionBindingList.add(toAdd);
		hasParams = true;
	}
	
	public symbolBindings setReturnType(String toSet)
	{
		returntype = toSet;
		return this;
	}
	
	public void setHasParams()
	{
		hasParams = true;
	}
	
	public String id()
    {
    	return symbolid;
    }
	
	public String type()
	{
		return symboltype;
	}
	
	public String returntype()
	{
		return returntype;
	}
	
	@Override
	public String toString()
	{
		String toRet = symboltype + " " + symbolid;
		if(symboltype.equals("fun"))
			toRet += " " + returntype + " " + functionBindingList.toString();
		return toRet;
	}
	
	public boolean hasParams()
	{
		return hasParams;
	}
	
	public boolean contains(String id)
	{
		for(symbolBindings x : functionBindingList)
			if(x.id().equals(id))
				return true;
		return false;
	}
	
	public String getFieldType(String id)
	{
		for(symbolBindings x : functionBindingList)
			if(x.id().equals(id))
				return x.type();
		return null;
	}
	
	public void add(symbolBindings toAdd)
	{
		functionBindingList.add(toAdd);
	}
}