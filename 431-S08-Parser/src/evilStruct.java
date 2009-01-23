import java.util.ArrayList;

public class evilStruct {
	
	private String structtype;
	private ArrayList<symbolBindings> structfields = new ArrayList<symbolBindings>();
	
	public evilStruct (String type)
	{
		structtype = type;
	}
	
	public void error (String errormsg)
    {
        System.out.println(errormsg);
        System.exit(1);
    }
	
    public String structtype()
    {
    	return structtype;
    }
    
    /* takes: id
     * returns: type of that id, or null if not found
     */
    
    public boolean insertField(String stype, String newid, String newtype)
    {
    	if(stype.equals(structtype))
    	{
    	   if(containsField(newid) != null)
    	   {
    		   error("Error binding " + newtype + " " + newid + " - id already declared");
    	   }
    	   structfields.add(new symbolBindings(newid, newtype));
    	   return true;
    	}
    	return false;
    }
    
    public String containsField(String lookup)
    {
    	for(symbolBindings binding : structfields)
    	{
    		if(binding.id().equals(lookup))
    			return binding.type();
    	}
    	return null;
    }
    
    @Override
	public String toString()
    {
    	String toRet = "struct type: " + structtype + "\n    fields: ";
    	for(symbolBindings s : structfields)
    		toRet += s.toString()+ "\n            ";
    	return toRet;
    }
}