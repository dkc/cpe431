public class LordILOC {
	private static int nextLabel = 0;
	
	public static String getNextLabel()
	{
		return "L" + nextLabel++;
	}
}