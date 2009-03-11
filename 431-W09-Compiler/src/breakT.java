import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Scanner;


public class breakT {
	public static void main(String[] args) {
		try{
			System.out.println("whee");
			Scanner s = new Scanner(new File("tests.txt"));
			String curLine;
			int testCaseNumber = 1;
			PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("tests/test" + testCaseNumber + ".g")));
			while(s.hasNextLine()) {
				curLine = s.nextLine();
				if(curLine.equals("**")) {
					testCaseNumber++;
					output.close();
					output = new PrintWriter(new BufferedWriter(new FileWriter("tests/test" + testCaseNumber + ".g")));
					continue;
				} else if(curLine.length() > 1 && curLine.substring(0,2).equals("**")) {
					System.out.println("echo '~~Test #" + testCaseNumber + "~~'");
					System.out.println("../compile -emitllvm test" + testCaseNumber + ".g");
					System.out.println("../a.out");
					System.out.println("echo '" + curLine + "'");
					System.out.println("echo '" + s.nextLine() + "'");
					System.out.println("\n");
				} else 
					output.println(curLine);
			}
		} catch(FileNotFoundException e) {
			System.err.println("fnf");
		} catch(IOException e) {
			
		}
	}
}
